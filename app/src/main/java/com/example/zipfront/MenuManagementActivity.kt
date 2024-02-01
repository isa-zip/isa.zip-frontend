package com.example.zipfront

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.zipfront.databinding.FitstMenuManagementBinding

class MenuManagementActivity : AppCompatActivity() {
    lateinit var binding : FitstMenuManagementBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FitstMenuManagementBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //리사이클러뷰
        val list = ArrayList<ManagementData>()
        list.add(ManagementData(R.drawable.imgbed, "전세 1억 5천만원", "37.29m", "3층", "관리비 3만", "동작구 상도동", "숭실대입구 4번출구 바로앞"))
        list.add(ManagementData(R.drawable.imgbed, "전세 2억 5천만원", "37.29m", "3층", "관리비 3만", "동작구 상도동", "숭실대입구 4번출구 바로앞"))
        list.add(ManagementData(R.drawable.imgbed, "전세 3억 5천만원", "37.29m", "3층", "관리비 3만", "동작구 상도동", "숭실대입구 4번출구 바로앞"))
        list.add(ManagementData(R.drawable.imgbed, "전세 3억 5천만원", "37.29m", "3층", "관리비 3만", "동작구 상도동", "숭실대입구 4번출구 바로앞"))
        list.add(ManagementData(R.drawable.imgbed, "전세 4억 5천만원", "37.29m", "3층", "관리비 3만", "동작구 상도동", "숭실대입구 4번출구 바로앞"))
        list.add(ManagementData(R.drawable.imgbed, "전세 5억 5천만원", "37.29m", "3층", "관리비 3만", "동작구 상도동", "숭실대입구 4번출구 바로앞"))
        list.add(ManagementData(R.drawable.imgbed, "전세 6억 5천만원", "37.29m", "3층", "관리비 3만", "동작구 상도동", "숭실대입구 4번출구 바로앞"))
        list.add(ManagementData(R.drawable.imgbed, "전세 7억 5천만원", "37.29m", "3층", "관리비 3만", "동작구 상도동", "숭실대입구 4번출구 바로앞"))


        binding.propertyManagementRv.layoutManager = LinearLayoutManager(this)
        binding.propertyManagementRv.adapter = ManagementAdapter(list)
        binding.propertyManagementRv.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))

    }
}