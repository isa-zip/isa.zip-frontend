package com.example.zipfront

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.zipfront.databinding.MatchingActivityBinding
import com.google.android.material.tabs.TabLayoutMediator

class MatchingActivity: AppCompatActivity() {
    lateinit var binding: MatchingActivityBinding
    private val information = arrayListOf("매칭 중", "매칭 완료")
    val REQUEST_CODE_OPTION = 1
    private lateinit var viewPager: ViewPager2
    private lateinit var matchingAdapter: MatchingAdapter

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
        matchingAdapter.initFragments()
    }

    private fun initViewPager() {
        matchingAdapter = MatchingAdapter(this)
        // 어댑터 초기화 메서드 호출
        matchingAdapter.initFragments()

        viewPager = binding.fragmentContainer
        viewPager.adapter = matchingAdapter

        TabLayoutMediator(binding.tabs, viewPager) { tab, position ->
            tab.text = information[position]
        }.attach()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("MatchingStillFragment2", "requestCode: $requestCode, resultCode: $resultCode")

        if (resultCode == Activity.RESULT_OK) {
            // MatchingOptionActivity에서 돌아왔을 때의 처리
            // 여기서 필요한 작업을 수행합니다.
            val fragment = matchingAdapter.fragments[viewPager.currentItem]

            if (fragment is MatchingStillFragment) {
                fragment.onActivityResult(
                    REQUEST_CODE_OPTION,
                    resultCode,
                    data
                )
            }
        }
    }
}