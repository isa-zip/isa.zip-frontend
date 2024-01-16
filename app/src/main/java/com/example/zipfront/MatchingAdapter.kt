package com.example.zipfront

import android.util.Log
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
        Log.d("MatchingAct21", "Fragments initialized: $fragments")
    }

    // 현재 Fragment를 원래의 MatchingStillFragment로 복원하는 메서드
    fun restoreFragment(fragmentPosition: Int, requestCode: Int) {
        Log.d("MatchingAct22", "$requestCode,$fragmentPosition")
        when (fragmentPosition) {
            0 -> {
                fragments[fragmentPosition] = MatchingStillFragment()
                Log.d("MatchingAct32", "After - Fragments: $fragments")
                notifyDataSetChanged()
            }
            // 다른 Fragment에 대한 경우도 필요한 경우 추가하세요.
        }
    }

    // "btnClose"를 눌렀을 때 requestCode를 처리하는 메서드
    fun closeBottomSheetWithCode(fragmentPosition: Int, requestCode: Int) {
        Log.d("MatchingAct2", "$requestCode,$fragmentPosition")
        when (fragmentPosition) {
            0 -> {
                // "매칭 요청" Fragment인 경우
                fragments[fragmentPosition] = MatchingStillUploadFragment()
                Log.d("MatchingAct3", "After - Fragments: $fragments")
                notifyDataSetChanged()

            }
            // 여기에 다른 Fragment에 대한 처리를 추가할 수 있습니다.
        }
    }

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment {
        // 이제 여기에서 fragments 리스트에서 Fragment를 가져오도록 수정
        return fragments[position]
    }
}