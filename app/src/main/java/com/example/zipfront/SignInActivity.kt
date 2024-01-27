package com.example.zipfront

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.zipfront.databinding.SignInBinding
import com.example.zipfront.databinding.SignUpSettingBinding

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: SignInBinding

    private lateinit var imageButton5: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        imageButton5 = findViewById(R.id.imageButton1_1)

        // imageView10을 클릭했을 때 SignUpBeforeActivity로 화면 전환
        binding.imageView10.setOnClickListener {
            val intent = Intent(this, SignUpBeforeActivity::class.java)
            startActivity(intent)
        }
        imageButton5.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
