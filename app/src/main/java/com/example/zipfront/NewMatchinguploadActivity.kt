package com.example.zipfront

import android.app.TaskStackBuilder
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.zipfront.connection.RetrofitClient2
import com.example.zipfront.databinding.ActivityMainBinding
import com.example.zipfront.databinding.ActivityUpdatingpropertyBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewMatchinguploadActivity : AppCompatActivity() {
    lateinit var binding: ActivityUpdatingpropertyBinding
    private lateinit var adapter: OuteruploadAdapter
    private val user = MyApplication.getUser()
    private val token = user.getString("jwt", "").toString()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdatingpropertyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val call = RetrofitObject.getRetrofitService.dongcount("Bearer $token")
        call.enqueue(object : Callback<RetrofitClient2.ResponseDongcount> {
            override fun onResponse(
                call: Call<RetrofitClient2.ResponseDongcount>,
                response: Response<RetrofitClient2.ResponseDongcount>
            ) {
                Log.d("Retrofit61", response.toString())
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    Log.d("Retrofit6", responseBody.toString())
                    if (responseBody != null) {
                        if (responseBody.isSuccess) {
                            if (responseBody.data.isEmpty()) {
                                // 만약 데이터가 비어있다면
                                binding.stiillupload2.visibility = View.VISIBLE
                                binding.stiillupload.visibility = View.GONE
                            } else {
                                val serverDataList = responseBody.data.map { data ->
                                    OuterUploadItem(data.dong, data.dongCount)
                                }
                                // RecyclerView 갱신
                                setupRecyclerView(serverDataList)
                                binding.stiillupload2.visibility = View.GONE
                                binding.stiillupload.visibility = View.VISIBLE
                            }
                        } else {
                            Toast.makeText(
                                this@NewMatchinguploadActivity,
                                responseBody.message ?: "Unknown error",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
            override fun onFailure(call: Call<RetrofitClient2.ResponseDongcount>, t: Throwable) {
                val errorMessage = "Call Failed: ${t.message}"
                Log.d("Retrofit", errorMessage)
            }
        })

        binding.imageButton12.setOnClickListener{
            val intent = Intent(this, AdditionalActivity0::class.java)
            startActivity(intent)
        }

        binding.imageView10.setOnClickListener{
            finish()
        }

//        outeruploadItemList = listOf(
//            OuterUploadItem(
//                "상도동",
//                listOf("정태연", "한승연", "김가연")
//            ),
//            OuterUploadItem(
//                "동선동",
//                listOf("강바다", "강다희", "유미라", "한수찬")
//            ),
//            OuterUploadItem("오목교"),
//            OuterUploadItem(
//                "성신여대",
//                listOf("차현수", "장은진", "메로나", "배고파", "집갈래")
//            )
//        )
//        setupRecyclerView(outeruploadItemList)
    }

    private fun setupRecyclerView(outerItemList: List<OuterUploadItem>) {
        // RecyclerView의 레이아웃 매니저 설정
        binding.optionRv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        // RecyclerView의 어댑터 설정
        adapter = OuteruploadAdapter(outerItemList)
        binding.optionRv.adapter = adapter

        binding.textView20.text = "${outerItemList.size}건"
    }
    data class OuterUploadItem(val title: String, val dongCount: Int)
}