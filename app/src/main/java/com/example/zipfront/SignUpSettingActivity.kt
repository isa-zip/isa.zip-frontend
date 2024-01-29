package com.example.zipfront

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.zipfront.databinding.SignUpSettingBinding
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView

class SignUpSettingActivity : AppCompatActivity() {
    private lateinit var binding: SignUpSettingBinding
    private var isWritePasswordVisible = false
    private var isCheckPasswordVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SignUpSettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // EditText를 binding으로 참조
        val writePassword = binding.writePassword
        val checkPassword = binding.checkPassword
        val eyeImageView = binding.imageView23
        val eyeCheckImageView = binding.imageView24

        val imageButton5 = binding.imageButton5

        binding.imageView10.setOnClickListener {
            finish()
        }
        eyeImageView.setOnClickListener {
            togglePasswordVisibility(writePassword, eyeImageView)
        }
        eyeCheckImageView.setOnClickListener {
            togglePasswordVisibility(checkPassword, eyeCheckImageView)
        }

        eyeCheckImageView.setOnClickListener {
            togglePasswordVisibility(checkPassword, eyeCheckImageView)
        }

        eyeImageView.setOnClickListener {
            togglePasswordVisibility(writePassword, eyeImageView)
        }

        /*// checkPassword의 텍스트 변경 감지
        checkPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                // 텍스트가 변경될 때마다 버튼 배경 업데이트
                updateButtonBackground(checkPassword, imageButton5)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })*/

        imageButton5.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }
    }

    private fun updateButtonBackground(editText: EditText, button: ImageButton) {
        // 텍스트가 비어있으면 버튼 배경을 profileroudradius_gray로, 비어있지 않으면 profileroudradius_blue로 설정
        if (editText.text.isNotEmpty()) {
            button.setBackgroundResource(R.drawable.profileroudradius_blue)
        } else {
            button.setBackgroundResource(R.drawable.profileroudradius_gray)
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
        } else if (editText == binding.checkPassword) {
            isCheckPasswordVisible = !isCheckPasswordVisible
            if (isCheckPasswordVisible) {
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
