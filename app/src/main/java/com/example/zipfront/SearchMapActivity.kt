package com.example.zipfront

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.zipfront.databinding.ActivitySearchlocationBinding
import com.example.zipfront.databinding.ActivitySearchmapBinding

class SearchMapActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySearchmapBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchmapBinding.inflate(layoutInflater)

        //editText text 바꾸기
        val locationData = intent.getStringExtra("location")
        binding.searchEt.setText(locationData)

        setContentView(binding.root)


        binding.leftImage.setOnClickListener {
            finish()
        }

        binding.deleteSearchIv.setOnClickListener {
            finish()
        }
    }
}