package com.example.zipfront

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.MotionEvent
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.zipfront.databinding.FitstMenuLayoutBinding
import com.example.zipfront.databinding.FitstMenuProfileBinding

class MenuProfileActivity : AppCompatActivity() {
    lateinit var binding: FitstMenuProfileBinding
    var isPasswordVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FitstMenuProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // EditText를 binding으로 참조
        val textPassword = binding.textPassword

        textPassword.setOnTouchListener { _, event ->
            // 오른쪽 drawable 영역을 클릭했을 때
            if (event.action == MotionEvent.ACTION_UP &&
                event.rawX >= textPassword.right - textPassword.compoundDrawables[2].bounds.width()
            ) {
                togglePasswordVisibility(textPassword)
                true
            } else {
                false
            }
        }
    }

    private fun togglePasswordVisibility(editText: EditText) {
        // 비밀번호 보이기/감추기 기능 구현
        isPasswordVisible = !isPasswordVisible

        if (isPasswordVisible) {
            // 비밀번호를 보이도록 변경
            editText.transformationMethod = HideReturnsTransformationMethod.getInstance()
            editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.eye_on, 0)
        } else {
            // 비밀번호를 감추도록 변경
            editText.transformationMethod = PasswordTransformationMethod.getInstance()
            editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.eye_off, 0)
        }

        // 커서를 마지막으로 이동하여 비밀번호 글자가 보이도록 함
        editText.setSelection(editText.text.length)
    }
}