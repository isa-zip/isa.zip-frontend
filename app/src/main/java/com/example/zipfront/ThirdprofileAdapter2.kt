package com.example.zipfront

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.zipfront.connection.RetrofitClient2
import com.squareup.picasso.Picasso

class ThirdprofileAdapter2(private val itemList: MutableList<RetrofitClient2.MatchDetail2> = mutableListOf()) : RecyclerView.Adapter<ThirdprofileAdapter2.ViewHolder>() {

    private val selectedItems = mutableListOf<Boolean>()
    private var onItemCountChangeListener: OnItemCountChangeListener? = null
    private var sortedList = itemList.sortedBy { it.matchId }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_matchingprofilenew, parent, false)
        return ViewHolder(view)
    }

    interface OnButtonClickListener {
        fun onButtonClick(position: Int)
    }
    interface OnItemCountChangeListener {
        fun onItemCountChanged(itemCount: Int)
    }

    private val itemCountChangeListenerList = mutableListOf<OnItemCountChangeListener>()

    fun addOnItemCountChangeListener(listener: OnItemCountChangeListener) {
        itemCountChangeListenerList.add(listener)
    }

   fun notifyItemCountChanged() {
        val selectedItemCount = getSelectedItemCount()
        Log.d("MyApp32", "Selected Item Count: $selectedItemCount")
        onItemCountChangeListener?.onItemCountChanged(selectedItemCount)
        itemCountChangeListenerList.forEach { it.onItemCountChanged(selectedItemCount) }
    }

    fun setOnItemCountChangeListener(listener: OnItemCountChangeListener) {
        this.onItemCountChangeListener = listener
    }

    private fun getSelectedItemCount(): Int {
        val count = itemList.size
        Log.d("MyApp33", "$count")
        return count
    }

    var onButtonClickListener: OnButtonClickListener? = null

    // 선택한 아이템의 위치 저장
    private var selectedItemPosition: Int = -1

    fun setSelectedItemPosition(position: Int) {
        selectedItemPosition = position
        notifyDataSetChanged()
        notifyItemCountChanged()
    }

    fun setItems(items: List<RetrofitClient2.MatchDetail2>) {
        itemList.clear()
        itemList.addAll(items)
        sortedList = itemList.sortedBy { it.matchId }
        notifyDataSetChanged()
        notifyItemCountChanged()
    }


    fun addItems(items: MutableList<RetrofitClient2.MatchDetail2>) {
        itemList.addAll(items)
        selectedItems.addAll(List(items.size) { false })
        sortedList = itemList.sortedBy { it.matchId } // sortedList 업데이트
        notifyDataSetChanged()
        notifyItemCountChanged()
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = sortedList[position]
        holder.bind(item)
//        holder.updateBackground(selectedItems[position])
    }

    override fun getItemCount(): Int {
        Log.d("ThirdprofileAdapter", "${sortedList.size}")
        return sortedList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            // 아이템 전체에 대한 클릭 리스너 설정
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, ZItmeInfoActivity4::class.java)
                itemView.context.startActivity(intent)
            }
        }

        fun bind(item: RetrofitClient2.MatchDetail2) {
            val textView1: TextView = itemView.findViewById(R.id.textView25)
            val textView2: TextView = itemView.findViewById(R.id.textView26)
            val textView3: TextView = itemView.findViewById(R.id.textView27)//동 정보
            val textView4: TextView = itemView.findViewById(R.id.textView28)
            val imageView: ImageView = itemView.findViewById(R.id.imageView19)

            val profileView1: TextView = itemView.findViewById(R.id.textView59)
            val profileView: ImageView = itemView.findViewById(R.id.imageView18)

            val tradingDeal = item.brokerItemResponse.optionResponse.dealTypes.firstOrNull { it.dealType == "TRADING" }
            val charterDeal = item.brokerItemResponse.optionResponse.dealTypes.firstOrNull { it.dealType == "CHARTER" }
            val monthDeal = item.brokerItemResponse.optionResponse.dealTypes.firstOrNull { it.dealType == "MONTHLY" }

            val tradingPriceText = when {
                tradingDeal != null && tradingDeal.tradingPrice != null -> "전세 ${tradingDeal.tradingPrice}"
                else -> ""
            }

            val charterPriceText = when {
                charterDeal != null && charterDeal.charterPrice != null -> "매매 ${charterDeal.charterPrice} "
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

            profileView1.text = item.userItemResponse.userNickname

            textView3.text = item.brokerItemResponse.addressResponse.dong

            // 방 크기, 층, 관리비 설정
            val roomSize = translateToKorean(item.brokerItemResponse.optionResponse.roomSize)
            val floors = item.brokerItemResponse.optionResponse.floors
                .mapNotNull { translateToKorean(it.floor) }
                .joinToString(", ")

            val managementPrice = item.brokerItemResponse.optionResponse.managementOptions.firstOrNull()?.let { translateToKorean(it.managementPrice) } ?: "-"
            textView2.text = "$roomSize, $floors, $managementPrice"


            // 짧은 소개 설정
            val shortIntroduction = item.brokerItemResponse.detailResponse.itemContent.shortIntroduction
            textView4.text = "$shortIntroduction"

            // 이미지 로딩
            val imageUrl = item.brokerItemResponse.detailResponse.itemImages.firstOrNull()?.imageUrl
            if (imageUrl != null) {
                Picasso.get().load(imageUrl).into(imageView)
            }

            val imageUrl2 = item.userImage?.firstOrNull()
            if (imageUrl2 != null) {
                Glide.with(itemView)
                    .load(imageUrl2)
                    .into(profileView)
            } else {
                // 이미지 URL이 없는 경우 기본 이미지를 사용
                profileView.setImageResource(R.drawable.btn_image_bed)
            }
        }
//        fun updateBackground(isSelected: Boolean) {
//            val newBackgroundResource = if (isSelected) R.drawable.btn_add_gray else R.drawable.btn_add__1_
//            // Update the background resource of the imageView
//            imageView.setImageResource(newBackgroundResource)
//            Log.d("MyApp3", "$isSelected")
//        }
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