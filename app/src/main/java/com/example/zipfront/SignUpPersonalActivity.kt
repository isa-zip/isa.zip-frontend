package com.example.zipfront

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.zipfront.databinding.SignUpPersonalBinding

class SignUpPersonalActivity : AppCompatActivity() {
    private lateinit var binding: SignUpPersonalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SignUpPersonalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // imageView10을 클릭했을 때 SignUpBeforeActivity로 화면 전환
        binding.imageView10.setOnClickListener {
            val intent = Intent(this, SignUpBeforeActivity::class.java)
            startActivity(intent)
        }
    }
}
