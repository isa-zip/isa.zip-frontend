package com.example.zipfront

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.zipfront.databinding.ActivityMainBinding
import com.example.zipfront.databinding.ActivityUpdatingpropertyBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class NewMatchinguploadActivity : AppCompatActivity() {
    lateinit var binding: ActivityUpdatingpropertyBinding
    private lateinit var outeruploadItemList: List<OuterUploadItem>
    private lateinit var adapter: OuteruploadAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdatingpropertyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textView10.visibility = View.GONE
        binding.stiillupload.visibility = View.VISIBLE

        binding.imageView10.setOnClickListener{
            finish()
        }

        outeruploadItemList = listOf(
            OuterUploadItem(
                "상도동",
                listOf("내부 아이템 1-1", "내부 아이템 1-2", "내부 아이템 1-3")
            ),
            OuterUploadItem(
                "동선동",
                listOf("내부 아이템 2-1", "내부 아이템 2-2", "내부 아이템 2-3", "내부 아이템 2-4")
            ),
            OuterUploadItem("오목교"),
            OuterUploadItem(
                "성신여대",
                listOf("내부 아이템 4-1", "내부 아이템 4-2", "내부 아이템 4-3", "내부 아이템 4-4", "내부 아이템 4-5")
            )
        )
        setupRecyclerView(outeruploadItemList)

        binding.textView20.text = "${outeruploadItemList.size}건"
    }

    private fun setupRecyclerView(outerItemList: List<OuterUploadItem>) {
        // RecyclerView의 레이아웃 매니저 설정
        binding.optionRv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        // RecyclerView의 어댑터 설정
        adapter = OuteruploadAdapter(outerItemList)
        binding.optionRv.adapter = adapter
    }
    data class OuterUploadItem(val title: String, val innerItemList: List<String>? = null) {
        fun getItemCount(): Int {
            return innerItemList?.size ?: 0
        }
    }
}