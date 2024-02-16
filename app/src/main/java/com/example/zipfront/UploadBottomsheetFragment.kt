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
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.zipfront.connection.RetrofitClient2
import com.example.zipfront.databinding.MatchingOptionBinding
import com.example.zipfront.databinding.UploadbottomsheetdialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UploadBottomsheetFragment() : BottomSheetDialogFragment() {
    private lateinit var recyclerView: RecyclerView
    private var adapter: BottomSheetAdapter? = null
    private lateinit var btnClose: ImageButton
    lateinit var binding: UploadbottomsheetdialogBinding
    private lateinit var onItemSelected: (List<RetrofitClient2.BrokerItem>, Int) -> Unit
    private var userItemId: Int = 0 // 이 부분을 추가하여 userItemId를 프래그먼트 내부에서 사용 가능하도록 함
    private val user = MyApplication.getUser()
    private val token = user.getString("jwt", "").toString()
    fun updateFragment(selectedItems: List<RetrofitClient2.BrokerItem>) {
        Log.d("Retrofit83", selectedItems.toString())
        val request = RetrofitClient2.RequestMatchbroker(selectedItems.map { it.brokerItemId })
        val call = RetrofitObject.getRetrofitService.matchBrokerItem("Bearer $token", userItemId, request)
        call.enqueue(object : Callback<RetrofitClient2.ResponseMatchbroker> {
            override fun onResponse(
                call: Call<RetrofitClient2.ResponseMatchbroker>,
                response: Response<RetrofitClient2.ResponseMatchbroker>
            ) {
                Log.d("Retrofit81", response.toString())
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    Log.d("Retrofit8", responseBody.toString())
                    if (responseBody != null && responseBody.isSuccess) {
                        //요청 성공시 화면 띄우기
                    } else {
                        // 요청이 실패했을 때의 처리
                    }
                }
            }
            override fun onFailure(call: Call<RetrofitClient2.ResponseMatchbroker>, t: Throwable) {
                val errorMessage = "Call Failed: ${t.message}"
                Log.d("Retrofit82", errorMessage)
            }
        })
    }

    fun setData(data: List<RetrofitClient2.BrokerItem>, userItemId: Int) {
        adapter?.setData(data)
        this.userItemId = userItemId // userItemId 설정
        Log.d("Retrofit77", "$data")
    }

    fun getSelectedItems(): List<RetrofitClient2.BrokerItem> {
        return adapter?.getSelectedItems() ?: emptyList()
    }

    private fun getBinding(view: View): UploadbottomsheetdialogBinding {
        return UploadbottomsheetdialogBinding.bind(view)
    }

    // Adapter에서 호출할 메소드
    fun updateBtnCloseBackground() {
        Log.d("UpdateBtnCloseBackground", "Method called")
        val selectedItems = getSelectedItems()
        Log.d("SelectedItems", "Selected Items: $selectedItems, Type: ${selectedItems.javaClass}")

        activity?.runOnUiThread {
            if (selectedItems.isNotEmpty()) {
                Log.d("UpdateBtnCloseBackground1", "blue")
                binding.imageButton5.setImageResource(R.drawable.btn_agent_blue)
            } else {
                Log.d("UpdateBtnCloseBackground2", "gray")
                binding.imageButton5.setImageResource(R.drawable.btn_agent_gray)
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.uploadbottomsheetdialog, container, false)
        binding = getBinding(view)
        val btnOK: ImageButton = view.findViewById(R.id.imageButton4)
        btnClose = view.findViewById(R.id.imageButton5)

        recyclerView = view.findViewById(R.id.option_rv)
        adapter = BottomSheetAdapter(this)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        updateBtnCloseBackground()

        btnOK.setOnClickListener {
            val intent = Intent(requireContext(), AdditionalActivity0::class.java)
            startActivity(intent)
            dismiss()
        }

        btnClose.setOnClickListener {
            val selectedItems = getSelectedItems()
            updateFragment(selectedItems)
            val activity = requireContext() as? MatchingSecondUploadActivity
            activity?.setupRecyclerView2()
            dismiss()
        }

        return view
    }

}