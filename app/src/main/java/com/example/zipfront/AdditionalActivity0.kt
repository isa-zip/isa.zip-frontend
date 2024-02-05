package com.example.zipfront

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.zipfront.databinding.ActivityAdditional0Binding
import com.example.zipfront.databinding.ActivityAdditional2Binding

class AdditionalActivity0: AppCompatActivity() {
    lateinit var binding: ActivityAdditional0Binding
    private lateinit var adapter: AdditionalAdapter
    private val information = arrayListOf("아이템 1", "아이템 2")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdditional0Binding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView(information)
    }

    private fun setupRecyclerView(informationList: List<String>) {
        // RecyclerView의 레이아웃 매니저 설정
        binding.optionRv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        // RecyclerView의 어댑터 설정
        adapter = AdditionalAdapter(this, informationList)
        binding.optionRv.adapter = adapter
    }
}