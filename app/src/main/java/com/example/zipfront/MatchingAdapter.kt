package com.example.zipfront

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class MatchingAdapter (activity: FragmentActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int =2

    override fun createFragment(position: Int): Fragment {
        return when(position)
        {
            0-> MatchingStillFragment()
            else-> MatchingCompleteFragment()
        }
    }
}