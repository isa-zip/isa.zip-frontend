package com.example.zipfront

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.zipfront.connection.RetrofitClient2
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class IsaScheduleHomeBottomSheet() : BottomSheetDialogFragment() {
    private var user = MyApplication.getUser()
    private var token = user.getString("jwt", "").toString()

    private var eventId: Int = 0
    private lateinit var optionRecyclerView: RecyclerView
    var scheduleHomeItems = ArrayList<IsaScheduleHomeItem>()
    private lateinit var adapter : IsaScheduleHomeAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.bottomsheet_dialog_component, container, false)
        val btnModify: ImageButton = view.findViewById(R.id.imageButton4)
        val btnDelete: ImageButton = view.findViewById(R.id.imageButton5)

        // 수정하기 버튼
        // 상세 일정 삭제 -> 상세 일정 등록 (파란색 버튼?) -> 상세 일정 조회
        btnModify.setOnClickListener {
            val intent = Intent(activity, ScheduleHomeModifyActivity::class.java)
            startActivity(intent)
        }

        // 삭제하기 버튼
        // 상세 일정 삭제 -> 상세 일정 조회
        btnDelete.setOnClickListener {
//            val call = RetrofitObject.getRetrofitService.evenscheduledelete("Bearer $token", eventId)
//            call.enqueue(object : Callback<RetrofitClient2.ResponseEventscheduledelete> {
//                override fun onResponse(
//                    call: Call<RetrofitClient2.ResponseEventscheduledelete>,
//                    response: Response<RetrofitClient2.ResponseEventscheduledelete>
//                ) {
//                    Log.d("Retrofit797", response.toString())
//                    if (response.isSuccessful) {
//                        val responseBody = response.body()
//                        if (responseBody != null && responseBody.isSuccess) {
//                            // 삭제 후 상세일정 조회
//                            evenschedulelookup()
//                        }
//                        // 바텀시트라서 오류 냠
//                        else {
//                                /*Toast.makeText(
//                                    this@IsaScheduleBottomSheet,
//                                    responseBody?.message ?: "Unknown error",
//                                    Toast.LENGTH_SHORT
//                                ).show()*/
//                            }
//                    }
//                }
//
//                override fun onFailure(call: Call<RetrofitClient2.ResponseEventscheduledelete>, t: Throwable) {
//                    val errorMessage = "Call Failed: ${t.message}"
//                    Log.d("Retrofit", errorMessage)
//                }
//            })
            dismiss()
        }
        return view
    }

    // 상세 일정 조회
    private fun evenschedulelookup() {
        var date = ""
        var description = ""

        val call = RetrofitObject.getRetrofitService.evenschedulelookup("Bearer $token")
        call.enqueue(object : Callback<RetrofitClient2.ResponseEventScheduleLookup> {
            override fun onResponse(
                call: Call<RetrofitClient2.ResponseEventScheduleLookup>,
                response: Response<RetrofitClient2.ResponseEventScheduleLookup>
            ) {
                Log.d("Retrofit777", response.toString())
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    Log.d("Retrofit77777", responseBody.toString())
                    if (responseBody != null && responseBody.isSuccess) {
                        //여기서 리사이클러뷰 설정
                        scheduleHomeItems.clear()
                        for (data in responseBody.data) {
                            date = data.eventDate
                            description = data.eventTitle
                            scheduleHomeItems.add(IsaScheduleHomeItem(date, description))
                        }
                        optionRecyclerView.adapter = null
                        optionRecyclerView.adapter = adapter
                    }
                }
            }

            override fun onFailure(call: Call<RetrofitClient2.ResponseEventScheduleLookup>, t: Throwable) {
                val errorMessage = "Call Failed: ${t.message}"
                Log.d("Retrofit", errorMessage)
            }
        })

    }
}