package com.example.zipfront

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_main)

        // ImageButton 참조
        val closeButton: ImageButton = findViewById(R.id.ic_x)

        // ImageButton 클릭 리스너 설정
        closeButton.setOnClickListener {
            // 앱을 종료합니다.
            finish()
        }

        val textView: TextView = findViewById(R.id.text_email) // 텍스트뷰 참조

        // 텍스트뷰 클릭 리스너 설정
        textView.setOnClickListener {
            // SignUpBeforeActivity로 이동하는 인텐트 생성
            val intent = Intent(this, SignUpBeforeActivity::class.java)
            startActivity(intent)
        }
    }

    // 이 메서드는 ImageButton이나 TextView가 클릭되었을 때 호출됩니다.
    fun onSignUpClicked(view: View) {
        // Sign Up 화면으로 전환하는 인텐트를 생성합니다.
        val intent = Intent(this, SignUpBeforeActivity::class.java)
        startActivity(intent)
    }
}
