package com.example.zipfront

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_main)

        val textView: TextView = findViewById(R.id.text_email) // 텍스트뷰 참조

        // 텍스트뷰 클릭 리스너 설정
        textView.setOnClickListener {
            // SignUpBeforeActivity로 이동하는 인텐트 생성
            val intent = Intent(this, SignUpBeforeActivity::class.java)
            startActivity(intent)
        }

        val loginEmailButton: Button = findViewById(R.id.login_email)

        // login_email ImageButton 클릭 리스너 설정
        loginEmailButton.setOnClickListener {
            // SignInActivity로 이동하는 인텐트 생성
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }
    }
}
