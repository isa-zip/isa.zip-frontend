package com.example.zipfront

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.zipfront.databinding.FragmentBannerBinding
import com.example.zipfront.databinding.FragmentPropertyBannerBinding
import com.squareup.picasso.RequestCreator

class PropertyBannerFragment(val imageres: RequestCreator) : Fragment() {

    lateinit var binding: FragmentPropertyBannerBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentPropertyBannerBinding.inflate(inflater,container,false)

        imageres.into(binding.bannerImage)
        return binding.root
    }
}