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
    private val information = arrayListOf("매칭 요청", "매칭 완료")
    val REQUEST_CODE_OPTION = 1
    private val REQUEST_CODE_CLOSE = 2
    private lateinit var viewPager: ViewPager2
    internal val matchingAdapter: MatchingAdapter by lazy {
        MatchingAdapter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MatchingActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val bottomSheetFragment = MatchingBottomsheetFragment(applicationContext, matchingAdapter)

        binding.imageButton3.setOnClickListener {
//            bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
            val intent = Intent(this, MatchingOptionActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_OPTION)
        }

        binding.imageView10.setOnClickListener {
            finish()
        }

        initViewPager()
        matchingAdapter.initFragments()
    }

    private fun initViewPager() {
        matchingAdapter.initFragments()

        viewPager = binding.fragmentContainer
        viewPager.adapter = matchingAdapter

        // Check if the adapter is up to date
        val currentAdapter = viewPager.adapter
        if (currentAdapter == matchingAdapter) {
            Log.d("ViewPagerUpdate", "Adapter is up to date.")
        } else {
            Log.d("ViewPagerUpdate", "Adapter needs to be updated.")
        }

        TabLayoutMediator(binding.tabs, viewPager) { tab, position ->
            tab.text = information[position]
        }.attach()
    }
    // MatchingActivity 내부
    internal fun updateFragmentAndViewPager() {
        matchingAdapter.restoreFragment(viewPager.currentItem, REQUEST_CODE_OPTION)
//        matchingAdapter.notifyDataSetChanged()
        initViewPager()
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        Log.d("MatchingStillFragment3", "$resultCode")
        if (resultCode == Activity.RESULT_OK) {
            // MatchingOptionActivity에서 돌아왔을 때의 처리
            val customData = data?.getStringExtra("EXTRA_CUSTOM_DATA")
            Log.d("MatchingStillFragment2", "Received custom data from MatchingOptionActivity: $customData")
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
    // "btnClose"를 눌렀을 때 requestCode를 MatchingAdapter로 전달하는 메서드
    fun closeBottomSheetWithCode(requestCode: Int) {
        // 현재 표시 중인 Fragment의 위치에 따라 처리
        Log.d("MatchingAct", "$requestCode")

        val currentItem = viewPager.currentItem
        when (currentItem) {
            0 -> {
                // "매칭 요청" Fragment인 경우
                matchingAdapter.closeBottomSheetWithCode(currentItem, requestCode)

                viewPager.adapter = matchingAdapter
            }
            // 여기에 다른 Fragment에 대한 처리를 추가할 수 있습니다.
        }
    }
}