package com.example.zipfront

import android.app.TaskStackBuilder
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.zipfront.connection.RetrofitClient2
import com.example.zipfront.databinding.FitstMenuCertifyBinding
import com.example.zipfront.databinding.FitstMenuSecessionBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MenuSecessionActivity: AppCompatActivity() {
    lateinit var binding: FitstMenuSecessionBinding
    private var isChecked: Boolean = false

    private val user = MyApplication.getUser()
    private val token = user.getString("jwt", "").toString()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FitstMenuSecessionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imageView10.setOnClickListener {
            finish()
        }
        // 이미지 뷰 클릭 이벤트 리스너 설정
        binding.imageView25.setOnClickListener {
            // 토글 상태 변경
            isChecked = !isChecked

            // 이미지 변경
            if (isChecked) {
                binding.imageView25.setImageResource(R.drawable.check_blue)
                binding.button3.setBackgroundResource(R.drawable.profileroudradius_blue)
            } else {
                binding.imageView25.setImageResource(R.drawable.groupcheck_gray)
                binding.button3.setBackgroundResource(R.drawable.profileroudradius_gray)
            }
        }
        binding.button3.setOnClickListener {
            val call = RetrofitObject.getRetrofitService.withdraw("Bearer $token")
            call.enqueue(object : Callback<RetrofitClient2.ResponseWithdraw> {
                override fun onResponse(
                    call: Call<RetrofitClient2.ResponseWithdraw>,
                    response: Response<RetrofitClient2.ResponseWithdraw>
                ) {
                    Log.d("Retrofit31", response.toString())
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        Log.d("Retrofit3", responseBody.toString())
                        if (responseBody != null && responseBody.isSuccess) {
                            val intent =
                                Intent(this@MenuSecessionActivity, LoginActivity::class.java)
                            val stackBuilder = TaskStackBuilder.create(this@MenuSecessionActivity)
                            stackBuilder.addNextIntentWithParentStack(intent)
                            stackBuilder.startActivities()
                        } else {
                            Toast.makeText(
                                this@MenuSecessionActivity,
                                responseBody?.message ?: "Unknown error",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }

                override fun onFailure(call: Call<RetrofitClient2.ResponseWithdraw>, t: Throwable) {
                    val errorMessage = "Call Failed: ${t.message}"
                    Log.d("Retrofit", errorMessage)
                }
            })
        }
    }
}