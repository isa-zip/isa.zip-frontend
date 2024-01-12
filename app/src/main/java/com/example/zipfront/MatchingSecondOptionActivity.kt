package com.example.zipfront

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.zipfront.databinding.ActivityMainBinding
import com.example.zipfront.databinding.ActivityMatchingoptionselectBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MatchingSecondOptionActivity : AppCompatActivity() {
    lateinit var binding: ActivityMatchingoptionselectBinding
    private lateinit var adapter: OuterSecondoptionAdapter
    private lateinit var outerItemList2: List<OuterItem2>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMatchingoptionselectBinding.inflate(layoutInflater)

        val title = intent.getStringExtra("title")
        if (!title.isNullOrBlank()) {
            binding.textView9.text = title
        }

        // innerItems를 받아서 RecyclerView 설정
        val innerItems = intent.getStringArrayListExtra("innerItems")
        if (innerItems.isNullOrEmpty()) {
            binding.optionRv.visibility = View.GONE
        } else {
            setupRecyclerView(innerItems)
        }
        binding.imageView10.setOnClickListener{
            finish()
        }
        setContentView(binding.root)
    }

    private fun setupRecyclerView(innerItems: List<String>) {
        // RecyclerView의 레이아웃 매니저 설정
        binding.optionRv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        // RecyclerView의 어댑터 설정
        adapter = OuterSecondoptionAdapter(innerItems)
        binding.optionRv.adapter = adapter
    }

    data class OuterItem2(val title: String, val innerItemList: List<String>? = null) {
        fun getItemCount(): Int {
            return innerItemList?.size ?: 0
        }
    }
}