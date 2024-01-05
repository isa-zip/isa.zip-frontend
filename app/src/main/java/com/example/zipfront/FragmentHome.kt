package com.example.zipfront

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.zipfront.databinding.HomeFragmentBinding
import com.example.zipfront.databinding.SplashBinding
import com.google.android.material.tabs.TabLayoutMediator

class FragmentHome: Fragment() {
    lateinit var binding: HomeFragmentBinding
    private lateinit var adapter: PoseAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = HomeFragmentBinding.inflate(inflater, container, false)
        // RecyclerView 찾기

        // RecyclerView에 사용할 아이템 리스트 생성
        val itemList = listOf("아이템 1", "아이템 2", "아이템 3")

        // RecyclerView에 아이템 리스트 설정
        setupRecyclerView(itemList)

        val bannerAdapter = BannerAdapter(this)
        bannerAdapter.addFragment(BannerFragment(R.drawable.banner))
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp))

        binding.viewPager.adapter=bannerAdapter
        binding.viewPager.orientation= ViewPager2.ORIENTATION_HORIZONTAL

        return binding.root
    }

    private fun setupRecyclerView(itemList: List<String>) {
        // RecyclerView의 레이아웃 매니저 설정
        binding.poseRv.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

        // RecyclerView의 어댑터 설정
        adapter = PoseAdapter(itemList)
        binding.poseRv.adapter = adapter
    }
}