package com.example.zipfront

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.zipfront.connection.RetrofitClient2
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DecimalFormat

class OuterSeconduploadAdapter(
    private val innerItems: List<RetrofitClient2.UserItemResponse>
) : RecyclerView.Adapter<OuterSeconduploadAdapter.ViewHolder>() {

    private val bottomSheetFragment = UploadBottomsheetFragment()
    private val user = MyApplication.getUser()
    private val token = user.getString("jwt", "").toString()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_uploaduser_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val innerItem = innerItems[position]
        holder.bind(innerItem, holder.itemView.context)
    }

    override fun getItemCount(): Int {
        return innerItems.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(innerItem: RetrofitClient2.UserItemResponse, context: Context) {
            // 여기서 innerItem 사용하여 필요한 작업 수행
            Log.d("Retrofit66", "$innerItem")
            val layout1: ConstraintLayout = itemView.findViewById(R.id.layout1)
            val layout3: ConstraintLayout = itemView.findViewById(R.id.layout3)
            val textView: TextView = itemView.findViewById(R.id.textView30)
            val textView2: TextView = itemView.findViewById(R.id.textView22)
            val imageView: ImageView = itemView.findViewById(R.id.imageView16)
            val imageView2: ImageView = itemView.findViewById(R.id.imageView17)
            val imageButton: ImageButton = itemView.findViewById(R.id.imageButton10)

            val userroomtype: TextView = itemView.findViewById(R.id.subtext1)
            val userdealtype: TextView = itemView.findViewById(R.id.subtext2)
            val userroomsize: TextView = itemView.findViewById(R.id.subtext3)

            val userRoomTypes: TextView = itemView.findViewById(R.id.textView31)
            val userDealTypelayout1: ConstraintLayout = itemView.findViewById(R.id.optionlayout1)//월세
            val userDealTypelayout2: ConstraintLayout = itemView.findViewById(R.id.optionlayout2)//전세
            val userDealTypelayout3: ConstraintLayout = itemView.findViewById(R.id.optionlayout3)//매매
            val userCHARTER: TextView = itemView.findViewById(R.id.textView38)//전세
            val userTRADING: TextView = itemView.findViewById(R.id.textView40)//매매
            val userMONTHLY: TextView = itemView.findViewById(R.id.textView36)//월세
            val userRoomSizes: TextView = itemView.findViewById(R.id.textView42)
            val userFloorslayout: ConstraintLayout = itemView.findViewById(R.id.itemnotshow1)
            val userFloors: TextView = itemView.findViewById(R.id.textView44)
            val userManagementOptionslayout: ConstraintLayout = itemView.findViewById(R.id.itemnotshow2)
            val userManagementOptions: TextView = itemView.findViewById(R.id.textView47)
            val userInternalFacilitieslayout: ConstraintLayout = itemView.findViewById(R.id.itemnotshow3)
            val userInternalFacilities: TextView = itemView.findViewById(R.id.textView49)
            val approveDatelayout: ConstraintLayout = itemView.findViewById(R.id.itemnotshow4)
            val approveDate: TextView = itemView.findViewById(R.id.textView51)
            val userExtraFilterslayout: ConstraintLayout = itemView.findViewById(R.id.itemnotshow5)
            val userExtraFilters: TextView = itemView.findViewById(R.id.textView55)

            layout1.visibility = View.VISIBLE
            layout3.visibility = View.GONE
            // 사용자 정보 설정
            textView.text = innerItem.userNickname
            textView2.text = innerItem.userNickname

            // 사용자의 방 정보 설정
            val roomTypes =
                innerItem.userItemOptionsResponse.userRoomTypes.joinToString(" / ") { translateToKorean(it.roomType) }
            userRoomTypes.text = roomTypes
            userroomtype.text = roomTypes

            val roomSizes =
                innerItem.userItemOptionsResponse.userRoomSizes.joinToString(" / ") { translateToKorean(it.roomSize) }
            userRoomSizes.text = roomSizes
            userroomsize.text = roomSizes

            // 사용자의 거래 유형 설정
            val dealTypes =
                innerItem.userItemOptionsResponse.userDealTypes.joinToString(" / ") { it.dealType }
            userDealTypelayout1.visibility = View.GONE
            userDealTypelayout2.visibility = View.GONE
            userDealTypelayout3.visibility = View.GONE
            if (dealTypes.contains("MONTHLY")) userDealTypelayout1.visibility = View.VISIBLE
            if (dealTypes.contains("CHARTER")) userDealTypelayout2.visibility = View.VISIBLE
            if (dealTypes.contains("TRADING")) userDealTypelayout3.visibility = View.VISIBLE

            // 각 거래 유형의 텍스트 설정
            val charterDeal = innerItem.userItemOptionsResponse.userDealTypes.find { it.dealType == "CHARTER" }
            val tradingDeal = innerItem.userItemOptionsResponse.userDealTypes.find { it.dealType == "TRADING" }
            val monthlyDeal = innerItem.userItemOptionsResponse.userDealTypes.find { it.dealType == "MONTHLY" }

            userCHARTER.text = "${formatPrice(charterDeal?.minPrice)}~${formatPrice(charterDeal?.maxPrice)}"
            userTRADING.text = "${formatPrice(tradingDeal?.minPrice)}~${formatPrice(tradingDeal?.maxPrice)}"

            val minMonthlyPrice = monthlyDeal?.minMonthPrice
            val maxMonthlyPrice = monthlyDeal?.maxMonthPrice

            userMONTHLY.text = "${formatPrice(minMonthlyPrice)}~${formatPrice(maxMonthlyPrice)}"


            val dealTypes2 =
                innerItem.userItemOptionsResponse.userDealTypes.joinToString(" / ") { translateToKorean(it.dealType) }
            userdealtype.text = dealTypes2

            // 사용자의 층 정보 설정
            val floors =
                innerItem.userItemOptionsResponse.userFloors.joinToString(" / ") {  translateToKorean(it.floor) }
            userFloors.text = floors
            userFloorslayout.visibility = if (floors.isNotEmpty()) View.VISIBLE else View.GONE

            // 사용자의 관리 옵션 정보 설정
            val managementOptions =
                innerItem.userItemOptionsResponse.userManagementOptions.joinToString(" / ") { translateToKorean(it.managementOption) }
            userManagementOptions.text = managementOptions
            userManagementOptionslayout.visibility =
                if (managementOptions.isNotEmpty()) View.VISIBLE else View.GONE

            // 사용자의 내부 시설 정보 설정
            val internalFacilities =
                innerItem.userItemOptionsResponse.userInternalFacilities.joinToString(" / ") { translateToKorean(it.internalFacility) }
            userInternalFacilities.text = internalFacilities
            userInternalFacilitieslayout.visibility =
                if (internalFacilities.isNotEmpty()) View.VISIBLE else View.GONE

            // 사용자의 승인 날짜 설정
            approveDate.text = translateToKorean(innerItem.userItemOptionsResponse.approveDate)
            approveDatelayout.visibility =
                if (innerItem.userItemOptionsResponse.approveDate.isNotEmpty()) View.VISIBLE else View.GONE

            // 사용자의 추가 필터 정보 설정
            val extraFilters =
                innerItem.userItemOptionsResponse.userExtraFilters.joinToString(" / ") { translateToKorean(it.extraFilter) }
            userExtraFilters.text = extraFilters
            userExtraFilterslayout.visibility =
                if (extraFilters.isNotEmpty()) View.VISIBLE else View.GONE

            // 클릭 리스너 등록
            imageView.setOnClickListener {
                layout1.visibility = View.GONE
                layout3.visibility = View.VISIBLE
            }
            imageView2.setOnClickListener {
                layout1.visibility = View.VISIBLE
                layout3.visibility = View.GONE
            }
            val dong = innerItem.dong
            val userItmeId=innerItem.userItemId

            imageButton.setOnClickListener {
                fetchDataFromServer(innerItem.dong, userItmeId)
                bottomSheetFragment.show(
                    (context as AppCompatActivity).supportFragmentManager,
                    bottomSheetFragment.tag
                )
            }
//            bottomSheetFragment.onItemSelected = onItemSelected
        }
    }
    private fun fetchDataFromServer(dong: String, userItemId: Int) {
        val call = RetrofitObject.getRetrofitService.brokeritem("Bearer $token")
        call.enqueue(object : Callback<RetrofitClient2.ResponseBrokeritem> {
            override fun onResponse(
                call: Call<RetrofitClient2.ResponseBrokeritem>,
                response: Response<RetrofitClient2.ResponseBrokeritem>
            ) {
                Log.d("Retrofit71", response.toString())
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    Log.d("Retrofit7", responseBody.toString())
                    if (responseBody != null && responseBody.isSuccess) {
                        val userItemResponses = responseBody.data
                        val matchingItems = userItemResponses.filter { it.addressResponse.postNumber == dong }
                        if (matchingItems.isNotEmpty()) {
                            // matchingItems 중 원하는 처리를 수행
                            bottomSheetFragment.setData(matchingItems, userItemId)
                        } else {
                            // 해당하는 아이템이 없을 때의 처리
                        }
                        // 바텀시트를 표시
                    } else {
                        // 요청이 실패했을 때의 처리
                    }
                }
            }
            override fun onFailure(call: Call<RetrofitClient2.ResponseBrokeritem>, t: Throwable) {
                val errorMessage = "Call Failed: ${t.message}"
                Log.d("Retrofit", errorMessage)
            }
        })
    }
    fun formatPrice(price: String?): String {
        return price?.let {
            val numericPrice = it.toIntOrNull() ?: 0 // price를 정수로 변환, 실패하면 0으로 처리
            if (numericPrice >= 0) {
                val formattedPrice = DecimalFormat("#,###").format(numericPrice)
                "$formattedPrice"
            } else {
                ""
            }
        } ?: ""
    }
    fun translateToKorean(keyword: String): String {
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
            else -> keyword // 다른 경우에는 그대로 반환
        }
    }
}