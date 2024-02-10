package com.example.zipfront

import android.annotation.SuppressLint
import android.app.Activity
import android.app.TaskStackBuilder
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.zipfront.connection.RetrofitClient2
import com.example.zipfront.databinding.ActivityMainBinding
import com.example.zipfront.databinding.FitstMenuCertifyBinding
import com.example.zipfront.databinding.FitstMenuLayoutBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
    @SuppressLint("MissingInflatedId")
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
            val textID = binding.textID.text.toString()
            val textID2 = binding.textID2.text.toString()
            val textPassword = binding.textPassword.text.toString()
            val textID3 = binding.textID3.text.toString()
            val brokerId: Int = textID.toIntOrNull() ?: 0 // 기본값으로 0을 사용하거나 적절한 기본값을 지정

            val requestCertify = RetrofitClient2.RequestCertify(
                brokerId = brokerId,
                name = textID2,
                phoneNum = textPassword,
                businessName = textID3
            )

            val user = MyApplication.getUser()
            val token = user.getString("jwt", "").toString()

            val call = RetrofitObject.getRetrofitService.menucertify("Bearer $token", requestCertify)
            call.enqueue(object : Callback<RetrofitClient2.ResponseCertify> {
                override fun onResponse(call: Call<RetrofitClient2.ResponseCertify>, response: Response<RetrofitClient2.ResponseCertify>) {
                    Log.d("Retrofit51", response.toString())
                    if (response.isSuccessful) {
                        Log.d("Retrofit5", response.toString())
                        val profileData = response.body()
                        if(profileData != null){
                            Log.d("Retrofit52", profileData.toString())
                            if(profileData.isSuccess) {
                                val intent = Intent(this@MenuCertifyActivity, MenuManagementActivity::class.java)
                                intent.putExtra("EXTRA_CUSTOM_DATA", "Hello from MatchingOptionActivity!")
                                setResult(Activity.RESULT_OK, intent)

                                val stackBuilder = TaskStackBuilder.create(this@MenuCertifyActivity)
                                stackBuilder.addNextIntentWithParentStack(intent)
                                stackBuilder.startActivities()
                                alertDialog.dismiss() // 다이얼로그 닫기
                            }else{
                                Toast.makeText(
                                    this@MenuCertifyActivity,
                                    response.body()?.message ?: "Unknown error",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    } else {
                        Toast.makeText(
                            this@MenuCertifyActivity,
                            response.body()?.message ?: "Unknown error",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                override fun onFailure(call: Call<RetrofitClient2.ResponseCertify>, t: Throwable) {
                    val errorMessage = "Call Failed: ${t.message}"
                    Log.d("Retrofit", errorMessage)
                }
            })
        }
        // AlertDialog 표시
        alertDialog.show()
    }
}