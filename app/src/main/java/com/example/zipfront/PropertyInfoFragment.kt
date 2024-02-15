package com.example.zipfront

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.zipfront.connection.RetrofitClient2
import com.example.zipfront.databinding.FragmentPropertyinfoBinding
import com.example.zipfront.databinding.MatchingcompleteRecyclerviewBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PropertyInfoFragment : Fragment() {

    lateinit var binding: FragmentPropertyinfoBinding

    private val user = MyApplication.getUser()
    private val token = user.getString("jwt", "").toString()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPropertyinfoBinding.inflate(inflater, container, false)

        //이미지 ViewPager
        val propertyImageAdater = PropertyImageAdater(this)
        propertyImageAdater.addFragment(PropertyBannerFragment(R.drawable.img_1))
        propertyImageAdater.addFragment(PropertyBannerFragment(R.drawable.img_1))
        propertyImageAdater.addFragment(PropertyBannerFragment(R.drawable.img_1))

        binding.propertyImgVp.adapter = propertyImageAdater
        binding.propertyImgVp.orientation= ViewPager2.ORIENTATION_HORIZONTAL


        //서버 연결
        val call = RetrofitObject.getRetrofitService.showDetailItem("Bearer $token", 2)


        call.enqueue(object : Callback<RetrofitClient2.ResponseDetail> {
            override fun onResponse(call: Call<RetrofitClient2.ResponseDetail>, response: Response<RetrofitClient2.ResponseDetail>) {
                // 통신 성공
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    Log.d("detail", responseBody.toString())
                    if (responseBody != null && responseBody.isSuccess) {
                        val data = responseBody.data
                        val addressResponse = responseBody.data.addressResponse
                        val detailResponse = responseBody.data.detailResponse
                        val optionResponse = responseBody.data.optionResponse

                        //부동산 이미지 설정
                        if (data.brokerImage != null) {
                            binding.estateImage.setImageResource(data.brokerImage)
                        }

                        //부동산명 설정
                        binding.estateText.text = data.businessName
                        //월세 000/00 부분
                        binding.propertyInfo1.text = if (!optionResponse.dealTypes.firstOrNull()?.charterPrice.isNullOrEmpty()) {
                            "전세 ${optionResponse.dealTypes.firstOrNull()?.charterPrice}"
                        } else if (!optionResponse.dealTypes.firstOrNull()?.tradingPrice.isNullOrEmpty()) {
                            "매매 ${optionResponse.dealTypes.firstOrNull()?.tradingPrice}"
                        } else if (!optionResponse.dealTypes.firstOrNull()?.monthPrice.isNullOrEmpty()) {
                            "월세 ${optionResponse.dealTypes.firstOrNull()?.monthPrice}"
                        } else {
                            "null"
                        }
                        //shortIntroduction
                        binding.propertyTv.text = detailResponse.itemContent.shortIntroduction
                        //동작구 상도동 부분
                        binding.propertyInfo4.text = addressResponse.dong
                        //방크기
                        binding.propertyInfo2.text = "${optionResponse.roomSize}m²(-평)"
                        //방종류
                        binding.propertyInfo3.text = optionResponse.roomType
                        //전세/매매/월세
                        binding.monthlyRentTv.text = if (!optionResponse.dealTypes.firstOrNull()?.charterPrice.isNullOrEmpty()) {
                            "전세"
                        } else if (!optionResponse.dealTypes.firstOrNull()?.tradingPrice.isNullOrEmpty()) {
                            "매매"
                        } else if (!optionResponse.dealTypes.firstOrNull()?.monthPrice.isNullOrEmpty()) {
                            "월세"
                        } else {
                            "null"
                        }
                        binding.rentPriceTv.text = if (!optionResponse.dealTypes.firstOrNull()?.charterPrice.isNullOrEmpty()) {
                            optionResponse.dealTypes.firstOrNull()?.charterPrice
                        } else if (!optionResponse.dealTypes.firstOrNull()?.tradingPrice.isNullOrEmpty()) {
                            optionResponse.dealTypes.firstOrNull()?.tradingPrice
                        } else if (!optionResponse.dealTypes.firstOrNull()?.monthPrice.isNullOrEmpty()){
                            optionResponse.dealTypes.firstOrNull()?.monthPrice
                        } else {
                            "null"
                        }
                        //관리비
                        binding.administrationPriceTv.text = optionResponse.managementOptions.firstOrNull()?.managementPrice
                        for (managementOption in optionResponse.managementOptions) {
                            if (managementOption.brokerManagementOptionId == 1) {
                                binding.optionBtn1.text = "옵션1"
                                binding.optionBtn1.visibility = View.VISIBLE
                            } else if (managementOption.brokerManagementOptionId == 2) {
                                binding.optionBtn2.text = "옵션2"
                                binding.optionBtn2.visibility = View.VISIBLE
                            } else if (managementOption.brokerManagementOptionId == 3) {
                                binding.optionBtn2.text = "전기세"
                                binding.optionBtn2.visibility = View.VISIBLE
                            }
                        }









                    } else {
                        Toast.makeText(requireContext(), responseBody?.message ?: "Unknown error", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            override fun onFailure(call: Call<RetrofitClient2.ResponseDetail>, t: Throwable) {
                Log.d("message", "")
            }
        })



        return binding.root
        /*
        call.enqueue(object : Callback<RetrofitClient2.ResponseWithdraw> {
            override fun onResponse(
                call: Call<RetrofitClient2.ResponseWithdraw>, response: Response<RetrofitClient2.ResponseWithdraw>
            ) {
                Log.d("Retrofit31", response.toString())
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    Log.d("Retrofit3", responseBody.toString())
                    if (responseBody != null && responseBody.isSuccess) {
                        val intent = Intent(this@MenuSecessionActivity, LoginActivity::class.java)
                        val stackBuilder = TaskStackBuilder.create(this@MenuSecessionActivity)
                        stackBuilder.addNextIntentWithParentStack(intent)
                        stackBuilder.startActivities()
                    } else {
                        Toast.makeText(this@MenuSecessionActivity, responseBody?.message ?: "Unknown error", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            override fun onFailure(call: Call<RetrofitClient2.ResponseWithdraw>, t: Throwable) {
                val errorMessage = "Call Failed: ${t.message}"
                Log.d("Retrofit", errorMessage)
            }
        }) */


    }
}