package com.example.zipfront

import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.zipfront.databinding.SignInBinding

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: SignInBinding
    private var isWritePasswordVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val writePassword = binding.writePassword
        val eyeImageView = binding.imageView24

        val imageButton5 = binding.imageButton5
        val text2 = binding.text2
        val imageButton1_1 = binding.imageButton

        binding.imageView10.setOnClickListener {
            finish()
        }
        eyeImageView.setOnClickListener {
            togglePasswordVisibility(writePassword, eyeImageView)
        }

        // imageView10을 클릭했을 때 SignUpBeforeActivity로 화면 전환
        binding.imageView10.setOnClickListener {
            val intent = Intent(this, SignUpBeforeActivity::class.java)
            startActivity(intent)
        }
        // imageButton5를 클릭했을 때 MainActivity로 이동
        imageButton5.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        // text2를 클릭했을 때 SignUpSettingActivity로 화면 전환
        text2.setOnClickListener {
            val intent = Intent(this, SignUpSettingActivity::class.java)
            startActivity(intent)
        }

        // imageButton1_1를 클릭했을 때 SignUpSettingActivity로 화면 전환
        imageButton1_1.setOnClickListener {
            val intent = Intent(this, SignUpSettingActivity::class.java)
            startActivity(intent)
        }
    }

    private fun togglePasswordVisibility(editText: EditText, imageView: ImageView) {
        // 비밀번호 보이기/감추기 기능 구현
        if (editText == binding.writePassword) {
            isWritePasswordVisible = !isWritePasswordVisible
            if (isWritePasswordVisible) {
                // 비밀번호를 보이도록 변경
                editText.transformationMethod = PasswordTransformationMethod.getInstance()
                imageView.setImageResource(R.drawable.eye_off)
            } else {
                // 비밀번호를 감추도록 변경
                editText.transformationMethod = HideReturnsTransformationMethod.getInstance()
                imageView.setImageResource(R.drawable.eye_on)
            }
        }
        // 커서를 마지막으로 이동하여 비밀번호 글자가 보이도록 함
        editText.setSelection(editText.text.length)
    }
}
