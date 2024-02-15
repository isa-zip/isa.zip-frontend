package com.example.zipfront

import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.zipfront.connection.RetrofitClient2
import com.example.zipfront.databinding.ActivityMainBinding
import com.example.zipfront.databinding.ActivityMatchingoptionselectBinding
import com.example.zipfront.databinding.ActivityMatchinguploadselectBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MatchingSecondUploadActivity : AppCompatActivity() {
    lateinit var binding: ActivityMatchinguploadselectBinding
    private lateinit var adapter: OuterSeconduploadAdapter
    private lateinit var thirdAdapter: ThirdprofileAdapter2
    private var clickedItemPosition: Int = -1
    private val user = MyApplication.getUser()
    private val token = user.getString("jwt", "").toString()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMatchinguploadselectBinding.inflate(layoutInflater)

        binding.layout1.visibility = View.VISIBLE
        binding.layout2.visibility = View.GONE

        binding.imageView16.setOnClickListener {
            binding.layout1.visibility = View.GONE
            binding.layout2.visibility = View.VISIBLE
        }

        binding.imageshow2.setOnClickListener {
            binding.layout1.visibility = View.VISIBLE
            binding.layout2.visibility = View.GONE
        }

        val title = intent.getStringExtra("title") ?: ""
        if (!title.isNullOrBlank()) {
            binding.textView9.text = title
        }

        val dongCount = intent.getIntExtra("dongCount", -1)
        Log.d("dongCount", dongCount.toString())

        if (dongCount == 0) {
            // dongCount가 0일 때
            binding.optionRv.visibility = View.GONE
            binding.notshwoingtext.visibility = View.VISIBLE
        } else {
            // dongCount가 0이 아닐 때
            fetchDongItem(title)
        }

        binding.imageView10.setOnClickListener {
            finish()
        }

        setContentView(binding.root)
    }

    private fun fetchDongItem(title: String) {
        val call = RetrofitObject.getRetrofitService.dongitem("Bearer $token", title)
        call.enqueue(object : Callback<RetrofitClient2.ResponseDongitem> {
            override fun onResponse(
                call: Call<RetrofitClient2.ResponseDongitem>,
                response: Response<RetrofitClient2.ResponseDongitem>
            ) {
                Log.d("Retrofit61", response.toString())
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    Log.d("Retrofit6", responseBody.toString())
                    if (responseBody != null) {
                        if (responseBody.isSuccess) {
                            val userItemResponses = responseBody.data.userItemResponses
                            // adapter 초기화
                            adapter = OuterSeconduploadAdapter(userItemResponses)
                            setupRecyclerView(userItemResponses)
                            binding.optionRv.visibility = View.VISIBLE
                            binding.notshwoingtext.visibility = View.GONE
                        } else {
                            Toast.makeText(
                                this@MatchingSecondUploadActivity,
                                responseBody.message ?: "Unknown error",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
            override fun onFailure(call: Call<RetrofitClient2.ResponseDongitem>, t: Throwable) {
                val errorMessage = "Call Failed: ${t.message}"
                Log.d("Retrofit", errorMessage)
            }
        })
    }

    private fun setupRecyclerView(innerItems: List<RetrofitClient2.UserItemResponse>) {
        // RecyclerView의 레이아웃 매니저 설정
        binding.optionRv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.optionRv2.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        thirdAdapter = ThirdprofileAdapter2(mutableListOf())
        thirdAdapter.onButtonClickListener = object : ThirdprofileAdapter2.OnButtonClickListener {
            override fun onButtonClick(position: Int) {
                showCustomDialog()
                clickedItemPosition=position
            }
        }

        thirdAdapter.setOnItemCountChangeListener(object : ThirdprofileAdapter2.OnItemCountChangeListener {
            override fun onItemCountChanged(itemCount: Int) {
                Log.d("MyApp22", "$itemCount")
                binding.textView23.text = "${itemCount}개"
                binding.texttitle2.text = "${itemCount}개"
            }
        })

//        adapter = OuterSeconduploadAdapter(mutableListOf())

        // RecyclerView의 어댑터 설정
        binding.optionRv.adapter = adapter

        // 서버에서 데이터를 가져와서 RecyclerView에 설정
//        fetchItemsFromServer(thirdAdapter)

        val itemCount = thirdAdapter.getItemCount()
        Log.d("ThirdprofileAdapter22", "$itemCount")

    }

    private fun fetchItemsFromServer(thirdAdapter: ThirdprofileAdapter2) {
        val call = RetrofitObject.getRetrofitService.matchbrokeritem("Bearer $token")
        call.enqueue(object : Callback<RetrofitClient2.ResponseMatchbrokeritem> {
            override fun onResponse(
                call: Call<RetrofitClient2.ResponseMatchbrokeritem>,
                response: Response<RetrofitClient2.ResponseMatchbrokeritem>
            ) {
                Log.d("Retrofit612", response.toString())
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    Log.d("Retrofit62", responseBody.toString())
                    if (responseBody != null) {
                        if (responseBody.isSuccess) {
                            val innerItems = responseBody.data.matchListDetails
                            thirdAdapter.setItems(innerItems)
                        } else {
                            Toast.makeText(
                                this@MatchingSecondUploadActivity,
                                responseBody.message ?: "Unknown error",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
            override fun onFailure(call: Call<RetrofitClient2.ResponseMatchbrokeritem>, t: Throwable) {
                val errorMessage = "Call Failed: ${t.message}"
                Log.d("Retrofit", errorMessage)
            }
        })
    }
    private fun showCustomDialog() {
        // 다이얼로그 레이아웃을 inflate
        val dialogView = layoutInflater.inflate(R.layout.option_seconddialogview, null)

        // AlertDialog.Builder를 사용하여 다이얼로그 생성
        val builder = AlertDialog.Builder(this)
        builder.setView(dialogView)
        // AlertDialog 생성
        val alertDialog: AlertDialog = builder.create()

        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        // Window 속성을 사용하여 크기 조절
        val layoutParams = WindowManager.LayoutParams()
        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT // 원하는 폭으로 설정
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT // 원하는 높이로 설정
        alertDialog.window?.attributes = layoutParams

        // 다이얼로그 내부의 ImageButton 참조
        val cancelButton = dialogView.findViewById<ImageButton>(R.id.imageButton8)
        val confirmButton = dialogView.findViewById<ImageButton>(R.id.imageButton9)

        // 취소 버튼 클릭 리스너 설정
        cancelButton.setOnClickListener {
            // 취소 버튼을 눌렀을 때 수행할 동작
            alertDialog.dismiss() // 다이얼로그 닫기
            // 추가적인 작업 수행 가능
        }

        // 확인 버튼 클릭 리스너 설정
        confirmButton.setOnClickListener {
//            finish()
            if (clickedItemPosition != -1) {
                thirdAdapter.setSelectedItemPosition(clickedItemPosition)
                Log.d("MyApp", "$clickedItemPosition")
            }
            alertDialog.dismiss() // 다이얼로그 닫기
            // 추가적인 작업 수행 가능
        }

        // AlertDialog 표시
        alertDialog.show()
    }
}