package com.example.zipfront

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.zipfront.connection.RetrofitClient2
import com.squareup.picasso.Picasso

class InnerCompleteAdapter (private val itemList: List<RetrofitClient2.MatchedBrokerItemResponse>) : RecyclerView.Adapter<InnerCompleteAdapter.ViewHolder>() {

    // onCreateViewHolder 메서드에서 레이아웃 관리자 설정
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_matchingoptiondoublecomplete, parent, false)
        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]
        holder.bind(item)
        Log.d("InnerCompleteAdapter", "$item")
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            // 아이템 전체에 대한 클릭 리스너 설정
            itemView.setOnClickListener {
                val position = adapterPosition // 클릭된 항목의 위치
                if (position != RecyclerView.NO_POSITION) {
                    val intent = Intent(itemView.context, ZItmeInfoActivity1::class.java)
                    // ZItemInfoActivity1로 이동
                    intent.putExtra("brokerItemID", itemList[position].brokerItemId)
                    itemView.context.startActivity(intent)
                }
            }
        }
        fun bind(item: RetrofitClient2.MatchedBrokerItemResponse) {
            val textView1: TextView = itemView.findViewById(R.id.textView25)
            val textView2: TextView = itemView.findViewById(R.id.textView26)
            val textView3: TextView = itemView.findViewById(R.id.textView27)//동 정보
            val textView4: TextView = itemView.findViewById(R.id.textView28)
            val imageView: ImageView = itemView.findViewById(R.id.imageView19)
//            val imagelayout: ConstraintLayout = itemView.findViewById(R.id.soldoutlayout)

            val tradingDeal = item.optionResponse.dealTypes.firstOrNull { it.dealType == "TRADING" }
            val charterDeal = item.optionResponse.dealTypes.firstOrNull { it.dealType == "CHARTER" }
            val monthDeal = item.optionResponse.dealTypes.firstOrNull { it.dealType == "MONTHLY" }

//            if (item.itemStatus!="ITEM_SELLING")
//            {
//                imagelayout.visibility=View.VISIBLE
//            }
//            else
//            {
//                imagelayout.visibility=View.GONE
//            }

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

            textView3.text = item.dongName

            // 방 크기, 층, 관리비 설정
            val roomSize = translateToKorean(item.optionResponse.roomSize)
            val floors = item.optionResponse.floors
                .mapNotNull { it.customFloor }
                .joinToString(", ")

            val managementPrice = item.optionResponse.managementOptions.firstOrNull()?.let { translateToKorean(it.managementPrice) } ?: "-"
            textView2.text = "$roomSize, $floors, $managementPrice"


            // 짧은 소개 설정
            val shortIntroduction = item.detailResponse.itemContent.shortIntroduction
            textView4.text = "$shortIntroduction"

            // 이미지 로딩
            val imageUrl = item.detailResponse.itemImages.firstOrNull()?.imageUrl
            if (imageUrl != null) {
                Picasso.get().load(imageUrl).into(imageView)
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