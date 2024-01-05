package com.example.zipfront

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.zipfront.databinding.MatchingstillLayoutBinding

class MatchingStillFragment : Fragment() {
    lateinit var binding: MatchingstillLayoutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= MatchingstillLayoutBinding.inflate(inflater,container,false)

        return binding.root
    }
}