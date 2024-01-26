package com.example.zipfront

import android.content.Context
import android.content.SharedPreferences
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toDrawable
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.zipfront.databinding.ActivitySearchlocationBinding
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

class SearchLocationActivity :AppCompatActivity(), OnItemClick{
    lateinit var binding : ActivitySearchlocationBinding
    private val gson = Gson()

    private lateinit var mPrefs : SharedPreferences
    private lateinit var mEditPrefs: SharedPreferences.Editor

    //최근 검색기록
    private var searchLocationList = ArrayList<CurrentSearch>()
    private var stringPrefs : String? = null



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
        val inputMethodManager = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        WindowInsetsControllerCompat(window, window.decorView).show(WindowInsetsCompat.Type.ime())


        //edittext에 글자가 입력될 경우
        binding.searchEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //구현 필요시 구현
                changeLayout()
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.searchImageLayout.visibility = View.GONE
                binding.currentSearchLayout.visibility = View.GONE
                binding.locationListRv.visibility = View.VISIBLE
            }

            override fun afterTextChanged(s: Editable?) {
                if (binding.searchEt.length() == 0){
                    changeLayout()
                }
            }
        })




        //뒤로가기 버튼 구현
        binding.leftImage.setOnClickListener() {
            finish()
        }


        //리사이클러뷰와 연결 - 동까지 뜨도록
        val location1 = Location(id = 1, sido = "Seoul", sigun = "Gangnam", dongmyeon = "A-dong", li = "B-li")
        val location2 = Location(id = 2, sido = "Busan", sigun = "Haeundae", dongmyeon = "C-dong", li = "D-li")

        // ArrayList 생성 및 데이터 추가
        val locationList: ArrayList<Location> = ArrayList()
        locationList.add(location1)
        locationList.add(location2)

        binding.locationListRv.layoutManager = LinearLayoutManager(this)
        binding.locationListRv.adapter = LocationAdapter(locationList, this)
        binding.locationListRv.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))



        //최근 검색 기록 - 리스트뷰 이용

        val currentSearchAdapter = CurrentSearchAdapter(this, searchLocationList)
        binding.currentSearchLv.adapter = currentSearchAdapter


        //아이템 클릭시
        binding.currentSearchLv.onItemClickListener = AdapterView.OnItemClickListener{ parent, view, position, id ->
            val selectItem = parent.getItemAtPosition(position) as CurrentSearch
            val imageView = view.findViewById<ImageView>(R.id.delete_iv)

            val handler = Handler(Looper.getMainLooper())
            imageView.setOnClickListener {
                handler.post {
                    // UI 업데이트 코드
                    searchLocationList.removeAt(position)
                    (binding.currentSearchLv.adapter as CurrentSearchAdapter).notifyDataSetChanged()
                    saveList(searchLocationList)
                }
            }

        }

        // EditText 키보드 이벤트
        binding.searchEt.setOnEditorActionListener { textView, actionId, _ ->
            // 키보드에서 완료 버튼이 눌렸을 때 처리
            if(actionId == EditorInfo.IME_ACTION_DONE){
                searchLocationList.add(CurrentSearch(textView.text.toString(), R.drawable.plus_circle))
                saveList(searchLocationList)
            }
            false // false로 해야 키패드가 닫힘
        }



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
        if(stringPrefs != null && stringPrefs != "[]") {
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
        }
        else {
            binding.searchImageLayout.visibility = View.GONE
            binding.currentSearchLayout.visibility = View.VISIBLE
            binding.locationListRv.visibility = View.GONE
        }
    }



    override fun onClick(locationId: Int) {
        /*setFragmentResultListener("requestInfo") { requestKey, bundle ->
            val email = bundle.getString("email")!!
            val nick = bundle.getString("nick")!!
            val password = bundle.getString("password")

            signUp(email, nick, password, locationId)
        }*/
    }

}