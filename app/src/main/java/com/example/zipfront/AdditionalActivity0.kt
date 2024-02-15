package com.example.zipfront

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.zipfront.connection.RetrofitClient2
import com.example.zipfront.databinding.ActivityAdditional0Binding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdditionalActivity0 : AppCompatActivity() {
    private lateinit var binding: ActivityAdditional0Binding
    private lateinit var adapter: AdditionalAdapter
    private val retrofitService = RetrofitObject.getRetrofitService

    private lateinit var user: SharedPreferences
    private lateinit var token: String

    private var isResponseHandled = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdditional0Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val imageView10: ImageView = findViewById(R.id.imageView10)
        imageView10.setOnClickListener {
            finish()
        }

        // user 객체 초기화
        user = getSharedPreferences("user", MODE_PRIVATE)
        token = user.getString("jwt", "").toString() // token 초기화

        // EditText에서 엔터 키 이벤트 처리
        binding.searchImage.setOnEditorActionListener { _, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_DONE || keyEvent.keyCode == KeyEvent.KEYCODE_ENTER) {
                getAddressInfo()
                // 이벤트를 계속 전달합니다.
                false
            } else {
                // 다른 이벤트는 처리하지 않고 계속 전달합니다.
                true
            }
        }
    }

    private fun getAddressInfo() {
        val address = binding.searchImage.text.toString().trim()
        if (address.isEmpty()) {
            Toast.makeText(this, "주소를 입력해주세요", Toast.LENGTH_SHORT).show()
            return
        }

        val call = retrofitService.BeforeAddress("Bearer $token", address)
        call.enqueue(object : Callback<RetrofitClient2.ResponseBeforeAddress> {
            override fun onResponse(
                call: Call<RetrofitClient2.ResponseBeforeAddress>,
                response: Response<RetrofitClient2.ResponseBeforeAddress>
            ) {
                if (!isResponseHandled) {
                    isResponseHandled = true
                    Log.d("Retrofit33", response.toString())
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null && responseBody.isSuccess) {
                            val addressData = responseBody.data
                            val addressList = listOf(
                                addressData.address,
                                addressData.road_address_name,
                                addressData.postNumber
                            )
                            binding.first.visibility = View.GONE
                            binding.optionRv.visibility = View.VISIBLE
                            setupRecyclerView(addressList)
                        } else {
                            Toast.makeText(
                                this@AdditionalActivity0,
                                responseBody?.message ?: "Unknown error",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }

            override fun onFailure(call: Call<RetrofitClient2.ResponseBeforeAddress>, t: Throwable) {
                val errorMessage = "Call Failed: ${t.message}"
                Log.d("Retrofit", errorMessage)
            }
        })
    }

    private fun setupRecyclerView(informationList: List<String>) {
        // RecyclerView의 레이아웃 매니저 설정
        binding.optionRv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        // RecyclerView의 어댑터 설정
        adapter = AdditionalAdapter(this, informationList)
        binding.optionRv.adapter = adapter
    }
}
