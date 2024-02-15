package com.example.zipfront

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.zipfront.connection.RetrofitClient2
import com.squareup.picasso.Picasso

class BottomSheetAdapter(private val fragment: UploadBottomsheetFragment): RecyclerView.Adapter<BottomSheetAdapter.ViewHolder>() {

    private var data: List<RetrofitClient2.BrokerItem> = emptyList()
    private val selectedItems = mutableListOf<RetrofitClient2.BrokerItem>()

    fun setData(data: List<RetrofitClient2.BrokerItem>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_matchinguploadbottom, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun getSelectedItems(): List<RetrofitClient2.BrokerItem> {
        return selectedItems.toList()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textView1: TextView = itemView.findViewById(R.id.textView25)
        private val textView2: TextView = itemView.findViewById(R.id.textView26)
        private val textView3: TextView = itemView.findViewById(R.id.textView27)
        private val textView4: TextView = itemView.findViewById(R.id.textView28)
        private val bottomSheetLayout: View = itemView.findViewById(R.id.bottomsheetlayout)
        private val imageView: ImageView = itemView.findViewById(R.id.imageView19)
        private var isChecked: Boolean = false

        fun bind(item: RetrofitClient2.BrokerItem) {
            // 여기서 item에 따라 뷰 업데이트 로직 작성
            Log.d("Retrofit76", "$item")
            val tradingDeal = item.optionResponse.dealTypes.firstOrNull { it.dealType == "TRADING" }
            val charterDeal = item.optionResponse.dealTypes.firstOrNull { it.dealType == "CHARTER" }
            val monthDeal = item.optionResponse.dealTypes.firstOrNull { it.dealType == "MONTHLY" }

            val tradingPriceText = when {
                tradingDeal != null && tradingDeal.charterPrice != null -> "전세 ${tradingDeal.charterPrice}"
                tradingDeal != null -> "전세 ${tradingDeal.tradingPrice}"
                else -> ""
            }

            val charterPriceText = when {
                charterDeal != null && charterDeal.charterPrice != null -> "매매 ${charterDeal.charterPrice} "
                charterDeal != null -> "매매 ${charterDeal.tradingPrice}"
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

            // 방 크기, 층, 관리비 설정
            val roomSize = translateToKorean(item.optionResponse.roomSize)
            val floors = item.optionResponse.floors
                .mapNotNull { translateToKorean(it.floor) }
                .joinToString(", ")

            val managementPrice = item.optionResponse.managementOptions.firstOrNull()?.let { translateToKorean(it.managementPrice) } ?: "-"
            textView2.text = "$roomSize, $floors, $managementPrice"

            // 동 정보 설정
            val dong = item.addressResponse.dong
            textView3.text = "$dong"

            // 짧은 소개 설정
            val shortIntroduction = item.detailResponse.itemContent.shortIntroduction
            textView4.text = "$shortIntroduction"

            // 이미지 로딩
            val imageUrl = item.detailResponse.itemImages.firstOrNull()?.imageUrl
            if (imageUrl != null) {
                Picasso.get().load(imageUrl).into(imageView)
            }
            itemView.setOnClickListener {
                if (selectedItems.contains(item)) {
                    // 이미 선택된 아이템이면 제거
                    selectedItems.remove(item)
                    bottomSheetLayout.setBackgroundResource(R.drawable.rounded_background)
                } else {
                    // 선택되지 않은 아이템이면 추가
                    selectedItems.add(item)
                    bottomSheetLayout.setBackgroundResource(R.drawable.rounded_background_blue)
                }

                fragment.updateBtnCloseBackground()
                Log.d("Retrofit84", selectedItems.toString())
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
            "VR" -> "360°VRㅅ"
            "NON_FACE_CONTRACT" -> "비대면계약"
            else -> keyword ?: ""
        }
    }
}