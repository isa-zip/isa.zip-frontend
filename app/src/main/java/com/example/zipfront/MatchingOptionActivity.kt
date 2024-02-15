package com.example.zipfront

import android.app.Activity
import android.app.TaskStackBuilder
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.RadioGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
import androidx.core.content.ContextCompat
import com.example.zipfront.connection.RetrofitClient2
import com.example.zipfront.databinding.MatchingOptionBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MatchingOptionActivity: AppCompatActivity() {
    lateinit var binding: MatchingOptionBinding
    val checkArray = BooleanArray(48)
    // 각 버튼에 대한 원래 값 저장 배열
    var originalWidthArray = IntArray(48)
    var originalBackgroundArray = arrayOfNulls<Drawable>(48)
    var originalTextColorArray = IntArray(48)

    val checkArray2 = BooleanArray(3)
    // 각 버튼에 대한 원래 값 저장 배열
    var originalWidthArray2 = IntArray(3)
    var originalBackgroundArray2 = arrayOfNulls<Drawable>(3)
    var originalTextColorArray2 = IntArray(3)

    //현재 값이 저장되어 있는지
    var isChoose = false
    private val user = MyApplication.getUser()
    private val token = user.getString("jwt", "").toString()

    private var charterInfo: RetrofitClient2.CharterInfo? = null
    private var tradingInfo: RetrofitClient2.TradingInfo? = null
    private var monthlyInfo: RetrofitClient2.MonthlyInfo? = null
    private val roomTypeList: MutableList<RetrofitClient2.RoomType> = mutableListOf()
    private val dealTypesList: MutableList<RetrofitClient2.DealType> = mutableListOf()
    private val roomSizeList: MutableList<RetrofitClient2.RoomSize> = mutableListOf()
    private val floorList: MutableList<RetrofitClient2.Floor> = mutableListOf()
    private val managementOptionList: MutableList<RetrofitClient2.ManagementOption> = mutableListOf()
    private val internalFacilityList: MutableList<RetrofitClient2.InternalFacility> = mutableListOf()
    private var approveDate: RetrofitClient2.ApproveDate? = null
    private val extraFilterList: MutableList<RetrofitClient2.ExtraFilter> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MatchingOptionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.constraintLayout3.visibility = View.GONE
        binding.constraintLayout4.visibility = View.GONE
        binding.constraintLayout5.visibility = View.GONE

        binding.imageButton7.setOnClickListener{

            val dong = "목동"
            val dealInfoMap = RetrofitClient2.DealInfoMap(charterInfo, tradingInfo, monthlyInfo)

            val request = RetrofitClient2.RequestUseritem(
                roomType = roomTypeList,
                dealTypes = dealTypesList,
                dealInfoMap = dealInfoMap,
                roomSize = roomSizeList,
                floor = floorList,
                managementOption = managementOptionList,
                internalFacility = internalFacilityList,
                approveDate = approveDate,
                extraFilter = extraFilterList
            )
            Log.d("Retrofit811", request.toString())

            val call = RetrofitObject.getRetrofitService.useritem("Bearer $token", dong, request)
            call.enqueue(object : Callback<RetrofitClient2.ResponseUseritem> {
                override fun onResponse(
                    call: Call<RetrofitClient2.ResponseUseritem>,
                    response: Response<RetrofitClient2.ResponseUseritem>
                ) {
                    Log.d("Retrofit81", response.toString())
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        Log.d("Retrofit8", responseBody.toString())
                        if (responseBody != null && responseBody.isSuccess) {
                            val intent = Intent(this@MatchingOptionActivity, MatchingActivity::class.java)
                            intent.putExtra("EXTRA_CUSTOM_DATA", "Hello from MatchingOptionActivity!")
                            val stackBuilder = TaskStackBuilder.create(this@MatchingOptionActivity)
                            stackBuilder.addNextIntentWithParentStack(intent)
                            stackBuilder.startActivities()
                        } else {
                            // 요청이 실패했을 때의 처리
                        }
                    }
                }
                override fun onFailure(call: Call<RetrofitClient2.ResponseUseritem>, t: Throwable) {
                    val errorMessage = "Call Failed: ${t.message}"
                    Log.d("Retrofit82", errorMessage)
                }
            })
        }

        binding.imageView10.setOnClickListener{
            if (isChoose) {
                showCustomDialog()
            }
            else {
                finish()
            }

        }
        val clickedColor = ContextCompat.getColor(this, R.color.zipblue01)

//        // switch2의 체크 상태에 따라 sizeofroom과 sizeofroom2의 레이아웃을 조절
//        binding.switch2.setOnCheckedChangeListener { _, isChecked ->
//            if (isChecked) {
//                // switch2가 체크된 경우 sizeofroom2를 보이게 함
//                binding.sizeofroom2.visibility = View.VISIBLE
//                binding.sizeofroom.visibility = View.GONE
//            } else {
//                // switch2가 체크되지 않은 경우 sizeofroom을 보이게 함
//                binding.sizeofroom.visibility = View.VISIBLE
//                binding.sizeofroom2.visibility = View.GONE
//            }
//        }

        // 각 버튼에 대한 원래 값 추출
        for (i in 0 until 48) {
            val buttonId = resources.getIdentifier("myButton${i + 1}", "id", packageName)
            val button = findViewById<Button>(buttonId)

//            // RoomType 할당
//            val roomType = if (i in 0..3) {
//                when (i) {
//                    0 -> RetrofitClient2.RoomType.ONE_ROOM
//                    1 -> RetrofitClient2.RoomType.TWO_OR_THREE_ROOM
//                    2 -> RetrofitClient2.RoomType.OFFICETELS
//                    else -> RetrofitClient2.RoomType.APARTMENT
//                }
//            }
//            else {
//                null // 예시로 null을 반환하도록 했습니다.
//            }
//
//            // RoomSize 할당
//            val roomSize = when {
//                i == 42 -> RetrofitClient2.RoomSize.UNDER_17
//                i == 43 -> RetrofitClient2.RoomSize.UNDER_66
//                i == 44 -> RetrofitClient2.RoomSize.UNDER_99
//                i == 45 -> RetrofitClient2.RoomSize.UNDER_132
//                i == 46 -> RetrofitClient2.RoomSize.UNDER_165
//                i == 47 -> RetrofitClient2.RoomSize.UNDER_198
//                i == 11 -> RetrofitClient2.RoomSize.OVER_198
//                else -> null
//            }
//
//// Floor 할당
//            val floor = when {
//                i == 12-> RetrofitClient2.Floor.ONE
//                i == 13 -> RetrofitClient2.Floor.TWO
//                i == 14-> RetrofitClient2.Floor.THREE
//                i == 15 -> RetrofitClient2.Floor.FOUR
//                i == 16 -> RetrofitClient2.Floor.FIVE
//                i == 17 -> RetrofitClient2.Floor.SIX
//                i == 18 -> RetrofitClient2.Floor.OVER_SEVEN
//                i == 19 -> RetrofitClient2.Floor.SEMI_LAYER
//                i == 20 -> RetrofitClient2.Floor.ROOFTOP
//                else -> null
//            }
//
//// ManagementOption 할당
//            val managementOption = when {
//                i == 21 -> RetrofitClient2.ManagementOption.ELECTRONIC_FEE
//                i == 22 -> RetrofitClient2.ManagementOption.GAS_FEE
//                i == 23 -> RetrofitClient2.ManagementOption.INTERNET_FEE
//                i == 24 -> RetrofitClient2.ManagementOption.PARKING_FEE
//                i == 25 -> RetrofitClient2.ManagementOption.WATER_FEE
//                else -> null
//            }
//
//// InternalFacility 할당
//            val internalFacility = when {
//                i == 26 -> RetrofitClient2.InternalFacility.AIR_CONDITIONER
//                i == 27 -> RetrofitClient2.InternalFacility.REFRIGERATOR
//                i == 28 -> RetrofitClient2.InternalFacility.WASHING_MACHINE
//                i == 29 -> RetrofitClient2.InternalFacility.MICROWAVE
//                i == 30 -> RetrofitClient2.InternalFacility.CLOSET
//                i == 31 -> RetrofitClient2.InternalFacility.TABLE
//                i == 32 -> RetrofitClient2.InternalFacility.TV
//                i == 33 -> RetrofitClient2.InternalFacility.BED
//                else -> null
//            }
//
//
//            val extraFilter = when {
//                i == 34 -> RetrofitClient2.ExtraFilter.PARKING
//                i == 35 -> RetrofitClient2.ExtraFilter.SHORT_LOAN
//                i == 36 -> RetrofitClient2.ExtraFilter.FULL_OPTION
//                i == 37 -> RetrofitClient2.ExtraFilter.ELEVATOR
//                i == 38 -> RetrofitClient2.ExtraFilter.VERANDA
//                i == 39 -> RetrofitClient2.ExtraFilter.SECURITY
//                i == 40 -> RetrofitClient2.ExtraFilter.VR
//                i == 41 -> RetrofitClient2.ExtraFilter.NON_FACE_CONTRACT
//                else -> null
//            }

            originalWidthArray[i] = button.layoutParams.width
            originalBackgroundArray[i] = button.background
            originalTextColorArray[i] = button.currentTextColor
            checkArray[i] = false // 초기값은 모두 false로 설정

            button.setOnClickListener {

                toggleButtonClickState(
                    checkArray[i],
                    originalWidthArray[i],
                    originalBackgroundArray[i],
                    originalTextColorArray[i],
                    clickedColor,
                    button,
                    i
                )
                // 하나라도 버튼이 눌리면 imageButton7의 이미지 변경
                if (checkArray.any { it }) {
                    binding.imageButton7.setImageResource(R.drawable.btn__apply_active)
                    isChoose = true
                } else {
                    binding.imageButton7.setImageResource(R.drawable.btn__apply_disabled)
                    isChoose = false
                }
            }
        }

        // 각 버튼에 대한 원래 값 추출
        for (i in 0 until 3) {
            val buttonId = resources.getIdentifier("tradeButton${i + 1}", "id", packageName)
            val button = findViewById<Button>(buttonId)

            val extraFilter = when {
                i == 0 -> RetrofitClient2.DealType.CHARTER
                i == 1 -> RetrofitClient2.DealType.TRADING
                i == 2 -> RetrofitClient2.DealType.MONTHLY
                else -> null
            }


            originalWidthArray2[i] = button.layoutParams.width
            originalBackgroundArray2[i] = button.background
            originalTextColorArray2[i] = button.currentTextColor
            checkArray2[i] = false // 초기값은 모두 false로 설정

            button.setOnClickListener {
                toggleButtonClickState2(
                    checkArray2[i],
                    originalWidthArray2[i],
                    originalBackgroundArray2[i],
                    originalTextColorArray2[i],
                    clickedColor,
                    button,
                    i
                )
                toggleConstraintLayoutVisibility2(i)
            }
        }

        val radioGroup = findViewById<RadioGroup>(R.id.radio_graphics)


        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            val selectedApproveDate = when (checkedId) {
                R.id.radioButton_total -> RetrofitClient2.ApproveDate.ALL
                R.id.radioButton_1 -> RetrofitClient2.ApproveDate.UNDER_ONE_YEAR
                R.id.radioButton_2 -> RetrofitClient2.ApproveDate.UNDER_FIVE_YEARS
                R.id.radioButton_3 -> RetrofitClient2.ApproveDate.UNDER_TEN_YEARS
                R.id.radioButton_4 -> RetrofitClient2.ApproveDate.UNDER_FIFTEEN_YEARS
                R.id.radioButton_5 -> RetrofitClient2.ApproveDate.OVER_FIFTEEN_YEARS
                else -> null // 예외 처리
            }

            // 선택된 값에 따른 처리
            selectedApproveDate?.let {
                approveDate = it
                Log.d("Retrofit81", approveDate.toString())
            }
        }

        // EditText로부터 사용자가 입력한 값을 가져옵니다.
        val minPriceText = findViewById<EditText>(R.id.textView16).text.toString()
        val maxPriceText = findViewById<EditText>(R.id.textView17).text.toString()

        val minPrice = if (minPriceText.isNotEmpty()) minPriceText.toIntOrNull() else null
        val maxPrice = if (maxPriceText.isNotEmpty()) maxPriceText.toIntOrNull() else null

        Log.d("Retrofit82", minPrice.toString() + maxPrice.toString())
        charterInfo = RetrofitClient2.CharterInfo(minPrice, maxPrice)

        // EditText로부터 사용자가 입력한 값을 가져옵니다.
        val minPriceText2 = findViewById<EditText>(R.id.editText2).text.toString()
        val maxPriceText2 = findViewById<EditText>(R.id.editText3).text.toString()

        val minPrice2 = if (minPriceText2.isNotEmpty()) minPriceText2.toIntOrNull() else null
        val maxPrice2 = if (maxPriceText2.isNotEmpty()) maxPriceText2.toIntOrNull() else null

        Log.d("Retrofit822", minPrice2.toString() + maxPrice2.toString())
        tradingInfo = RetrofitClient2.TradingInfo(minPrice2, maxPrice2)

        // EditText로부터 사용자가 입력한 값을 가져옵니다.
        val minPriceText3 = findViewById<EditText>(R.id.editText4).text.toString()
        val maxPriceText3 = findViewById<EditText>(R.id.editText5).text.toString()

        val minPrice3 = if (minPriceText3.isNotEmpty()) minPriceText2.toIntOrNull() else null
        val maxPrice3 = if (maxPriceText3.isNotEmpty()) maxPriceText2.toIntOrNull() else null

        Log.d("Retrofit823", minPrice3.toString() + maxPrice3.toString())
        monthlyInfo = RetrofitClient2.MonthlyInfo(minPrice3, maxPrice3)
    }

    private fun toggleConstraintLayoutVisibility2(index: Int) {
        when (index) {
            0 -> {
                binding.constraintLayout3.visibility = if (binding.constraintLayout3.visibility == View.VISIBLE) View.GONE else View.VISIBLE
            }
            1 -> {
                binding.constraintLayout4.visibility = if (binding.constraintLayout4.visibility == View.VISIBLE) View.GONE else View.VISIBLE
            }
            2 -> {
                binding.constraintLayout5.visibility = if (binding.constraintLayout5.visibility == View.VISIBLE) View.GONE else View.VISIBLE
            }
            else -> {
            }
        }
    }

    private fun toggleButtonClickState(
        isChecked: Boolean,
        originalWidth: Int,
        originalBackground: Drawable?,
        originalTextColor: Int,
        clickedColor: Int,
        button: Button,
        index: Int
    ) {
        if (!isChecked) {
            // 클릭 시 아이콘 추가
            val icon = resources.getDrawable(R.drawable.plus_circle_blue)
            button.setCompoundDrawablesWithIntrinsicBounds(null, null, icon, null)

            // 클릭 시 버튼의 넓이를 기존 넓이에서 16 추가
            val newWidth = originalWidth + 16
            val layoutParams = button.layoutParams
            layoutParams.width = newWidth
            button.layoutParams = layoutParams

            // 클릭 시 버튼의 배경 변경
            button.setBackgroundResource(R.drawable.click_button_roundcircle)

            // 클릭 시 텍스트 색상 변경
            button.setTextColor(clickedColor)

//            roomTypeList = mutableListOf<RetrofitClient2.RoomType>()
//            roomSizeList = mutableListOf<RetrofitClient2.RoomSize>()
//            floorList = mutableListOf<RetrofitClient2.Floor>()
//            managementOptionList = mutableListOf<RetrofitClient2.ManagementOption>()
//            internalFacilityList = mutableListOf<RetrofitClient2.InternalFacility>()
//            extraFilterList = mutableListOf<RetrofitClient2.ExtraFilter>()

            when (index) {
                in 0..3 -> roomTypeList += when (index) {
                    0 -> RetrofitClient2.RoomType.ONE_ROOM
                    1 -> RetrofitClient2.RoomType.TWO_OR_THREE_ROOM
                    2 -> RetrofitClient2.RoomType.OFFICETELS
                    else -> RetrofitClient2.RoomType.APARTMENT
                }
                11 -> roomSizeList+=RetrofitClient2.RoomSize.OVER_198
                in 42..47 -> roomSizeList+= when (index) {
                    42 -> RetrofitClient2.RoomSize.UNDER_17
                    43 -> RetrofitClient2.RoomSize.UNDER_66
                    44 -> RetrofitClient2.RoomSize.UNDER_99
                    45 -> RetrofitClient2.RoomSize.UNDER_132
                    46 -> RetrofitClient2.RoomSize.UNDER_165
                    else -> RetrofitClient2.RoomSize.UNDER_198
                }
                in 12..16 -> floorList+= when (index) {
                    12 -> RetrofitClient2.Floor.ONE
                    13 -> RetrofitClient2.Floor.TWO
                    14 -> RetrofitClient2.Floor.THREE
                    15 -> RetrofitClient2.Floor.FOUR
                    else -> RetrofitClient2.Floor.FIVE
                }
                in 21..25 -> managementOptionList+= when (index) {
                    21 -> RetrofitClient2.ManagementOption.ELECTRONIC_FEE
                    22 -> RetrofitClient2.ManagementOption.GAS_FEE
                    23 -> RetrofitClient2.ManagementOption.INTERNET_FEE
                    24 -> RetrofitClient2.ManagementOption.PARKING_FEE
                    else -> RetrofitClient2.ManagementOption.WATER_FEE
                }
                in 26..33 -> internalFacilityList+= when (index) {
                    26 -> RetrofitClient2.InternalFacility.AIR_CONDITIONER
                    27 -> RetrofitClient2.InternalFacility.REFRIGERATOR
                    28 -> RetrofitClient2.InternalFacility.WASHING_MACHINE
                    29 -> RetrofitClient2.InternalFacility.MICROWAVE
                    30 -> RetrofitClient2.InternalFacility.CLOSET
                    31 -> RetrofitClient2.InternalFacility.TABLE
                    32 -> RetrofitClient2.InternalFacility.TV
                    else -> RetrofitClient2.InternalFacility.BED
                }
                in 34..41 -> extraFilterList+= when (index) {
                    34 -> RetrofitClient2.ExtraFilter.PARKING
                    35 -> RetrofitClient2.ExtraFilter.SHORT_LOAN
                    36 -> RetrofitClient2.ExtraFilter.FULL_OPTION
                    37 -> RetrofitClient2.ExtraFilter.ELEVATOR
                    38 -> RetrofitClient2.ExtraFilter.VERANDA
                    39 -> RetrofitClient2.ExtraFilter.SECURITY
                    40 -> RetrofitClient2.ExtraFilter.VR
                    else -> RetrofitClient2.ExtraFilter.NON_FACE_CONTRACT
                }
                else -> { /* 처리할 로직이 없는 경우 */ }
            }
            Log.d("Retrofit82", roomTypeList.toString())
        } else {
            // 클릭 시 원래의 상태로 복원
            button.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)

            val layoutParams = button.layoutParams
            layoutParams.width = originalWidth
            button.layoutParams = layoutParams

            button.background = originalBackground

            button.setTextColor(originalTextColor)
        }

        // 토글 상태 변경
        checkArray[index] = !isChecked
    }

    private fun toggleButtonClickState2(
        isChecked: Boolean,
        originalWidth: Int,
        originalBackground: Drawable?,
        originalTextColor: Int,
        clickedColor: Int,
        button: Button,
        index: Int
    ) {
        if (!isChecked) {
            // 클릭 시 아이콘 추가
            val icon = resources.getDrawable(R.drawable.plus_circle_blue)
            button.setCompoundDrawablesWithIntrinsicBounds(null, null, icon, null)

            when (index) {
                in 0..2 -> dealTypesList += when (index) {
                    0 -> RetrofitClient2.DealType.CHARTER
                    1 -> RetrofitClient2.DealType.TRADING
                    else -> RetrofitClient2.DealType.MONTHLY
                }
            }
            // 클릭 시 버튼의 넓이를 기존 넓이에서 16 추가
            val newWidth = originalWidth + 16
            val layoutParams = button.layoutParams
            layoutParams.width = newWidth
            button.layoutParams = layoutParams

            // 클릭 시 버튼의 배경 변경
            button.setBackgroundResource(R.drawable.click_button_roundcircle)

            // 클릭 시 텍스트 색상 변경
            button.setTextColor(clickedColor)
        } else {
            // 클릭 시 원래의 상태로 복원
            button.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)

            val layoutParams = button.layoutParams
            layoutParams.width = originalWidth
            button.layoutParams = layoutParams

            button.background = originalBackground

            button.setTextColor(originalTextColor)
        }

        // 토글 상태 변경
        checkArray2[index] = !isChecked
    }

    private fun showCustomDialog() {
        // 다이얼로그 레이아웃을 inflate
        val dialogView = layoutInflater.inflate(R.layout.option_dialogview, null)

        // AlertDialog.Builder를 사용하여 다이얼로그 생성
        val builder = AlertDialog.Builder(this)
        builder.setView(dialogView)
        // AlertDialog 생성
        val alertDialog: AlertDialog = builder.create()

        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        // Window 속성을 사용하여 크기 조절
        val params =  alertDialog.window?.attributes
        params?.width = 296
        params?.height = 149
        alertDialog.window?.attributes = params

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
            finish()
            alertDialog.dismiss() // 다이얼로그 닫기
            // 추가적인 작업 수행 가능
        }

        // AlertDialog 표시
        alertDialog.show()
    }
}