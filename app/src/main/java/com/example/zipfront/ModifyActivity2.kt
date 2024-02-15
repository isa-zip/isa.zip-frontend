package com.example.zipfront

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.zipfront.connection.RetrofitClient2
import com.example.zipfront.databinding.ActivityAdditional2Binding
import com.example.zipfront.databinding.ActivityModify2Binding
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ModifyActivity2: AppCompatActivity() {
    lateinit var binding: ActivityModify2Binding
    private lateinit var adapter: PictuerAdapter
    private val PICK_IMAGE_REQUEST = 1 // 이미지를 선택하는 요청 코드
    private val user = MyApplication.getUser()
    private val token = user.getString("jwt", "").toString()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityModify2Binding.inflate(layoutInflater)


        //brokerItemID 받아오기
        val intent = Intent()
        val brokerId = intent.getIntExtra("brokerItemId", 1)


        //데이터 불러오기
        val call = RetrofitObject.getRetrofitService.showDetailItem("Bearer $token", brokerId)


        call.enqueue(object : Callback<RetrofitClient2.ResponseDetail> {
            override fun onResponse(call: Call<RetrofitClient2.ResponseDetail>, response: Response<RetrofitClient2.ResponseDetail>) {
                // 통신 성공
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    Log.d("detail", responseBody.toString())
                    if (responseBody != null && responseBody.isSuccess) {
                        val data = responseBody.data

                        //주소 설정
                        binding.text.text = data.addressResponse.addressName

                        //이미지 추가
                        val imageUrl = data.detailResponse.itemImages.firstOrNull()?.imageUrl
                        if (!imageUrl.isNullOrEmpty()) {
                            val uri = Uri.parse(imageUrl)
                            adapter.addImage(uri)
                        }

                        //간단 소개
                        binding.text1.setText(data.detailResponse.itemContent.shortIntroduction)

                        //상세 설명
                        binding.text2.setText(data.detailResponse.itemContent.specificIntroduction)


                    } else {
                        Toast.makeText(this@ModifyActivity2, responseBody?.message ?: "Unknown error", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            override fun onFailure(call: Call<RetrofitClient2.ResponseDetail>, t: Throwable) {
                Log.d("message", "")
            }
        })


        setContentView(binding.root)

        binding.relativeLayout.setOnClickListener {
            openGallery()
        }
        binding.imageButton5.setOnClickListener {






            val intent = Intent(this, ModifyActivity3::class.java)
            startActivity(intent)
        }

        //주소 클릭시
        /*binding.text.setOnClickListener {
            //나중에 주소 API 연결된 후 구현
        }*/

        setupRecyclerView()

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

    private fun loadImage() {

    }
}