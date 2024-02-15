package com.example.zipfront

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.zipfront.databinding.ActivityAdditional2Binding


class AdditionalActivity2: AppCompatActivity() {
    lateinit var binding: ActivityAdditional2Binding
    private lateinit var adapter: PictuerAdapter
    private val PICK_IMAGE_REQUEST = 1 // 이미지를 선택하는 요청 코드
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdditional2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.relativeLayout.setOnClickListener {
            openGallery()
        }
        binding.imageButton5.setOnClickListener {
            val intent = Intent(this, AdditonalActivity3::class.java)
            startActivity(intent)
        }
        setupRecyclerView()

        val imageView10: ImageView = findViewById(R.id.imageView10)
        imageView10.setOnClickListener {
            finish()
        }
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