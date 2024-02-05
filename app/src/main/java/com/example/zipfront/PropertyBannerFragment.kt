package com.example.zipfront

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.zipfront.databinding.FragmentBannerBinding
import com.example.zipfront.databinding.FragmentPropertyBannerBinding

class PropertyBannerFragment(val imageres: Int) : Fragment() {

    lateinit var binding: FragmentPropertyBannerBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentPropertyBannerBinding.inflate(inflater,container,false)

        binding.bannerImage.setImageResource(imageres)
        return binding.root
    }
}