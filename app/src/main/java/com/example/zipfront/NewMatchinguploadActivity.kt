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
                listOf("정태연", "한승연", "김가연")
            ),
            OuterUploadItem(
                "동선동",
                listOf("강바다", "강다희", "유미라", "한수찬")
            ),
            OuterUploadItem("오목교"),
            OuterUploadItem(
                "성신여대",
                listOf("차현수", "장은진", "메로나", "배고파", "집갈래")
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