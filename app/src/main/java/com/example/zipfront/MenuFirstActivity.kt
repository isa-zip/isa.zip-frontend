package com.example.zipfront

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.zipfront.databinding.ActivityMainBinding
import com.example.zipfront.databinding.FitstMenuLayoutBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MenuFirstActivity : AppCompatActivity() {
    lateinit var binding: FitstMenuLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FitstMenuLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.arrowbutton2.setOnClickListener {
            val intent = Intent(this, MenuProfileActivity::class.java)
            startActivity(intent)
        }

        binding.arrowbutton3.setOnClickListener {
            val intent = Intent(this, MenuCertifyActivity::class.java)
            startActivity(intent)
        }

        binding.imageView10.setOnClickListener {
            finish()
        }

    }
}