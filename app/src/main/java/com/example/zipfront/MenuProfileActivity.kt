package com.example.zipfront

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.InputType
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.zipfront.databinding.FitstMenuLayoutBinding
import com.example.zipfront.databinding.FitstMenuProfileBinding

class MenuProfileActivity : AppCompatActivity() {
    lateinit var binding: FitstMenuProfileBinding
    private val PICK_IMAGE_REQUEST = 1 // 이미지를 선택하는 요청 코드
    var isPasswordVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FitstMenuProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // EditText를 binding으로 참조
        val textPassword = binding.textPassword

        binding.imageView18.setOnClickListener {
            openGallery()
        }

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

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            val selectedImageUri: Uri = data.data!!
            // button2의 view를 gone으로 설정
            binding.soldoutlayout.visibility = View.GONE

            // 이제 selectedImageUri를 사용하여 이미지를 처리하고, ImageView에 설정할 수 있습니다.
            // 예시로 Glide 사용하는 방법:
            Glide.with(this)
                .load(selectedImageUri)
                .into(binding.imageView18)
        }
    }
}