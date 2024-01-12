package com.example.zipfront

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class MatchingAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    val fragments: MutableList<Fragment> = mutableListOf()

    // 초기화 메서드 추가
    fun initFragments() {
        fragments.clear()
        fragments.add(MatchingStillFragment())
        fragments.add(MatchingCompleteFragment())
    }

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment {
        // 이제 여기에서 fragments 리스트에서 Fragment를 가져오도록 수정
        return fragments[position]
    }
}