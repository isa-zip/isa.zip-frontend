package com.example.zipfront

import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.zipfront.connection.RetrofitClient2
import com.example.zipfront.databinding.ActivityMainBinding
import com.example.zipfront.databinding.ActivityMatchingoptionselectBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DecimalFormat

class MatchingSecondOptionActivity : AppCompatActivity() {
    lateinit var binding: ActivityMatchingoptionselectBinding
    private lateinit var adapter: OuterSecondoptionAdapter
    private lateinit var thirdAdapter: ThirdoptionAdapter
    private val user = MyApplication.getUser()
    private val token = user.getString("jwt", "").toString()
    private var matchingID: List<Int> = ArrayList() // 또는 listOf<Int>()
    private var userItemID: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMatchingoptionselectBinding.inflate(layoutInflater)

        val title = intent.getStringExtra("title")
        val position = intent.getIntExtra("position", -1)
        userItemID = intent.getIntExtra("userItemId", -1)

        // JSON 형태의 데이터를 문자열로 가져옴
        val outerItemJson = intent.getStringExtra("outerItemJson")

        // JSON 문자열을 객체로 변환
        val gson = Gson()
        val outerItem = gson.fromJson(outerItemJson, RetrofitClient2.UserMatchitemData::class.java)

        binding.profilelayout1.visibility=View.VISIBLE
        binding.layout3.visibility=View.GONE

        // profileimage 클릭 시
        binding.profileimage.setOnClickListener {
            binding.profilelayout1.visibility = View.GONE
            binding.layout3.visibility = View.VISIBLE
        }

        // dropdowmimage 클릭 시
        binding.dropdowmimage.setOnClickListener {
            binding.profilelayout1.visibility = View.VISIBLE
            binding.layout3.visibility = View.GONE
        }

        binding.layout1.visibility=View.VISIBLE
        binding.layout2.visibility=View.GONE

        // profileimage 클릭 시
        binding.imageView16.setOnClickListener {
            binding.layout1.visibility = View.GONE
            binding.layout2.visibility = View.VISIBLE
        }

        // dropdowmimage 클릭 시
        binding.imageshow2.setOnClickListener {
            binding.layout1.visibility = View.VISIBLE
            binding.layout2.visibility = View.GONE
        }

        if (!title.isNullOrBlank() && position != -1) {
            binding.textView9.text =  "${position + 1} $title"
            binding.textView30.text =  "${position + 1} $title"
            binding.profiletext.text =  "${position + 1} $title"
        }

//        // innerItems를 받아서 RecyclerView 설정
//        val innerItems = intent.getStringArrayListExtra("innerItems")

        // thirdAdapter 초기화
        thirdAdapter = ThirdoptionAdapter(mutableListOf())


        Log.d("Retrofit9", "$outerItem")
        if (outerItem.matchingCount==0) {
            binding.optionRv.visibility = View.GONE
            binding.textView58.visibility = View.VISIBLE
        } else {
            binding.optionRv.visibility = View.VISIBLE
            binding.textView58.visibility = View.GONE
            setupRecyclerView(outerItem.matchedBrokerItemResponses,userItemID)
            setupRecyclerView2(userItemID)
        }
        binding.imageView10.setOnClickListener{
            finish()
        }

        binding.imageView17.setOnClickListener{
            showCustomDialog()
        }
        binding.imageshow3.setOnClickListener{
            showCustomDialog()
        }

        // 사용자의 방 정보 설정
        val roomTypes =
            outerItem.userRequestInfo.userItemOptionsResponse.userRoomTypes.joinToString(" / ") { translateToKorean(it.roomType) }
        binding.textView31.text = roomTypes
        binding.subtext1.text = roomTypes

        val roomSizes =
            outerItem.userRequestInfo.userItemOptionsResponse.userRoomSizes.joinToString(" / ") { translateToKorean(it.roomSize) }
        binding.textView42.text = roomSizes
        binding.subtext3.text = roomSizes

        // 사용자의 거래 유형 설정
        val dealTypes =
            outerItem.userRequestInfo.userItemOptionsResponse.userDealTypes.joinToString(" / ") { it.dealType }
        Log.d("retrofitd",dealTypes.toString())
        binding.optionlayout1.visibility = View.GONE
        binding.optionlayout2.visibility = View.GONE
        binding.optionlayout3.visibility = View.GONE
        if (dealTypes.contains("MONTHLY")) binding.optionlayout1.visibility = View.VISIBLE
        if (dealTypes.contains("CHARTER")) binding.optionlayout2.visibility = View.VISIBLE
        if (dealTypes.contains("TRADING")) binding.optionlayout3.visibility = View.VISIBLE

        // 각 거래 유형의 텍스트 설정
        val charterDeal = outerItem.userRequestInfo.userItemOptionsResponse.userDealTypes.find { it.dealType == "CHARTER" }
        val tradingDeal = outerItem.userRequestInfo.userItemOptionsResponse.userDealTypes.find { it.dealType == "TRADING" }
        val monthlyDeal = outerItem.userRequestInfo.userItemOptionsResponse.userDealTypes.find { it.dealType == "MONTHLY" }

        binding.textView38.text = "${charterDeal?.minPrice}~${charterDeal?.maxPrice}"
        binding.textView40.text = "${tradingDeal?.minPrice}~${tradingDeal?.maxPrice}"

        val minMonthlyPrice = monthlyDeal?.minMonthPrice
        val maxMonthlyPrice = monthlyDeal?.maxMonthPrice

        binding.textView36.text = "${minMonthlyPrice}~${maxMonthlyPrice}"


        val dealTypes2 =
            outerItem.userRequestInfo.userItemOptionsResponse.userDealTypes.joinToString(" / ") { translateToKorean(it.dealType) }
        binding.subtext2.text = dealTypes2

        // 사용자의 층 정보 설정
        val floors: String = outerItem.userRequestInfo.userItemOptionsResponse.userFloors.joinToString(" / ") { translateToKorean(it.floor) }

        binding.textView44.text = floors
        binding.itemnotshow1.visibility = if (floors.isNotEmpty()) View.VISIBLE else View.GONE

        // 사용자의 관리 옵션 정보 설정
        val managementOptions =
            outerItem.userRequestInfo.userItemOptionsResponse.userManagementOptions.joinToString(" / ") { translateToKorean(it.managementOption) }
        binding.textView47.text = managementOptions
        binding.itemnotshow2.visibility =
            if (managementOptions.isNotEmpty()) View.VISIBLE else View.GONE

        // 사용자의 내부 시설 정보 설정
        val internalFacilities =
            outerItem.userRequestInfo.userItemOptionsResponse.userInternalFacilities.joinToString(" / ") { translateToKorean(it.internalFacility) }
        binding.textView49.text = internalFacilities
        binding.itemnotshow3.visibility =
            if (internalFacilities.isNotEmpty()) View.VISIBLE else View.GONE

        // 사용자의 승인 날짜 설정
        binding.textView51.text = translateToKorean(outerItem.userRequestInfo.userItemOptionsResponse.approveDate)
        binding.itemnotshow4.visibility =
            if (!outerItem.userRequestInfo.userItemOptionsResponse.approveDate.isNullOrBlank()) View.VISIBLE else View.GONE

        // 사용자의 추가 필터 정보 설정
        val extraFilters =
            outerItem.userRequestInfo.userItemOptionsResponse.userExtraFilters.joinToString(" / ") { translateToKorean(it.extraFilter) }
        binding.textView55.text = extraFilters
        binding.itemnotshow5.visibility =
            if (extraFilters.isNotEmpty()) View.VISIBLE else View.GONE
        setContentView(binding.root)
    }

    private fun setupRecyclerView(outerItem: List<RetrofitClient2.MatchedBrokerItemResponse>,useritemId: Int) {
        // RecyclerView의 레이아웃 매니저 설정
        binding.optionRv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.optionRv2.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        adapter = OuterSecondoptionAdapter(outerItem,useritemId)
//
//        adapter = OuterSecondoptionAdapter(outerItem) { selectedItems ->
//            Log.d("ThirdoptionAdapter2", "${selectedItems}")
//            // ThirdoptionAdapter에 아이템 추가
//
//            // ThirdoptionAdapter에 아이템 추가
//            thirdAdapter.addItems(selectedItems.toMutableList())
//            Log.d("ThirdoptionAdapter3", "${thirdAdapter.itemCount}")
//            binding.optionRv2.adapter = thirdAdapter
//
//            // 데이터가 변경될 때마다 RecyclerView에 알리기
//            thirdAdapter.notifyDataSetChanged()
//        }

        binding.optionRv.adapter = adapter
    }
    fun setupRecyclerView2(useritemId: Int) {
        // RecyclerView의 레이아웃 매니저 설정
        binding.optionRv2.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        val call = RetrofitObject.getRetrofitService.usermatchitem("Bearer $token", "MATCH_LIKE")
        call.enqueue(object : Callback<RetrofitClient2.ResponseUserMatchitem> {
            override fun onResponse(
                call: Call<RetrofitClient2.ResponseUserMatchitem>,
                response: Response<RetrofitClient2.ResponseUserMatchitem>
            ) {
                Log.d("Retrofit81", response.toString())
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    Log.d("Retrofit8", responseBody.toString())
                    if (responseBody != null && responseBody.isSuccess) {
                        val filteredData = responseBody.data.filter { it.userRequestInfo.userItemId == useritemId }
                        if (filteredData.isNotEmpty()) {
                            val matchedBrokerItemResponses = filteredData.flatMap { it.matchedBrokerItemResponses }
                            Log.d("Retrofit82", matchedBrokerItemResponses.toString())
                            thirdAdapter.setItems(matchedBrokerItemResponses)
                            // RecyclerView 어댑터 설정
                            binding.optionRv2.adapter = thirdAdapter
                            if (matchedBrokerItemResponses.isNotEmpty()) {
                                val matchingIds = matchedBrokerItemResponses.map { it.matchingId }
                                matchingID = matchingIds
                            }
                        } else {
                            // 필터링된 데이터가 없는 경우의 처리
                        }
                    } else {
                        // 요청이 실패했을 때의 처리
                    }
                }
            }
            override fun onFailure(call: Call<RetrofitClient2.ResponseUserMatchitem>, t: Throwable) {
                val errorMessage = "Call Failed: ${t.message}"
                Log.d("Retrofit82", errorMessage)
            }
        })

        thirdAdapter.setOnItemCountChangeListener(object : ThirdoptionAdapter.OnItemCountChangeListener {
            override fun onItemCountChanged(itemCount: Int) {
                Log.d("MyApp22", "$itemCount")
                binding.textView23.text = "${itemCount}개"
                binding.texttitle2.text = "${itemCount}개"

                if (itemCount >= 1) {
                    binding.imageView17.setImageResource(R.drawable.btn_add__1_)
                    binding.imageshow3.setImageResource(R.drawable.btn_add__1_)
                } else {
                    binding.imageView17.setImageResource(R.drawable.btn_add_gray)
                    binding.imageshow3.setImageResource(R.drawable.btn_add_gray)
                }
            }
        })
//        binding.optionRv2.adapter = thirdAdapter
    }

//    data class OuterItem2(val title: String, val innerItemList: List<String>? = null) {
//        fun getItemCount(): Int {
//            return innerItemList?.size ?: 0
//        }
//    }
    private fun showCustomDialog() {
        // 다이얼로그 레이아웃을 inflate
        val dialogView = layoutInflater.inflate(R.layout.option_thirddialogview, null)

        // AlertDialog.Builder를 사용하여 다이얼로그 생성
        val builder = AlertDialog.Builder(this)
        builder.setView(dialogView)
        // AlertDialog 생성
        val alertDialog: AlertDialog = builder.create()

        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        // Window 속성을 사용하여 크기 조절
        val layoutParams = WindowManager.LayoutParams()
        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT // 원하는 폭으로 설정
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT // 원하는 높이로 설정
        alertDialog.window?.attributes = layoutParams

        // 다이얼로그 내부의 ImageButton 참조
        val cancelButton = dialogView.findViewById<ImageButton>(R.id.imageButton8)
        val confirmButton = dialogView.findViewById<ImageButton>(R.id.imageButton9)

        // 취소 버튼 클릭 리스너 설정
        cancelButton.setOnClickListener {
            // 취소 버튼을 눌렀을 때 수행할 동작
            alertDialog.dismiss() // 다이얼로그 닫기
            // 추가적인 작업 수행 가능
        }

        // 확인 버튼 클릭 리스너 설정
        confirmButton.setOnClickListener {
            Log.d("Retrofit88", matchingID.toString())
            val request = RetrofitClient2.RequestMatchbroker2(matchingID,"MATCH_COMPLETE")
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
                            setupRecyclerView2(userItemID)
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
            finish()
            alertDialog.dismiss() // 다이얼로그 닫기
            // 추가적인 작업 수행 가능
        }

        // AlertDialog 표시
        alertDialog.show()
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

    fun translateToKorean(keyword: String?): String {
        return when (keyword) {
            "ONE_ROOM" -> "원룸"
            "TWO_OR_THREEROOM" -> "투룸/쓰리룸"
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