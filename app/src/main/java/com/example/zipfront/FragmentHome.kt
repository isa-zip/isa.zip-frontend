package com.example.zipfront

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.zipfront.connection.RetrofitClient2
import com.example.zipfront.databinding.HomeFragmentBinding
import com.example.zipfront.databinding.SplashBinding
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FragmentHome: Fragment() {
    lateinit var binding: HomeFragmentBinding
    private lateinit var adapter: PoseAdapter
    private lateinit var adapter2: OuterhomeAdapter
    internal val matchingAdapter: MatchingAdapter by lazy {
        MatchingAdapter(requireActivity())
    }
    private val user = MyApplication.getUser()
    private val token = user.getString("jwt", "").toString()
    private var Dong: String? = null // nullable로 변경
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = HomeFragmentBinding.inflate(inflater, container, false)

        binding.propertylayout.visibility=View.VISIBLE
        binding.propertylayout3.visibility=View.GONE

        binding.propertylayout2.visibility = View.VISIBLE
        binding.propertylayout22.visibility = View.GONE
        // RecyclerView 찾기

        // RecyclerView에 사용할 아이템 리스트 생성
        val itemList = listOf("아이템 1", "아이템 2", "아이템 3")

//        outerItemList = listOf(
//            OuterItem(
//                "상도동",
//                listOf("내부 아이템 1-1", "내부 아이템 1-2", "내부 아이템 1-3")
//            )
//        )

        val call = RetrofitObject.getRetrofitService.home("Bearer $token")
        call.enqueue(object : Callback<RetrofitClient2.ResponseHome> {
            override fun onResponse(
                call: Call<RetrofitClient2.ResponseHome>,
                response: Response<RetrofitClient2.ResponseHome>
            ) {
                Log.d("Retrofit61", response.toString())
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    Log.d("Retrofit6", responseBody.toString())
                     if (responseBody != null) {
                        if (responseBody.isSuccess) {
                            val matchedItems = responseBody.data.matchedItems
                            if (matchedItems != null) {
                                binding.propertylayout.visibility = View.GONE
                                binding.propertylayout3.visibility = View.VISIBLE
                                Dong = matchedItems.dong
                                binding.titletext4.text=Dong
                                setupRecyclerView2(matchedItems.matchedBrokerItemResponses)
                            }
                            else
                            {
                                binding.propertylayout.visibility = View.VISIBLE
                                binding.propertylayout3.visibility = View.GONE
                            }
                            val isaSchedule = responseBody.data.movingSchedule
                            if (isaSchedule != null) {
                                binding.propertylayout2.visibility = View.GONE
                                binding.propertylayout22.visibility = View.VISIBLE
                                binding.textView60.text= "${isaSchedule.month.toString()}월"
                                binding.textView61.text= isaSchedule.event.eventDate
                                binding.textView62.text= isaSchedule.event.eventTitle
                            }
                            else
                            {
                                binding.propertylayout2.visibility = View.VISIBLE
                                binding.propertylayout22.visibility = View.GONE
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
            override fun onFailure(call: Call<RetrofitClient2.ResponseHome>, t: Throwable) {
                val errorMessage = "Call Failed: ${t.message}"
                Log.d("Retrofit", errorMessage)
            }
        })
        val bottomSheetFragment = MatchingBottomsheetFragment(requireContext().applicationContext, matchingAdapter)

        // RecyclerView에 아이템 리스트 설정
        setupRecyclerView(itemList)

//        setupRecyclerView2(outerItemList)

        val bannerAdapter = BannerAdapter(this)
        bannerAdapter.addFragment(BannerFragment(R.drawable.banner))
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp))

        binding.viewPager.adapter=bannerAdapter
        binding.viewPager.orientation= ViewPager2.ORIENTATION_HORIZONTAL

        binding.imageView6.setOnClickListener {
            bottomSheetFragment.show(childFragmentManager, bottomSheetFragment.tag)
        }

        binding.imageButton.setOnClickListener {
            bottomSheetFragment.show(childFragmentManager, bottomSheetFragment.tag)
        }
        
        binding.imageView7.setOnClickListener {
            startActivity(Intent(requireContext(), ScheduleActivityHome::class.java))
        }

        binding.imageButton2.setOnClickListener {
            startActivity(Intent(requireContext(), ScheduleActivityHome::class.java))
        }

        return binding.root
    }

    private fun setupRecyclerView(itemList: List<String>) {
        // RecyclerView의 레이아웃 매니저 설정
        binding.poseRv.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

        // RecyclerView의 어댑터 설정
        adapter = PoseAdapter(itemList)
        binding.poseRv.adapter = adapter
    }

    private fun setupRecyclerView2(outerItemList: List<RetrofitClient2.HomeItemResponse>) {
        // RecyclerView의 레이아웃 매니저 설정
        binding.recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

        // RecyclerView의 어댑터 설정
        adapter2 = OuterhomeAdapter(outerItemList)
        binding.recyclerView.adapter = adapter2
    }

}