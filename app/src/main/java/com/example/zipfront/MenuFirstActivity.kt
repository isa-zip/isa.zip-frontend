package com.example.zipfront

import android.app.TaskStackBuilder
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.zipfront.connection.RetrofitClient2
import com.example.zipfront.databinding.FitstMenuLayoutBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MenuFirstActivity : AppCompatActivity() {
    lateinit var binding: FitstMenuLayoutBinding

    private lateinit var user: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var token: String
    private lateinit var email: String
    private lateinit var nickName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FitstMenuLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 사용자 SharedPreferences 초기화
        user = MyApplication.getUser()
        token = user.getString("jwt", "").toString()
        email = user.getString("email", "").toString()
        nickName = user.getString("nickName", "").toString()

        binding.profiletext.text = nickName

        binding.subemail.text = email
        binding.arrowbutton2.setOnClickListener {
            val intent = Intent(this, MenuProfileActivity::class.java)
            startActivity(intent)
        }

        binding.arrowbutton3.setOnClickListener {
            val intent = Intent(this, MenuCertifyActivity::class.java)
            startActivity(intent)
        }

        binding.arrowbutton5.setOnClickListener {
            val intent = Intent(this, MenuSecessionActivity::class.java)
            startActivity(intent)
        }
        //이용약관
        binding.arrowbutton1.setOnClickListener {
            val intent = Intent(this, MenuSerciveActivity::class.java)
            startActivity(intent)
        }

        //매물관리
        binding.arrowbutton4.setOnClickListener {
            val intent = Intent(this, MenuManagementActivity::class.java)
            startActivity(intent)
        }

        binding.imageView10.setOnClickListener {
            finish()
        }
        binding.button2.setOnClickListener {
            val call = RetrofitObject.getRetrofitService.logout("Bearer $token")
            call.enqueue(object : Callback<RetrofitClient2.Responselogout> {
                override fun onResponse(call: Call<RetrofitClient2.Responselogout>, response: Response<RetrofitClient2.Responselogout>) {
                    if (response.isSuccessful) {
                        Log.d("Retrofit4", response.toString())
                        val responseBody = response.body()
                        if (responseBody != null && responseBody.isSuccess)
                        {
                            val intent = Intent(this@MenuFirstActivity, LoginActivity::class.java)
                            val stackBuilder = TaskStackBuilder.create(this@MenuFirstActivity)
                            stackBuilder.addNextIntentWithParentStack(intent)
                            stackBuilder.startActivities()
                        }
                        else
                        {
                        }
                    } else {
                        Toast.makeText(
                            this@MenuFirstActivity,
                            response.body()?.message ?: "Unknown error",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<RetrofitClient2.Responselogout>, t: Throwable) {
                    val errorMessage = "Call Failed: ${t.message}"
                    Log.d("Retrofit", errorMessage)
                }
            })
        }

        // 프로필 이미지 가져오기
        loadProfileImage()
    }
    private fun loadProfileImage() {

        val call = RetrofitObject.getRetrofitService.profile("Bearer $token")
        call.enqueue(object : Callback<RetrofitClient2.ResponseProfilesee> {
            override fun onResponse(call: Call<RetrofitClient2.ResponseProfilesee>, response: Response<RetrofitClient2.ResponseProfilesee>) {
                if (response.isSuccessful) {
                    Log.d("Retrofit4", response.toString())
                    val profileData = response.body()?.data
                    profileData?.let {
                        // 이미지를 가져와서 이미지뷰에 설정
                        val imageUrl = it.userImg
                        if (imageUrl != null) {
                            // 이미지 URL이 null이 아닌 경우 Glide를 사용하여 이미지 로드
                            Glide.with(this@MenuFirstActivity)
                                .load(imageUrl)
                                .into(binding.imageView18)
                        } else {
                            // 이미지 URL이 null인 경우 기본 이미지 설정
                            binding.imageView18.setImageResource(R.drawable.btn_image_bed)
                        }
                        // 닉네임을 가져와서 SharedPreferences에 저장
                        val nickName = it.nickName
                        if (nickName != null) {
                            binding.profiletext.text = nickName
                        }
                    }
                } else {
                    Toast.makeText(
                        this@MenuFirstActivity,
                        response.body()?.message ?: "Unknown error",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<RetrofitClient2.ResponseProfilesee>, t: Throwable) {
                val errorMessage = "Call Failed: ${t.message}"
                Log.d("Retrofit", errorMessage)
            }
        })
    }
    override fun onResume() {
        super.onResume()
        // 프로필 이미지 다시 불러오기
        loadProfileImage()
    }
}