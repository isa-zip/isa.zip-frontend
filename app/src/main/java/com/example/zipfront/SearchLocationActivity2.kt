package com.example.zipfront

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Resources
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextWatcher
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toDrawable
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.zipfront.databinding.ActivitySearchlocationBinding
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

class SearchLocationActivity2 :AppCompatActivity(), ItemClickHandler, OnItemClick {
    lateinit var binding: ActivitySearchlocationBinding
    private val gson = Gson()

    private lateinit var mPrefs: SharedPreferences
    private lateinit var mEditPrefs: SharedPreferences.Editor

    //최근 검색기록
    private var searchLocationList = ArrayList<CurrentSearch>()
    private var stringPrefs: String? = null

    private var locationdong: String? = null

    //위치 검색 구현
    private lateinit var retrofit: Retrofit
    private lateinit var locationService: LocationService
    private var locationList = mutableListOf<Location>()
    private lateinit var locationAdapter: LocationAdapter2

    //현재 검색


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchlocationBinding.inflate(layoutInflater)

        setContentView(binding.root)

        //searchLocationList 불러오기
        loadList()

        //레이아웃 바꾸기
        changeLayout()


        //자동으로 포커싱, 키보드 올리기
        binding.searchEt.requestFocus()
        val inputMethodManager =
            this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        WindowInsetsControllerCompat(window, window.decorView).show(WindowInsetsCompat.Type.ime())


        //edittext에 글자가 입력될 경우
        binding.searchEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //구현 필요시 구현
                if (binding.searchEt.length() == 0) {
                    changeLayout()
                }
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.searchImageLayout.visibility = View.GONE
                binding.currentSearchLayout.visibility = View.GONE
                binding.locationListRv.visibility = View.VISIBLE
                binding.deleteSearchIv.visibility = View.VISIBLE
            }

            override fun afterTextChanged(s: Editable?) {
                if (binding.searchEt.length() == 0) {
                    val handler = Handler(Looper.getMainLooper())
                    val currentSearchAdapter = CurrentSearchAdapter(this@SearchLocationActivity2, searchLocationList)
                    handler.post {
                        // UI 업데이트 코드
                        binding.currentSearchLv.adapter = currentSearchAdapter
                        changeLayout()
                    }
                }
            }
        })


        //뒤로가기 버튼 구현
        binding.leftImage.setOnClickListener() {
            finish()
        }

        init()


        //최근 검색 기록 - 리스트뷰 이용
        val currentSearchAdapter = CurrentSearchAdapter(this, searchLocationList)
        binding.currentSearchLv.adapter = currentSearchAdapter


        //아이템 클릭시
        binding.currentSearchLv.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                val selectItem = parent.getItemAtPosition(position) as CurrentSearch
                val imageView = view.findViewById<ImageView>(R.id.delete_iv)
                //아이템 클릭시 지도 화면으로 넘어감
                Log.d("Retrofitdong1", locationdong.toString())
                val intent = Intent(this, SearchMapActivity2::class.java)
                intent.putExtra("location", selectItem.location)
                intent.putExtra("dong", locationdong)
                ContextCompat.startActivity(this, intent, null)


                //x 아이콘 클릭시 삭제
                val handler = Handler(Looper.getMainLooper())
                imageView.setOnClickListener {
                    handler.post {
                        // UI 업데이트 코드
                        searchLocationList.removeAt(position)
                        (binding.currentSearchLv.adapter as CurrentSearchAdapter).notifyDataSetChanged()
                        saveList(searchLocationList)
                        changeLayout()
                    }
                }
            }

        //검색중인 지역 x 버튼 누를때 처리
        binding.deleteSearchIv.setOnClickListener {
            binding.searchEt.text.clear()
            changeLayout()
        }


        // EditText 키보드 이벤트 -> searchLocationList 눌렀을 때 저장되도록 변경 완료
        /*binding.searchEt.setOnEditorActionListener { textView, actionId, _ ->
            // 키보드에서 완료 버튼이 눌렸을 때 처리
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                saveCurrentSearchLocation(textView.text.toString())
            }
            false // false로 해야 키패드가 닫힘
        }*/


    }
    //Shared Preferences
    // SharedPreferences에 데이터 저장하는 함수

    private fun saveList(locationList: ArrayList<CurrentSearch>) {
        stringPrefs = GsonBuilder().create().toJson(
            locationList,
            object : TypeToken<ArrayList<CurrentSearch>>() {}.type
        )
        mEditPrefs.putString("searchLocationList", stringPrefs) // SharedPreferences에 push
        mEditPrefs.apply() // SharedPreferences 적용
    }

    private fun loadList() {

        mPrefs = getSharedPreferences("pref_file", MODE_PRIVATE) // SharedPreferences 불러오기
        mEditPrefs = mPrefs.edit() // SharedPreferences Edit 선언
        stringPrefs = mPrefs.getString("searchLocationList", null)

        // SharedPreferences 데이터가 있으면 String을 ArrayList로 변환
        // fromJson → json 형태의 문자열을 명시한 객체로 변환(두번째 인자)
        if (stringPrefs != null && stringPrefs != "[]") {
            searchLocationList = GsonBuilder().create().fromJson(
                stringPrefs, object : TypeToken<ArrayList<CurrentSearch>>() {}.type
            )
        }
    }
    private fun changeLayout() {
        if (searchLocationList.size == 0) {
            binding.searchImageLayout.visibility = View.VISIBLE
            binding.currentSearchLayout.visibility = View.GONE
            binding.locationListRv.visibility = View.GONE
            binding.deleteSearchIv.visibility = View.GONE
        } else {
            binding.searchImageLayout.visibility = View.GONE
            binding.currentSearchLayout.visibility = View.VISIBLE
            binding.locationListRv.visibility = View.GONE
            binding.deleteSearchIv.visibility = View.GONE
        }
    }


    override fun onClick(locationId: Int, locationText: String) {
        Toast.makeText(this, "$locationText", Toast.LENGTH_SHORT).show()
        saveCurrentSearchLocation(locationText)
        val parts = locationText.split(" ")
        val dong = if (parts.size >= 3) parts[2] else ""
        Log.d("Retrofitdong1234", "$dong")
        // 이제 클릭된 위치의 동 이름을 사용할 수 있습니다.
    }


    //위치 검색 구현
    private fun init() {
        //initRetrofit()
        initSearch()
        getAllLocation()
        initRecyclerView()
    }

    private fun initRetrofit() {
        retrofit = RetrofitClient.getInstance()
        locationService = retrofit.create(LocationService::class.java)
    }

    private fun initSearch() {
        val search = this.findViewById<EditText>(R.id.search_et)
        search.addTextChangedListener(DynamicTextWatcher(
            afterChanged = { s ->
                val editTextValue = binding.searchEt.text.toString()
                locationAdapter.setEditTextValue(editTextValue)
                filter(s.toString())
            }
        ))
    }

    private fun initRecyclerView() {
        val rvLocation = this.findViewById<RecyclerView>(R.id.location_list_rv)
        locationAdapter = LocationAdapter2(mutableListOf(), this)
        rvLocation.adapter = locationAdapter
    }

    private fun getAllLocation() {
        val id: Int
        val sido: String
        val sigun: String
        val dongmyeon: String
        val li: String
        var line: String?
        val doList: List<Int> = listOf(
            R.raw.gangwondo,
            R.raw.gyeonggido,
            R.raw.gyeongsangnamdo,
            R.raw.gyeongsangbukdo,
            R.raw.gwangju,
            R.raw.daegu,
            R.raw.daejeon,
            R.raw.busan,
            R.raw.seoul,
            R.raw.sejong,
            R.raw.ulsan,
            R.raw.incheon,
            R.raw.jeollanamdo,
            R.raw.jeollabukdo,
            R.raw.jeju,
            R.raw.chungcheongnamdo,
            R.raw.chungcheongnamdo
        )


        try {
            // assets 폴더에 있는 locations.txt 파일 열기
            var inputStream: InputStream
            var reader: BufferedReader


            // 파일에서 한 줄씩 읽어와서 처리
            for (doName in doList) {
                inputStream = resources.openRawResource(doName)
                reader = BufferedReader(InputStreamReader(inputStream))

                line = reader.readLine()
                var id = 1

                while (line != null) {
                    // "|"를 기준으로 분리
                    val parts = line.split("|")

                    // LocationItem 객체 생성
                    val location = Location(
                        id = id++,
                        sido = parts.getOrNull(0) ?: "",
                        sigun = parts.getOrNull(1) ?: "",
                        dongmyeon = parts.getOrNull(2) ?: "",
                        li = parts.getOrNull(3) ?: ""
                    )
//                    locationdong = location.dongmyeon
//                    Log.d("Retrofitdong12", locationdong.toString())
                    locationList.add(location)
                    line = reader.readLine()
                }

                // 파일 닫기
                reader.close()
                inputStream.close()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // 지역 검색 filter
    private fun filter(keyword: String) {
        var filteredList = mutableListOf<Location>()
        Log.d("filter()", keyword + "/" + locationList.size.toString())

        val view: View = layoutInflater.inflate(R.layout.item_location, null)
        val textView: TextView = view.findViewById(R.id.location_tv)

        if (keyword.isNotBlank()) {
            for (location in locationList) {
                // 일치하는 부분이 있으면
                if (location.sido.contains(keyword) || location.sigun.contains(keyword) || location.dongmyeon.contains(
                        keyword
                    ) || location.li.contains(keyword) || (location.sido + location.sigun + location.dongmyeon + location.li).contains(
                        keyword
                    ) || (location.sido + " " + location.sigun + " " + location.dongmyeon + " " + location.li).contains(
                        keyword
                    )
                ) {
                    filteredList.add(location)
//                    locationdong = location.dongmyeon
//                    Log.d("Retrofitdong123", locationdong.toString())
                }
            }
        }

        locationAdapter.filterList(filteredList)

    }

    fun clearEt() {
        binding.searchEt.text.clear()
        changeLayout()
    }

    private fun saveCurrentSearchLocation(text : String) {
        searchLocationList.add(
            CurrentSearch(
                text.toString(),
                R.drawable.plus_circle
            )
        )
        saveList(searchLocationList)
        //changeLayout()
    }

}