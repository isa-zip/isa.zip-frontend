package com.example.zipfront

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.zipfront.databinding.MatchingActivityBinding
import com.google.android.material.tabs.TabLayoutMediator

class MatchingActivity: AppCompatActivity() {
    lateinit var binding: MatchingActivityBinding
    private val information = arrayListOf("매칭 중", "매칭 완료")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MatchingActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomSheetFragment = MatchingBottomsheetFragment(applicationContext)

        binding.imageButton3.setOnClickListener {
            bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
        }

        binding.imageView10.setOnClickListener {
            finish()
        }

        initViewPager()
    }

    private fun initViewPager() {

        val matchingAdapter = MatchingAdapter(this)
        binding.fragmentContainer.adapter = matchingAdapter
        TabLayoutMediator(binding.tabs, binding.fragmentContainer) { tab, position ->
            tab.text = information[position]
        }.attach()
    }
}