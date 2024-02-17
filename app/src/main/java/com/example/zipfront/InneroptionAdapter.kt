package com.example.zipfront

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.zipfront.connection.RetrofitClient2

class InneroptionAdapter(private val itemList: List<RetrofitClient2.MatchedBrokerItemResponse>) : RecyclerView.Adapter<InneroptionAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_matchingoption, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            // 아이템 전체에 대한 클릭 리스너 설정
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, ZItmeInfoActivity1::class.java)
                // ZItemInfoActivity1로 이동
                itemView.context.startActivity(intent)
            }
        }
        fun bind(item: RetrofitClient2.MatchedBrokerItemResponse) {
            val textView1: TextView = itemView.findViewById(R.id.textView6)
            val textView2: TextView = itemView.findViewById(R.id.textView7)
            val imageView: ImageView = itemView.findViewById(R.id.imageView9)

            // 거래 유형과 해당하는 금액을 함께 표시
            val dealTypesWithPrices = item.optionResponse.dealTypes.joinToString(" / ") { dealType ->
                val dealPrice = when {
                    dealType.tradingPrice != null -> "${dealType.tradingPrice}"
                    dealType.charterPrice != null -> "${dealType.charterPrice}"
                    dealType.monthPrice != null -> "${dealType.monthPrice}"
                    else -> "" // 다른 거래 유형이 있는 경우 여기에 추가
                }
                "${translateToKorean(dealType.dealType)} $dealPrice"
            }
            textView1.text = dealTypesWithPrices
            textView2.text = item.businessName

            // Glide를 사용하여 이미지 로드 및 설정
            Glide.with(itemView)
                .load(item.detailResponse.itemImages.firstOrNull()?.imageUrl) // 첫 번째 이미지 URL을 사용합니다.
                .into(imageView)
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