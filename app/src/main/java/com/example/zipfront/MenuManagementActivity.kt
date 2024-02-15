package com.example.zipfront

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.zipfront.connection.RetrofitClient2
import com.example.zipfront.databinding.FitstMenuManagementBinding
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MenuManagementActivity : AppCompatActivity() {
    lateinit var binding : FitstMenuManagementBinding
    private val list = ArrayList<ManagementData>()
    private val user = MyApplication.getUser()
    private val token = user.getString("jwt", "").toString()

    var info1 : String = ""
    var info2 : String = ""
    var info3 : String = ""
    var info4 : String = ""
    var info5 : String = ""
    var tv : String = ""
    var brokerItemId: Int = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FitstMenuManagementBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.leftImage.setOnClickListener {
            finish()
        }


        //매물 전체 조회 API 연동
        //이미지, 전세 1억 5천만원, 평, 3층, 관리비 3만, 동작구 상도동, 숭실대입구 4번출구바로앞
        val call = RetrofitObject.getRetrofitService.brokeritem("Bearer $token")

        call.enqueue(object : Callback<RetrofitClient2.ResponseBrokeritem> {
            override fun onResponse(call: Call<RetrofitClient2.ResponseBrokeritem>, response: Response<RetrofitClient2.ResponseBrokeritem>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    Log.d("Retrofit2", response.toString())
                    if(responseBody != null){
                        if(responseBody.isSuccess) {
                            val responseData = responseBody.data
                            var imgRes : RequestCreator

                            for (data in responseData) {
                                Log.d("Retrofit3", data.toString())
                                //이미지 설정
                                val imageUrl = data.detailResponse.itemImages.firstOrNull()?.imageUrl
                                imgRes = Picasso.get().load(imageUrl)

                                info1 = if (data.optionResponse.dealTypes.firstOrNull()?.dealType == "CHARTER") {
                                    "매매 ${data.optionResponse.dealTypes.firstOrNull()?.charterPrice ?: "-"}"
                                } else if (data.optionResponse.dealTypes.firstOrNull()?.dealType == "TRADING") {
                                    "전세 ${data.optionResponse.dealTypes.firstOrNull()?.tradingPrice ?: "-"}"
                                } else if (data.optionResponse.dealTypes.firstOrNull()?.dealType == "MONTHLY") {
                                    "월세 ${data.optionResponse.dealTypes.firstOrNull()?.monthPrice ?: "-"}"
                                } else {
                                    "전세 1억 5천만원"
                                }
                                info2 = "${translateToKorean(data.optionResponse.roomSize)}, "
                                info3 = if (translateToKorean(data.optionResponse.floors.firstOrNull()?.floor) == "") {
                                    "-층, "
                                } else{
                                    "${translateToKorean(data.optionResponse.floors.firstOrNull()?.floor)}, "
                                }

                                info4 = "관리비 ${data.optionResponse.managementOptions.firstOrNull()?.managementPrice ?: "-"}"
                                info5 = data.addressResponse.addressName
                                tv = data.detailResponse.itemContent.shortIntroduction
                                brokerItemId = data.brokerItemId

                                //리스트에 추가
                                list.add(ManagementData(imgRes, info1, info2, info3, info4, info5, tv, brokerItemId))
                            }
                            bindingRV(list)
                        }else{
                            Toast.makeText(this@MenuManagementActivity, responseBody?.message ?: "Unknown error", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

            override fun onFailure(call: Call<RetrofitClient2.ResponseBrokeritem>, t: Throwable) {
                val errorMessage = "Call Failed: ${t.message}"
                Log.d("Retrofit", errorMessage)
            }
        })


        val recyclerView = RecyclerView(this)

        binding.addPropertyBtn.setOnClickListener {
            val intent = Intent(this, AdditionalActivity0::class.java)
            startActivity(intent)
        }

        checkList()
    }

    private fun checkList() {
        if (list.isEmpty()) {
            binding.noPropertyTv.visibility = View.VISIBLE
            binding.propertyManagementRv.visibility = View.GONE

        } else {
            binding.noPropertyTv.visibility = View.GONE
            binding.propertyManagementRv.visibility = View.VISIBLE
        }
    }

    private fun translateToKorean(keyword: String?): String {
        return when (keyword) {
            "ONE_ROOM" -> "원룸"
            "TWO_OR_THREE_ROOM" -> "투룸/쓰리룸"
            "OFFICETELS" -> "오피스텔"
            "APARTMENT" -> "아파트"
            "CHARTER" -> "전세"
            "TRADING" -> "매매"
            "MONTHLY" -> "월세"
            "UNDER_FIVE" -> "~5평"
            "TEN" -> "10평대"
            "TWENTY" -> "20평대"
            "THIRTY" -> "30평대"
            "FORTY" -> "40평대"
            "FIFTY" -> "50평대"
            "OVER_SIXTY" -> "60평대~"
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
            else -> keyword ?: ""
        }
    }

    private fun bindingRV(list : ArrayList<ManagementData>) {
        binding.propertyManagementRv.layoutManager = LinearLayoutManager(this)
        binding.propertyManagementRv.adapter = ManagementAdapter(list)
        binding.propertyManagementRv.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))

        checkList()
    }

}