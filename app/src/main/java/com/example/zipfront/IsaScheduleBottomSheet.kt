package com.example.zipfront

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.viewpager2.widget.ViewPager2
import com.example.zipfront.connection.RetrofitClient2
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class IsaScheduleBottomSheet(private val eventId: Int) : BottomSheetDialogFragment() {
    private var user = MyApplication.getUser()
    private var token = user.getString("jwt", "").toString()

    // private val eventId = eventId
    private var isRegistration: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.bottomsheet_dialog_component, container, false)
        val btnModify: ImageButton = view.findViewById(R.id.imageButton4)
        val btnDelete: ImageButton = view.findViewById(R.id.imageButton5)

        // 수정하기 버튼
        btnModify.setOnClickListener {
            dismiss() // 현재 다이얼로그를 닫음

            val intent = Intent(activity, ScheduleModifyActivity::class.java)
            startActivity(intent)
        }

        // 삭제하기 버튼
        btnDelete.setOnClickListener {
            dismiss()

            // 삭제하기 api
            val call = RetrofitObject.getRetrofitService.evenscheduledelete("Bearer $token", eventId)
            call.enqueue(object : Callback<RetrofitClient2.ResponseEventscheduledelete> {
                override fun onResponse(
                    call: Call<RetrofitClient2.ResponseEventscheduledelete>,
                    response: Response<RetrofitClient2.ResponseEventscheduledelete>
                ) {
                    Log.d("Retrofit18", response.toString())
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        Log.d("Retrofit118", responseBody.toString())
                        if (responseBody != null && responseBody.isSuccess) {
                            // 삭제
                            isRegistration = true
                        } else {
                            Toast.makeText(
                                requireContext(),
                                responseBody?.message ?: "Unknown error",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }

                override fun onFailure(call: Call<RetrofitClient2.ResponseEventscheduledelete>, t: Throwable) {
                    val errorMessage = "Call Failed: ${t.message}"
                    Log.d("Retrofit", errorMessage)
                    Toast.makeText(
                        requireContext(),
                        errorMessage,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
        }
        return view
    }

}