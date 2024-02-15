package com.example.zipfront

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.zipfront.connection.RetrofitClient2
import com.example.zipfront.databinding.MatchingcompleteRecyclerviewBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MatchingCompleteFragment: Fragment() {
    lateinit var binding: MatchingcompleteRecyclerviewBinding
    private lateinit var adapter: OuterCompleteAdapter
    private val user = MyApplication.getUser()
    private val token = user.getString("jwt", "").toString()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MatchingcompleteRecyclerviewBinding.inflate(inflater, container, false)

        binding.textView10.visibility = View.VISIBLE
        binding.notShowing1.visibility = View.GONE
//        outerItemList = listOf(
//            OuterItem2(
//                "성수동",
//                listOf("숭실대입구 4번출구 바로 앞 가성비 좋지않냐? 아닌가? 나도 모름", "내부 아이템 1-2", "내부 아이템 1-3")
//            ),
//            OuterItem2(
//                "동선동",
//                listOf("내부 아이템 2-1", "내부 아이템 2-2", "내부 아이템 2-3", "내부 아이템 2-4")
//            ),
//            OuterItem2("목동"),
//            OuterItem2(
//                "성신여대",
//                listOf("내부 아이템 4-1", "내부 아이템 4-2", "내부 아이템 4-3", "내부 아이템 4-4", "내부 아이템 4-5")
//            )
//        )

        val call = RetrofitObject.getRetrofitService.usermatchitem("Bearer $token", "MATCH_COMPLETE")
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
                                binding.notShowing1.visibility = View.VISIBLE
                                setupRecyclerView(responseBody.data)
                            } else {
                                // 데이터가 없을 때 처리
                                binding.textView10.visibility = View.VISIBLE
                                binding.notShowing1.visibility = View.GONE
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        setupRecyclerView(outerItemList)
    }

    private fun setupRecyclerView(outerItemList: List<RetrofitClient2.UserMatchitemData>) {
        // RecyclerView의 레이아웃 매니저 설정
        binding.optionRv.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)

        // RecyclerView의 어댑터 설정
        adapter = OuterCompleteAdapter(outerItemList)
        binding.optionRv.adapter = adapter
    }
//    data class OuterItem2(val title: String, val innerItemList: List<String>? = null) {
//        fun getItemCount(): Int {
//            return innerItemList?.size ?: 0
//        }
//    }
}