package com.example.zipfront

import android.text.Layout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.zipfront.connection.RetrofitClient2
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InnerSecondoptionAdapter(private val itemList: List<RetrofitClient2.MatchedBrokerItemResponse>,
                               private val userItemId: Int) : RecyclerView.Adapter<InnerSecondoptionAdapter.ViewHolder>() {
    private val user = MyApplication.getUser()
    private val token = user.getString("jwt", "").toString()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_matchingoptiondoubleact, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("ItemListAdapter", "Item list size: ${itemList.size}")
        val innerItem = itemList[position]
        holder.bind(innerItem)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val activity = itemView.context as? MatchingSecondOptionActivity
        fun bind(innerItem: RetrofitClient2.MatchedBrokerItemResponse) {
            Log.d("Retrofit92", "$innerItem")
            val textView1: TextView = itemView.findViewById(R.id.textView25)
            val textView2: TextView = itemView.findViewById(R.id.textView26)
            val textView3: TextView = itemView.findViewById(R.id.textView27)//동 정보
            val textView4: TextView = itemView.findViewById(R.id.textView28)
            val imageView22: ImageView = itemView.findViewById(R.id.imageView22)
            val imageView: ImageView = itemView.findViewById(R.id.imageView19)
            val imagelayout: ConstraintLayout = itemView.findViewById(R.id.soldoutlayout)

            val tradingDeal = innerItem.optionResponse.dealTypes.firstOrNull { it.dealType == "TRADING" }
            val charterDeal = innerItem.optionResponse.dealTypes.firstOrNull { it.dealType == "CHARTER" }
            val monthDeal = innerItem.optionResponse.dealTypes.firstOrNull { it.dealType == "MONTHLY" }

            if (innerItem.itemStatus!="ITEM_SELLING")
            {
                imagelayout.visibility=View.VISIBLE
                imageView22.visibility=View.GONE
                val textColorGray500 = ContextCompat.getColor(itemView.context, R.color.Gray500)
                textView1.setTextColor(textColorGray500)
                textView2.setTextColor(textColorGray500)
                textView3.setTextColor(textColorGray500)
                textView4.setTextColor(textColorGray500)
            }
            else
            {
                imagelayout.visibility=View.GONE
            }

            val tradingPriceText = when {
                tradingDeal != null && tradingDeal.tradingPrice != null -> "매매 ${tradingDeal.tradingPrice}"
                else -> ""
            }

            val charterPriceText = when {
                charterDeal != null && charterDeal.charterPrice != null -> "전세 ${charterDeal.charterPrice} "
                else -> ""
            }

            val monthPriceText = when {
                monthDeal != null && monthDeal.monthPrice != null -> "월세 ${monthDeal.monthPrice}"
                else -> ""
            }

            val dealTypesText = listOf(tradingPriceText, charterPriceText, monthPriceText)
                .filter { it.isNotEmpty() }
                .joinToString(", ")

            val finalText = "$dealTypesText"

            textView1.text = finalText

            textView3.text = innerItem.dongName

            // 방 크기, 층, 관리비 설정
            val roomSize = translateToKorean(innerItem.optionResponse.roomSize)
            val floors = innerItem.optionResponse.floors
                .mapNotNull { it.customFloor }
                .joinToString(", ")

            val managementPrice = innerItem.optionResponse.managementOptions.firstOrNull()?.let { translateToKorean(it.managementPrice) } ?: "-"
            textView2.text = "$roomSize, $floors, $managementPrice"


            // 짧은 소개 설정
            val shortIntroduction = innerItem.detailResponse.itemContent.shortIntroduction
            textView4.text = "$shortIntroduction"

            // 이미지 로딩
            val imageUrl = innerItem.detailResponse.itemImages.firstOrNull()?.imageUrl
            if (imageUrl != null) {
                Picasso.get().load(imageUrl).into(imageView)
            }

            // 이미지 뷰 클릭 이벤트 처리
            imageView22.setOnClickListener {
                imageView22.setImageResource(R.drawable.ic_plusbox)
                val request = RetrofitClient2.RequestMatchbroker2(listOf(innerItem.matchingId),"MATCH_LIKE")
                val call = RetrofitObject.getRetrofitService.matchBrokeUserItem("Bearer $token", request)
                call.enqueue(object : Callback<RetrofitClient2.ResponseMatchbroker2> {
                    override fun onResponse(
                        call: Call<RetrofitClient2.ResponseMatchbroker2>,
                        response: Response<RetrofitClient2.ResponseMatchbroker2>
                    ) {
                        Log.d("Retrofit81", response.toString())
                        if (response.isSuccessful) {
                            val responseBody = response.body()
                            Log.d("Retrofit8", responseBody.toString())
                            if (responseBody != null && responseBody.isSuccess) {
                                activity?.setupRecyclerView2(userItemId)
                            } else {
                                // 요청이 실패했을 때의 처리
                            }
                        }
                    }
                    override fun onFailure(call: Call<RetrofitClient2.ResponseMatchbroker2>, t: Throwable) {
                        val errorMessage = "Call Failed: ${t.message}"
                        Log.d("Retrofit82", errorMessage)
                    }
                })
            }
        }
    }
    fun translateToKorean(keyword: String?): String {
        return when (keyword) {
            "ONE_ROOM" -> "원룸"
            "TWO_OR_THREE_ROOM" -> "투룸/쓰리룸"
            "OFFICETELS" -> "오피스텔"
            "APARTMENT" -> "아파트"
            "CHARTER" -> "전세"
            "TRADING" -> "매매"
            "MONTHLY" -> "월세"
            "UNDER_17" -> "~17㎡"
            "UNDER_66" -> "33~66㎡"
            "UNDER_99" -> "66~99㎡"
            "UNDER_132" -> "99~132㎡"
            "UNDER_165" -> "132~165㎡"
            "UNDER_198" -> "165~198㎡"
            "OVER_198" -> "198㎡~"
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
            "VR" -> "360°VRㅅ"
            "NON_FACE_CONTRACT" -> "비대면계약"
            else -> keyword ?: ""
        }
    }
}