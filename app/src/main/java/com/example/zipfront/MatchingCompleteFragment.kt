package com.example.zipfront

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.zipfront.databinding.MatchingcompleteLayoutBinding

class MatchingCompleteFragment: Fragment() {
    lateinit var binding: MatchingcompleteLayoutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= MatchingcompleteLayoutBinding.inflate(inflater,container,false)

        return binding.root
    }
}