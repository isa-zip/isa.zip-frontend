package com.example.zipfront

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.zipfront.databinding.ActivityAdditional2Binding

class AdditionalActivity2 : AppCompatActivity() {
    lateinit var binding: ActivityAdditional2Binding
    private lateinit var adapter: PictuerAdapter
    private val PICK_IMAGE_REQUEST = 1 // 이미지를 선택하는 요청 코드
    private var isBlueButtonClicked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdditional2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.relativeLayout.setOnClickListener {
            openGallery()
        }

        // 이미지 버튼 클릭 이벤트 설정
        binding.imageButton5.setOnClickListener {
            // 이미지가 btn_next_di_blue인 경우에만 AdditonalActivity3로 이동
            if (isBlueButtonClicked) {
                val intent = Intent(this, AdditonalActivity3::class.java)
                startActivity(intent)
            }
        }

        setupRecyclerView()

        val imageView10: ImageView = findViewById(R.id.imageView10)
        imageView10.setOnClickListener {
            finish()
        }

        // AdditionalAdapter로부터 가져온 도로명 주소 값만 binding.road.text에 설정
        val selectedItem = intent.getStringExtra("selectedItem")
        val items = selectedItem?.split(", ") // 쉼표를 기준으로 문자열을 분리
        if (items != null && items.size >= 2) { // 최소한 2개의 요소가 있는지 확인
            binding.textAddress.text = items[0] // 도로명 주소
        }

        // EditText에 텍스트 입력 시 이미지 버튼 이미지 변경 이벤트 설정
        binding.text2.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                // EditText에 텍스트가 입력되면 이미지 버튼의 이미지 변경
                if (s.toString().isNotEmpty()) {
                    // 버튼 이미지를 btn_next_di_blue로 변경
                    binding.imageButton5.setImageResource(R.drawable.btn_next_di_blue)
                    isBlueButtonClicked = true
                } else {
                    // 버튼 이미지를 기본 이미지로 변경
                    binding.imageButton5.setImageResource(R.drawable.btn_next_di)
                    isBlueButtonClicked = false
                }
            }
        })
    }

    private fun setupRecyclerView() {
        // RecyclerView의 레이아웃 매니저 설정
        binding.poseRv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        // RecyclerView의 어댑터 설정
        adapter = PictuerAdapter()
        binding.poseRv.adapter = adapter
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            val selectedImageUri: Uri = data.data!!

            // 이미지를 어댑터에 추가하고 갱신
            adapter.addImage(selectedImageUri)
        }
    }
}
