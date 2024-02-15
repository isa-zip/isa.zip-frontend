package com.example.zipfront

import android.app.Activity
import android.app.TaskStackBuilder
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.zipfront.connection.RetrofitClient2
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ManagementBottomsheetDialogFragment(brokerId : Int) : BottomSheetDialogFragment() {

    private val user = MyApplication.getUser()
    private val token = user.getString("jwt", "").toString()
    private val brokerItemId = brokerId

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.managementbottomsheetdialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val button1: Button = view.findViewById(R.id.modify_btn)
        button1.setOnClickListener() {
            //수정하기
            val intent = Intent(requireContext(), ModifyActivity2::class.java)
            intent.putExtra("brokerItemId", brokerItemId)
            startActivity(intent)
            //수정하기 넘어가면 AdditionalActivity2 뒤로가기 버튼 안먹음
        }

        val button2: Button = view.findViewById(R.id.sold_out_btn)
        button2.setOnClickListener() {
            showSoldOutDialog()
        }

        val button3: Button = view.findViewById(R.id.delete_btn)
        button3.setOnClickListener() {
            showDeleteDialog(brokerItemId)
        }

    }

    private fun showSoldOutDialog() {
        // 다이얼로그 레이아웃을 inflate
        val dialogView = layoutInflater.inflate(R.layout.soldout_dialog, null)

        // AlertDialog.Builder를 사용하여 다이얼로그 생성
        val builder = AlertDialog.Builder(requireContext())
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
        val cancelButton = dialogView.findViewById<ImageButton>(R.id.cancel_btn)
        val deleteButton = dialogView.findViewById<ImageButton>(R.id.finish_btn)

        // 취소 버튼 클릭 리스너 설정
        cancelButton.setOnClickListener {
            alertDialog.dismiss() // 다이얼로그 닫기
        }

        // 확인 버튼 클릭 리스너 설정
        deleteButton.setOnClickListener {
            alertDialog.dismiss() // 다이얼로그 닫기
        }

        alertDialog.show()
    }


    private fun showDeleteDialog(brokerItemId : Int) {
        // 다이얼로그 레이아웃을 inflate
        val dialogView = layoutInflater.inflate(R.layout.delete_dialog, null)

        // AlertDialog.Builder를 사용하여 다이얼로그 생성
        val builder = AlertDialog.Builder(requireContext())
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
        val cancelButton = dialogView.findViewById<ImageButton>(R.id.cancel_btn)
        val deleteButton = dialogView.findViewById<ImageButton>(R.id.delete_btn)

        // 취소 버튼 클릭 리스너 설정
        cancelButton.setOnClickListener {
            alertDialog.dismiss() // 다이얼로그 닫기
        }

        // 확인 버튼 클릭 리스너 설정
        deleteButton.setOnClickListener {
            Log.d("delete", "버튼 클릭")

            //매물 삭제 API 연동
            val call = RetrofitObject.getRetrofitService.deleteProperty("Bearer $token", brokerItemId)

            call.enqueue(object : Callback<RetrofitClient2.ResponseDelete> {
                override fun onResponse(call: Call<RetrofitClient2.ResponseDelete>, response: Response<RetrofitClient2.ResponseDelete>) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        Log.d("Retrofit2", response.toString())
                        if(responseBody != null){
                            Log.d("delete", "${responseBody.isSuccess}")
                            if(responseBody.isSuccess) {
                                Toast.makeText(requireContext(), "삭제 완료", Toast.LENGTH_SHORT).show()

                            }else{
                                Toast.makeText(requireContext(), responseBody?.message ?: "Unknown error", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<RetrofitClient2.ResponseDelete>, t: Throwable) {
                    val errorMessage = "Call Failed: ${t.message}"
                    Log.d("Retrofit", errorMessage)
                    Toast.makeText(requireContext(), "삭제 실패", Toast.LENGTH_SHORT).show()
                }
            })

            val intent = Intent(requireContext(), MenuManagementActivity::class.java)
            startActivity(intent)
            requireActivity().finish() // 현재 Activity 종료
            //alertDialog.dismiss() // 다이얼로그 닫기
        }

        alertDialog.show()
    }

}