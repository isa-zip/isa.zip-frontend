package com.example.zipfront

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.zipfront.databinding.SignUpServiceBinding

class SignUpServiceActivity : AppCompatActivity() {
    private lateinit var binding: SignUpServiceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SignUpServiceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imageView10.setOnClickListener {
            finish()
        }
    }
}
