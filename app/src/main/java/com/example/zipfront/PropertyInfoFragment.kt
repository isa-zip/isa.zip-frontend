package com.example.zipfront

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.zipfront.databinding.FragmentPropertyinfoBinding
import com.example.zipfront.databinding.MatchingcompleteRecyclerviewBinding

class PropertyInfoFragment : Fragment() {

    lateinit var binding: FragmentPropertyinfoBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPropertyinfoBinding.inflate(inflater, container, false)

        //이미지 ViewPager
        val propertyImageAdater = PropertyImageAdater(this)
        propertyImageAdater.addFragment(PropertyBannerFragment(R.drawable.img_1))
        propertyImageAdater.addFragment(PropertyBannerFragment(R.drawable.img_1))
        propertyImageAdater.addFragment(PropertyBannerFragment(R.drawable.img_1))

        binding.propertyImgVp.adapter = propertyImageAdater
        binding.propertyImgVp.orientation= ViewPager2.ORIENTATION_HORIZONTAL



        return binding.root
    }
}