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
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Locale

class PropertyInfoFragment : Fragment() {

    companion object {
        fun newInstance(brokerItemId: Int): PropertyInfoFragment {
            val fragment = PropertyInfoFragment()
            val args = Bundle()
            args.putInt("brokerItemId", brokerItemId)
            fragment.arguments = args
            return fragment
        }
    }

    //전화번호 데이터 보내기



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

        binding.propertyImgVp.adapter = propertyImageAdater
        binding.propertyImgVp.orientation= ViewPager2.ORIENTATION_HORIZONTAL


        //brokerItemID 받아오기
        val brokerItemId = arguments?.getInt("brokerItemId", 1) ?: 1
//        Toast.makeText(requireContext(), "$brokerItemId", Toast.LENGTH_SHORT).show()


        //서버 연결
        val call = RetrofitObject.getRetrofitService.showDetailItem("Bearer $token", brokerItemId)


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

                        //뷰페이저 설정
                        val imageUrl = data.detailResponse.itemImages.firstOrNull()?.imageUrl
                        val imgRes = Picasso.get().load(imageUrl)
                        propertyImageAdater.addFragment(PropertyBannerFragment(imgRes))
                        propertyImageAdater.addFragment(PropertyBannerFragment(imgRes))
                        propertyImageAdater.addFragment(PropertyBannerFragment(imgRes))

                        //부동산 이미지 설정
                        if (data.brokerImage != null) {
                            binding.estateImage.setImageResource(data.brokerImage)
                        }

                        //부동산명 설정
                        binding.estateText.text = data.businessName
                        //월세 000/00 부분
                        binding.propertyInfo1.text = if (data.optionResponse.dealTypes.firstOrNull()?.dealType == "CHARTER") {
                            "전세 ${data.optionResponse.dealTypes.firstOrNull()?.charterPrice ?: "-"}"
                        } else if (data.optionResponse.dealTypes.firstOrNull()?.dealType == "TRADING") {
                            "매매 ${data.optionResponse.dealTypes.firstOrNull()?.tradingPrice ?: "-"}"
                        } else if (data.optionResponse.dealTypes.firstOrNull()?.dealType == "MONTHLY") {
                            "월세 ${data.optionResponse.dealTypes.firstOrNull()?.monthPrice ?: "-"}"
                        } else {
                            "월세 000/00"
                        }
                        //shortIntroduction
                        binding.propertyTv.text = detailResponse.itemContent.shortIntroduction
                        //동작구 상도동 부분
                        binding.propertyInfo4.text = addressResponse.dong
                        //방크기
                        binding.propertyInfo2.text = translateToKorean(optionResponse.roomSize)
                        //방종류
                        binding.propertyInfo3.text = optionResponse.floors.firstOrNull()?.customFloor
                        //전세/매매/월세
                        binding.monthlyRentTv.text = if (data.optionResponse.dealTypes.firstOrNull()?.dealType == "CHARTER") {
                            "전세"
                        } else if (data.optionResponse.dealTypes.firstOrNull()?.dealType == "TRADING") {
                            "매매"
                        } else if (data.optionResponse.dealTypes.firstOrNull()?.dealType == "MONTHLY") {
                            "월세"
                        } else {
                            "방세"
                        }
                        binding.rentPriceTv.text = if (!optionResponse.dealTypes.firstOrNull()?.charterPrice.isNullOrEmpty()) {
                            optionResponse.dealTypes.firstOrNull()?.charterPrice
                        } else if (!optionResponse.dealTypes.firstOrNull()?.tradingPrice.isNullOrEmpty()) {
                            optionResponse.dealTypes.firstOrNull()?.tradingPrice
                        } else if (!optionResponse.dealTypes.firstOrNull()?.monthPrice.isNullOrEmpty()){
                            optionResponse.dealTypes.firstOrNull()?.monthPrice
                        } else {
                            "000/00"
                        }
                        //관리비
                        binding.administrationPriceTv.text = optionResponse.managementOptions.firstOrNull()?.managementPrice
                        for (managementOption in optionResponse.managementOptions) {
                            if (managementOption.brokerManagementOptionId == 1) {
                                binding.optionBtn1.text = "전기세"
                                binding.optionBtn1.visibility = View.VISIBLE
                            } else if (managementOption.brokerManagementOptionId == 2) {
                                binding.optionBtn2.text = "수도세"
                                binding.optionBtn2.visibility = View.VISIBLE
                            } else if (managementOption.brokerManagementOptionId == 3) {
                                binding.optionBtn3.text = "전기세"
                                binding.optionBtn3.visibility = View.VISIBLE
                            } else if (managementOption.brokerManagementOptionId == 4) {
                                binding.optionBtn4.text = "수도세"
                                binding.optionBtn4.visibility = View.VISIBLE
                            }
                        }

                        //상세 정보 위치
                        binding.locationInfoTv.text = addressResponse.addressName
                        //방종류
                        binding.roomTypeInfoTv.text = translateToKorean(optionResponse.roomType)
                        //층수
                        binding.floorInfoTv.text = optionResponse.floors.firstOrNull()?.customFloor
                        //방크기
                        binding.roomSizeInfoTv.text = translateToKorean(optionResponse.roomSize)
                        //사용 승인일
                        binding.approvalDateInfoTv.text = optionResponse.approvedDate
                        //최초 등록일
                        val inputDate = data.createdAt
                        val convertedDate = convertDateFormat(inputDate)
                        binding.registrationDateInfoTv.text = convertedDate
                        //내부 시설
                        for (internalFacility in optionResponse.internalFacilities) {
                            if (internalFacility.brokerInternalFacilityId == 1) {
                                binding.facilityBtn1.text = "에어컨"
                                binding.facilityBtn1.visibility = View.VISIBLE
                            } else if (internalFacility.brokerInternalFacilityId == 2) {
                                binding.facilityBtn2.text = "세탁기"
                                binding.facilityBtn2.visibility = View.VISIBLE
                            } else if (internalFacility.brokerInternalFacilityId == 3) {
                                binding.facilityBtn3.text = "에어컨"
                                binding.facilityBtn3.visibility = View.VISIBLE
                            } else if (internalFacility.brokerInternalFacilityId == 4) {
                                binding.facilityBtn4.text = "세탁기"
                                binding.facilityBtn4.visibility = View.VISIBLE
                            } else {
                                binding.facilityBtn5.text = "옵션5"
                                binding.facilityBtn5.visibility = View.VISIBLE
                            }
                        }
                        //상세 설명
                        binding.detailTextTv.text = detailResponse.itemContent.specificIntroduction


                        //전화번호 정보




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
    }


    private fun translateToKorean(keyword: String?): String {
        return when (keyword) {
            "ONE_ROOM" -> "원룸"
            "TWO_OR_THREEROOM" -> "투룸/쓰리룸"
            "OFFICETELS" -> "오피스텔"
            "APARTMENT" -> "아파트"
            "CHARTER" -> "전세"
            "TRADING" -> "매매"
            "MONTHLY" -> "월세"
            "UNDER_FIVE" -> "~5"
            "TEN" -> "10"
            "TWENTY" -> "20"
            "THIRTY" -> "30"
            "FORTY" -> "40"
            "FIFTY" -> "50"
            "OVER_SIXTY" -> "60"
            "ONE" -> "1층"
            "TWO" -> "2층"
            "THREE" -> "3층"
            "FOUR" -> "4층"
            "FIVE" -> "5층"
            "SIX" -> "6층"
            "SEMI_LAYER" -> "반지층"
            "ROOFTOP" -> "옥탑방"
            "ELECTRONIC_FEE" -> "전기세"
            "GAS_FEE" -> "가스비"
            "INTERNET_FEE" -> "인터넷"
            "PARKING_FEE" -> "주차여부"
            "WATER_FEE" -> "수도세"
            "AIR_CONDITIONER" -> "에어컨"
            "REFRIGERATOR" -> "냉장고"
            "WASHING_MACHINE" -> "세탁기"
            "MICROWAVE" -> "전자레인지"
            "CLOSET" -> "옷장"
            "TABLE" -> "책상"
            "TV" -> "TV"
            "BED" -> "침대"
            "ALL" -> "전체"
            "UNDER_ONE_YEAR" -> "1년 이내"
            "UNDER_FIVE_YEARS" -> "5년 이내"
            "UNDER_TEN_YEARS" -> "10년 이내"
            "UNDER_FIFTEEN_YEARS" -> "15년 이내"
            "OVER_FIFTEEN_YEARS" -> "15년 이상"
            "PARKING" -> "주차장"
            "SHORT_LOAN" -> "단기임대"
            "FULL_OPTION" -> "풀옵션"
            "ELEVATOR" -> "엘리베이터"
            "VERANDA" -> "베란다"
            "SECURITY" -> "보안/안전시설"
            "VR" -> "360°VR"
            "NON_FACE_CONTRACT" -> "비대면계약"
            "UNDER_17" -> "~ 17m²"
            "UNDER_66" -> "33~66m²"
            "UNDER_99" -> "66~99m²"
            "UNDER_132" -> "99~132m²"
            "UNDER_165" -> "132~165m²"
            "UNDER_198" -> "165~198m²"
            "OVER_198" -> "198m² ~"
            else -> keyword ?: ""
        }
    }


    fun convertDateFormat(inputDate: String): String {
        // 입력된 날짜 문자열의 형식 지정
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS", Locale.getDefault())
        // 출력할 날짜 형식 지정
        val outputFormat = SimpleDateFormat("yyyy년 MM월 dd일", Locale.getDefault())

        try {
            // 입력된 날짜 문자열을 Date 객체로 파싱
            val date = inputFormat.parse(inputDate)

            // 출력 형식에 맞게 변환된 문자열 반환
            return outputFormat.format(date)
        } catch (e: Exception) {
            e.printStackTrace()
            return "ALL"
        }
    }

}