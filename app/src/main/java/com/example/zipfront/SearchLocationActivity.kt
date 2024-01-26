package com.example.zipfront

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
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
import com.google.gson.reflect.TypeToken

class SearchLocationActivity :AppCompatActivity(), OnItemClick{
    lateinit var binding : ActivitySearchlocationBinding
    private val gson = Gson()

    //최근 검색기록
    private var searchLocationList = arrayListOf<CurrentSearch>(
    CurrentSearch("테스트1", R.drawable.plus_circle),
    CurrentSearch("테스트2", R.drawable.plus_circle),
    CurrentSearch("테스트3", R.drawable.plus_circle)
    )



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchlocationBinding.inflate(layoutInflater)

        setContentView(binding.root)


        //레이아웃 바꾸기
        changeLayout()

        //searchLocationList = loadList()

        //자동으로 포커싱, 키보드 올리기
        binding.searchEt.requestFocus()
        val inputMethodManager = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        WindowInsetsControllerCompat(window, window.decorView).show(WindowInsetsCompat.Type.ime())


        //edittext에 글자가 입력될 경우
        binding.searchEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //구현 필요시 구현
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
                    //saveList(searchLocationList)
                }
            }

        }



    }
    //Shared Preferences
    // SharedPreferences에 데이터 저장하는 함수

    private fun saveList(userList: ArrayList<CurrentSearch>) {
        val json = gson.toJson(userList)
        MyPref.prefs.setString("searchLocationList", json)
    }

    private fun loadList(): ArrayList<CurrentSearch> {
        val json = MyPref.prefs.getString("searchLocationList", "")
        return if (json.isNullOrBlank()) {
            ArrayList()
        } else {
            val type = object : TypeToken<ArrayList<CurrentSearch>>() {}.type
            gson.fromJson(json, type)
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