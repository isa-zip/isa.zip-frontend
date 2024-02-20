package com.example.zipfront

import android.app.Activity
import android.app.TaskStackBuilder
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.zipfront.connection.RetrofitClient2
import com.example.zipfront.databinding.ActivityAdditional3Binding
import com.example.zipfront.databinding.MatchingOptionBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AdditonalActivity3: AppCompatActivity() {
    lateinit var binding: ActivityAdditional3Binding

    val checkArray = BooleanArray(35)
    // 각 버튼에 대한 원래 값 저장 배열
    var originalWidthArray = IntArray(35)
    var originalBackgroundArray = arrayOfNulls<Drawable>(35)
    var originalTextColorArray = IntArray(35)

    val checkArray2 = BooleanArray(3)
    // 각 버튼에 대한 원래 값 저장 배열
    var originalWidthArray2 = IntArray(3)
    var originalBackgroundArray2 = arrayOfNulls<Drawable>(3)
    var originalTextColorArray2 = IntArray(3)

    private var brokerId = 0


    //현재 값이 저장되어 있는지
    var isChoose = false
    private val user = MyApplication.getUser()
    private val token = user.getString("jwt", "").toString()

    private var charterInfo2: RetrofitClient2.CharterInfo2? = null
    private var tradingInfo2: RetrofitClient2.TradingInfo2? = null
    private var monthlyInfo2: RetrofitClient2.MonthlyInfo2? = null

    private var roomTypeList: RetrofitClient2.RoomType? = null
    private val dealTypesList: MutableList<RetrofitClient2.DealType> = mutableListOf()
    private var roomSizeList: String? = null
    private val floorList: MutableList<RetrofitClient2.Floor> = mutableListOf()
    private val managementOptionList: MutableList<RetrofitClient2.ManagementOption> = mutableListOf()
    private var managementPrice: String? = null
    private val internalFacilityList: MutableList<RetrofitClient2.InternalFacility> = mutableListOf()
    private var approveDate: String? = null
    private val extraFilterList: MutableList<RetrofitClient2.ExtraFilter> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdditional3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        brokerId = intent.getIntExtra("brokerId", 0)
        Log.d("yes", brokerId.toString())

        val selectedItem = intent.getStringExtra("selectedItem")
        val parts = selectedItem?.split(" ") // 공백을 기준으로 분할
        val result = if (parts != null && parts.size >= 4) { // 최소한 4개의 부분으로 나누어진다고 가정
            "${parts[2]} ${parts[3]}" // [2][3]번째 부분을 추출하여 조합
        } else {
            "" // 예외 처리: 부족한 부분이 있거나 null인 경우 빈 문자열 반환
        }
        Log.d("Retrofit8322", result.toString())
        val shortIntroduction = intent.getStringExtra("shortIntroduction")
        val specificIntroduction = intent.getStringExtra("specificIntroduction")

        binding.constraintLayout3.visibility = View.GONE
        binding.constraintLayout4.visibility = View.GONE
        binding.constraintLayout5.visibility = View.GONE

        val tradeButtonExArray = arrayOf(binding.textView16,binding.editText2,binding.editText4,binding.tradeButtonEx1, binding.tradeButtonEx2, binding.tradeButtonEx3, binding.tradeButtonEx4)
        val imageViewArray = arrayOf(binding.imageView27,binding.imageView28,binding.imageView29,binding.imageView30, binding.imageView31, binding.imageView32, binding.imageView33)


        for (imageView in imageViewArray) {
            imageView.visibility = View.GONE
        }

        for (i in tradeButtonExArray.indices) {
            tradeButtonExArray[i].addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    updateImageViewVisibility(s, imageViewArray[i])
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            })
        }

        binding.imageButton7.setOnClickListener{
            // EditText로부터 사용자가 입력한 값을 가져옵니다.
            val minPriceText = binding.textView16.text.toString()

            val minPrice: String? = if (minPriceText.isNotEmpty()) minPriceText else null

            Log.d("Retrofit832", minPrice.toString())
            charterInfo2 = RetrofitClient2.CharterInfo2(minPrice,null,null)
            Log.d("Retrofit8321", charterInfo2.toString())

            // EditText로부터 사용자가 입력한 값을 가져옵니다.
            val minPriceText2 = binding.editText2.text.toString()

            val minPrice2: String? = if (minPriceText2.isNotEmpty()) minPriceText2 else null

            Log.d("Retrofit822", minPrice2.toString())
            tradingInfo2 = RetrofitClient2.TradingInfo2(null, minPrice2, null)
            Log.d("Retrofit8221", tradingInfo2.toString())

            // EditText로부터 사용자가 입력한 값을 가져옵니다.
            val minPriceText3 = binding.editText4.text.toString()

            val minPrice3: String? = if (minPriceText3.isNotEmpty()) minPriceText3 else null

            Log.d("Retrofit823", minPrice3.toString())
            monthlyInfo2 = RetrofitClient2.MonthlyInfo2(null,null,minPrice3)
            Log.d("Retrofit8231", monthlyInfo2.toString())

            val dong = result ?: ""

            val dealInfoMap = RetrofitClient2.DealInfoMap2(charterInfo2, tradingInfo2, monthlyInfo2)

            roomSizeList=binding.tradeButtonEx2.text.toString()
            managementPrice=binding.tradeButtonEx3.text.toString()
            approveDate=binding.tradeButtonEx4.text.toString()

            val detailsRequest = RetrofitClient2.Introduction(
                shortIntroduction = shortIntroduction,
                specificIntroduction = specificIntroduction
            )

            val request = RetrofitClient2.RequestUseritem2(
                roomType = roomTypeList?.toString(),
                dealTypes = dealTypesList,
                dealInfoMap = dealInfoMap,
                roomSize = roomSizeList,
                selectedFloor = floorList,
                managementOptions = managementOptionList,
                managementPrice = managementPrice,
                internalFacilities = internalFacilityList,
                approveDate = approveDate,
                extraFilters = extraFilterList
            )
            Log.d("Retrofit811", request.toString())


            if (brokerId == 0 ) {
                val call = RetrofitObject.getRetrofitService.NewItem(
                    "Bearer $token",
                    dong,
                    detailsRequest,
                    request,
                    null
                )
                call.enqueue(object : Callback<RetrofitClient2.ResponseMatchbrokeritemadd> {
                    override fun onResponse(
                        call: Call<RetrofitClient2.ResponseMatchbrokeritemadd>,
                        response: Response<RetrofitClient2.ResponseMatchbrokeritemadd>
                    ) {
                        Log.d("Retrofit81", response.toString())
                        if (response.isSuccessful) {
                            val responseBody = response.body()
                            Log.d("Retrofit8", responseBody.toString())
                            if (responseBody != null && responseBody.isSuccess) {
                                val intent =
                                    Intent(this@AdditonalActivity3, MainActivity::class.java)
                                val stackBuilder = TaskStackBuilder.create(this@AdditonalActivity3)
                                stackBuilder.addNextIntentWithParentStack(intent)
                                stackBuilder.startActivities()
                            } else {
                                // 요청이 실패했을 때의 처리
                            }
                        }
                    }

                    override fun onFailure(
                        call: Call<RetrofitClient2.ResponseMatchbrokeritemadd>,
                        t: Throwable
                    ) {
                        val errorMessage = "Call Failed: ${t.message}"
                        Log.d("Retrofit82", errorMessage)
                    }
                })
            } else {
                val call = RetrofitObject.getRetrofitService.ModifyItem(
                    "Bearer $token",
                    brokerId,
                    dong,
                    detailsRequest,
                    request,
                    null
                )
                call.enqueue(object : Callback<RetrofitClient2.ResponseMatchbrokeritemadd> {
                    override fun onResponse(
                        call: Call<RetrofitClient2.ResponseMatchbrokeritemadd>,
                        response: Response<RetrofitClient2.ResponseMatchbrokeritemadd>
                    ) {
                        Log.d("Retrofit81", response.toString())
                        if (response.isSuccessful) {
                            val responseBody = response.body()
                            Log.d("Retrofit8", responseBody.toString())
                            if (responseBody != null && responseBody.isSuccess) {
                                val intent =
                                    Intent(this@AdditonalActivity3, MainActivity::class.java)
                                val stackBuilder = TaskStackBuilder.create(this@AdditonalActivity3)
                                stackBuilder.addNextIntentWithParentStack(intent)
                                stackBuilder.startActivities()
                            } else {
                                // 요청이 실패했을 때의 처리
                            }
                        }
                    }

                    override fun onFailure(
                        call: Call<RetrofitClient2.ResponseMatchbrokeritemadd>,
                        t: Throwable
                    ) {
                        val errorMessage = "Call Failed: ${t.message}"
                        Log.d("Retrofit82", errorMessage)
                    }
                })
            }
        }

        binding.imageView10.setOnClickListener{
            finish()
        }
        val clickedColor = ContextCompat.getColor(this, R.color.zipblue01)


        // 각 버튼에 대한 원래 값 추출
        for (i in 0 until 35) {
            val buttonId = resources.getIdentifier("additionalButton${i + 1}", "id", packageName)
            val button = findViewById<Button>(buttonId)

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
                } else {
                    binding.imageButton7.setImageResource(R.drawable.btn__apply_disabled)
                }
            }
        }

        // 각 버튼에 대한 원래 값 추출
        for (i in 0 until 3) {
            val buttonId = resources.getIdentifier("tradeButton${i + 1}", "id", packageName)
            val button = findViewById<Button>(buttonId)

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

        val imageView10: ImageView = findViewById(R.id.imageView10)
        imageView10.setOnClickListener {
            finish()
        }

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

            when (index) {
                in 0..3 -> when (index) {
                    0 -> roomTypeList = RetrofitClient2.RoomType.ONE_ROOM
                    1 -> roomTypeList = RetrofitClient2.RoomType.TWO_OR_THREEROOM
                    2 -> roomTypeList = RetrofitClient2.RoomType.OFFICETELS
                    else -> roomTypeList = RetrofitClient2.RoomType.APARTMENT
                }

                in 5..13 -> floorList += when (index) {
                    5 -> RetrofitClient2.Floor.ONE
                    6 -> RetrofitClient2.Floor.TWO
                    7 -> RetrofitClient2.Floor.THREE
                    8 -> RetrofitClient2.Floor.FOUR
                    9 -> RetrofitClient2.Floor.FIVE
                    10 -> RetrofitClient2.Floor.SIX
                    11 -> RetrofitClient2.Floor.OVER_SEVEN
                    12-> RetrofitClient2.Floor.SEMI_LAYER
                    else -> RetrofitClient2.Floor.ROOFTOP
                }

                in 14..18 -> managementOptionList += when (index) {
                    14 -> RetrofitClient2.ManagementOption.ELECTRONIC_FEE
                    15 -> RetrofitClient2.ManagementOption.GAS_FEE
                    16 -> RetrofitClient2.ManagementOption.INTERNET_FEE
                    17 -> RetrofitClient2.ManagementOption.PARKING_FEE
                    else -> RetrofitClient2.ManagementOption.WATER_FEE
                }

                in 19..26 -> internalFacilityList += when (index) {
                    19 -> RetrofitClient2.InternalFacility.AIR_CONDITIONER
                    20 -> RetrofitClient2.InternalFacility.REFRIGERATOR
                    21 -> RetrofitClient2.InternalFacility.WASHING_MACHINE
                    22 -> RetrofitClient2.InternalFacility.MICROWAVE
                    23 -> RetrofitClient2.InternalFacility.CLOSET
                    24 -> RetrofitClient2.InternalFacility.TABLE
                    25 -> RetrofitClient2.InternalFacility.TV
                    else -> RetrofitClient2.InternalFacility.BED
                }

                in 27..34 -> extraFilterList += when (index) {
                    27 -> RetrofitClient2.ExtraFilter.PARKING
                    28 -> RetrofitClient2.ExtraFilter.SHORT_LOAN
                    29 -> RetrofitClient2.ExtraFilter.FULL_OPTION
                    30 -> RetrofitClient2.ExtraFilter.ELEVATOR
                    31 -> RetrofitClient2.ExtraFilter.VERANDA
                    32 -> RetrofitClient2.ExtraFilter.SECURITY
                    43 -> RetrofitClient2.ExtraFilter.VR
                    else -> RetrofitClient2.ExtraFilter.NON_FACE_CONTRACT
                }

                else -> { /* 처리할 로직이 없는 경우 */
                }
            }
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
    private fun updateImageViewVisibility(s: Editable?, imageView: ImageView) {
        if (s.isNullOrEmpty()) {
            imageView.visibility = View.GONE
        } else {
            imageView.visibility = View.VISIBLE
        }
    }

}