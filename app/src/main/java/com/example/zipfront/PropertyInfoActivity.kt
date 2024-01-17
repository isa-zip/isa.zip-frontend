package com.example.zipfront

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.zipfront.databinding.ActivityPropertyinfoBinding

class PropertyInfoActivity:AppCompatActivity() {
    lateinit var binding: ActivityPropertyinfoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPropertyinfoBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }
}