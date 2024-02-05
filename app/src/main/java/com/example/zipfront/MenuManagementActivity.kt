package com.example.zipfront

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.zipfront.databinding.FitstMenuManagementBinding

class MenuManagementActivity : AppCompatActivity() {
    lateinit var binding : FitstMenuManagementBinding
    private val list = ArrayList<ManagementData>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FitstMenuManagementBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.leftImage.setOnClickListener {
            finish()
        }


        //리사이클러뷰
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


        val recyclerView = RecyclerView(this)

        binding.addPropertyBtn.setOnClickListener {
            val intent = Intent(this, AdditionalActivity0::class.java)
            startActivity(intent)
        }

        checkList()
    }

    private fun checkList() {
        if (list.isEmpty()) {
            binding.noPropertyTv.visibility = View.VISIBLE
            binding.propertyManagementRv.visibility = View.GONE

        } else {
            binding.noPropertyTv.visibility = View.GONE
            binding.propertyManagementRv.visibility = View.VISIBLE
        }
    }

}