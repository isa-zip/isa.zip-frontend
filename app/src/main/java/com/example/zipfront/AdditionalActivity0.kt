package com.example.zipfront

import android.content.Context
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

        // EditText에서 엔터 키 이벤트 처리
        binding.searchImage2.setOnEditorActionListener { _, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_DONE || keyEvent.keyCode == KeyEvent.KEYCODE_ENTER) {
                getAddressInfo()
                // 이벤트를 소비했으므로 true 반환
                true
            } else {
                // 다른 이벤트는 처리하지 않고 계속 전달
                false
            }
        }
    }

    private fun getAddressInfo() {
        // user 객체 초기화
        user = getSharedPreferences("user", Context.MODE_PRIVATE)
        token = user.getString("jwt", "").toString() // token 초기화

        val address = binding.searchImage2.text.toString().trim()
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
                Log.d("Retrofit73", response.toString())
                if (!isResponseHandled) {
                    isResponseHandled = true
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        Log.d("Retrofit7", responseBody.toString())
                        if (responseBody != null && responseBody.isSuccess) {
                            val addressList = responseBody.data.map { beforeAddress ->
                                "${beforeAddress.road_address_name}, ${beforeAddress.address}, ${beforeAddress.postNumber}"
                            }
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

        // EditText를 엔터를 눌렀을 때 초기화하지 않도록 수정
        if (binding.searchImage2.imeOptions != EditorInfo.IME_ACTION_DONE) {
            // EditText에 입력된 주소를 초기화하지 않습니다.
            // binding.searchImage2.setText("")
        }
    }

    override fun onResume() {
        super.onResume()
        // EditText 초기화
        binding.searchImage2.setText("")
    }

    private fun setupRecyclerView(informationList: List<String>) {
        // RecyclerView의 레이아웃 매니저 설정
        binding.optionRv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        // RecyclerView의 어댑터 설정
        adapter = AdditionalAdapter(this, informationList)
        binding.optionRv.adapter = adapter
    }
}
