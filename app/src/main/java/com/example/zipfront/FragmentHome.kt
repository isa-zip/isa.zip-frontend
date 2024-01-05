package com.example.zipfront

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.zipfront.databinding.HomeFragmentBinding
import com.example.zipfront.databinding.SplashBinding
import com.google.android.material.tabs.TabLayoutMediator

class FragmentHome: Fragment() {
    lateinit var binding: HomeFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = HomeFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }
}