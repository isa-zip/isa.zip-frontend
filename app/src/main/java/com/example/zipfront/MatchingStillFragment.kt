package com.example.zipfront

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.zipfront.connection.RetrofitClient2
import com.example.zipfront.databinding.MatchingstillLayoutBinding
import com.example.zipfront.databinding.MatchingstillRecyclerviewBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MatchingStillFragment : Fragment() {
    lateinit var binding: MatchingstillRecyclerviewBinding
    private lateinit var adapter: OuteroptionAdapter
    val REQUEST_CODE_OPTION = 1
    private val user = MyApplication.getUser()
    private val token = user.getString("jwt", "").toString()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= MatchingstillRecyclerviewBinding.inflate(inflater,container,false)

        val requestCode = arguments?.getInt("REQUEST_CODE", -1) ?: -1
        Log.d("MatchingStillFragment", "Received requestCode: $requestCode")

        binding.textView10.visibility = View.VISIBLE
        binding.notShowing2.visibility = View.GONE

//        outerItemList = listOf(
//            OuterItem("상도동", listOf("내부 아이템 1-1", "내부 아이템 1-2", "내부 아이템 1-3")),
//            OuterItem("동선동", listOf("내부 아이템 2-1", "내부 아이템 2-2", "내부 아이템 2-3", "내부 아이템 2-4")),
//            OuterItem("목동"),
//            OuterItem("성신여대", listOf("내부 아이템 4-1", "내부 아이템 4-2", "내부 아이템 4-3", "내부 아이템 4-4","내부 아이템 4-5"))
//        )

        val call = RetrofitObject.getRetrofitService.usermatchitem("Bearer $token", "WAITING")
        call.enqueue(object : Callback<RetrofitClient2.ResponseUserMatchitem> {
            override fun onResponse(
                call: Call<RetrofitClient2.ResponseUserMatchitem>,
                response: Response<RetrofitClient2.ResponseUserMatchitem>
            ) {
                Log.d("Retrofit61", response.toString())
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    Log.d("Retrofit6", responseBody.toString())
                    if (responseBody != null) {
                        if (responseBody.isSuccess) {
                            if (responseBody.data.isNotEmpty()) {
                                binding.textView10.visibility = View.GONE
                                binding.notShowing2.visibility = View.VISIBLE
                                setupRecyclerView(responseBody.data)
                                binding.textView20.text = "${responseBody.data.size}건"
                            } else {
                                // 데이터가 없을 때 처리
                                binding.textView10.visibility = View.VISIBLE
                                binding.notShowing2.visibility = View.GONE
                            }
                        } else {
                            Toast.makeText(
                                requireContext(),
                                responseBody.message ?: "Unknown error",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
            override fun onFailure(call: Call<RetrofitClient2.ResponseUserMatchitem>, t: Throwable) {
                val errorMessage = "Call Failed: ${t.message}"
                Log.d("Retrofit", errorMessage)
            }
        })

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        // 여기에 서버에서 데이터를 다시 불러오고 UI를 업데이트하는 코드를 작성합니다.
        val call = RetrofitObject.getRetrofitService.usermatchitem("Bearer $token", "WAITING")
        call.enqueue(object : Callback<RetrofitClient2.ResponseUserMatchitem> {
            override fun onResponse(
                call: Call<RetrofitClient2.ResponseUserMatchitem>,
                response: Response<RetrofitClient2.ResponseUserMatchitem>
            ) {
                Log.d("Retrofit61", response.toString())
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    Log.d("Retrofit6", responseBody.toString())
                    if (responseBody != null) {
                        if (responseBody.isSuccess) {
                            if (responseBody.data.isNotEmpty()) {
                                binding.textView10.visibility = View.GONE
                                binding.notShowing2.visibility = View.VISIBLE
                                setupRecyclerView(responseBody.data)
                                binding.textView20.text = "${responseBody.data.size}건"
                            } else {
                                // 데이터가 없을 때 처리
                                binding.textView10.visibility = View.VISIBLE
                                binding.notShowing2.visibility = View.GONE
                            }
                        } else {
                            Toast.makeText(
                                requireContext(),
                                responseBody.message ?: "Unknown error",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }

            override fun onFailure(call: Call<RetrofitClient2.ResponseUserMatchitem>, t: Throwable) {
                val errorMessage = "Call Failed: ${t.message}"
                Log.d("Retrofit", errorMessage)
            }
        })
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("MatchingStill3", "onViewCreated")
        // 뷰가 생성된 후에 레이아웃 상태를 설정
        // 여기에서 onActivityResult를 직접 호출하지 않습니다.
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        Log.d("MatchingStillFragment", "requestCode: $requestCode, resultCode: $resultCode")

        activity?.runOnUiThread {
            if (requestCode == REQUEST_CODE_OPTION && resultCode == Activity.RESULT_OK) {
                Log.d("MatchingStill", "Inside runOnUiThread - Updating UI for RESULT_OK")
                binding.textView10.visibility = View.GONE
                binding.notShowing2.visibility = View.VISIBLE
            } else {
                Log.d("MatchingStill2", "Inside runOnUiThread - Updating UI for other result codes")
                binding.textView10.visibility = View.VISIBLE
                binding.notShowing2.visibility = View.GONE
            }
        }
    }

    fun handleActivityResult(resultCode: Int) {
        Log.d("MatchingStill6", "resultCode: $resultCode")
        activity?.runOnUiThread {
            if (resultCode == Activity.RESULT_OK) {
                binding.textView10.visibility = View.GONE
                binding.notShowing2.visibility = View.VISIBLE
            } else {
                binding.textView10.visibility = View.VISIBLE
                binding.notShowing2.visibility = View.GONE
            }
        }
    }

    private fun setupRecyclerView(outerItemList: List<RetrofitClient2.UserMatchitemData>) {
        // RecyclerView의 레이아웃 매니저 설정
        binding.optionRv.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        // RecyclerView의 어댑터 설정
        adapter = OuteroptionAdapter(outerItemList)
        binding.optionRv.adapter = adapter
    }

//    data class OuterItem(val title: String, val innerItemList: List<String>? = null) {
//        fun getItemCount(): Int {
//            return innerItemList?.size ?: 0
//        }
//    }

}