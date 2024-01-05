package com.example.zipfront

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.zipfront.databinding.FragmentBannerBinding

class BannerFragment(val imageres: Int) : Fragment() {

    lateinit var binding: FragmentBannerBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentBannerBinding.inflate(inflater,container,false)

        binding.bannerImage.setImageResource(imageres)
        return binding.root
    }
}