package com.example.zipfront

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.zipfront.databinding.ActivityMainBinding
import com.example.zipfront.databinding.FitstMenuCertifyBinding
import com.example.zipfront.databinding.FitstMenuLayoutBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MenuCertifyActivity : AppCompatActivity() {
    lateinit var binding: FitstMenuCertifyBinding
    private val PICK_IMAGE_REQUEST = 1 // 이미지를 선택하는 요청 코드

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FitstMenuCertifyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button2.setOnClickListener {
            openGallery()
        }
        binding.button3.setOnClickListener {
            showCustomDialog()
        }
        binding.imageView10.setOnClickListener {
            finish()
        }
//        binding.imageupload.setOnClickListener {
//            openGallery()
//        }
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
            binding.button2.visibility = View.GONE

            // imageupload의 visibility를 visible로 변경
            binding.imageupload.visibility = View.VISIBLE
            binding.button3.setBackgroundResource(R.drawable.profileroudradius_blue)

            // 이제 selectedImageUri를 사용하여 이미지를 처리하고, ImageView에 설정할 수 있습니다.
            // 예시로 Glide 사용하는 방법:
            Glide.with(this)
                .load(selectedImageUri)
                .into(binding.imageupload)
        }
    }
    private fun showCustomDialog() {
        // 다이얼로그 레이아웃을 inflate
        val dialogView = layoutInflater.inflate(R.layout.certify_dialogview, null)

        // AlertDialog.Builder를 사용하여 다이얼로그 생성
        val builder = AlertDialog.Builder(this)
        builder.setView(dialogView)
        // AlertDialog 생성
        val alertDialog: AlertDialog = builder.create()

        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        // Window 속성을 사용하여 크기 조절
        val params =  alertDialog.window?.attributes
        params?.width = 296
        params?.height = 149
        alertDialog.window?.attributes = params

        // 다이얼로그 내부의 ImageButton 참조
        val cancelButton = dialogView.findViewById<Button>(R.id.button3)

        // 취소 버튼 클릭 리스너 설정
        cancelButton.setOnClickListener {
            // 취소 버튼을 눌렀을 때 수행할 동작
            finish()
            alertDialog.dismiss() // 다이얼로그 닫기
            // 추가적인 작업 수행 가능
        }

        // AlertDialog 표시
        alertDialog.show()
    }
}