package com.example.zipfront

import android.app.Dialog
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Adapter
import android.widget.CalendarView
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.zipfront.connection.RetrofitClient2
import com.google.android.material.bottomsheet.BottomSheetDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class IsaScheduleActivity : AppCompatActivity() {
    private lateinit var calendarView: CalendarView
    private lateinit var roundtab_3_Button: ImageButton
    private lateinit var roundtab_2_Button: ImageButton
    private lateinit var roundtab_1_Button: ImageButton
    private lateinit var roundtab_2week_Button: ImageButton
    private lateinit var optionRecyclerView: RecyclerView
    private lateinit var roundtab_3MonthsAgo_Button: ImageButton
    private lateinit var Button: ImageButton
    private var isEditingClicked = false
    private var isEditingMode = false
    private var isRegistration: Boolean = false
    var scheduleItems = ArrayList<IsaScheduleItem>()
    private lateinit var adapter : IsaScheduleAdapter

    private var selectedRoundTab: ImageButton? = null

    private var user = MyApplication.getUser()
    private var token = user.getString("jwt", "").toString()


    //period, moveDate
    private lateinit var peroid : String
    private var moveDate = "2024.02.11"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_isa_schedule)

        // 리사이클러뷰 초기화
        optionRecyclerView = findViewById(R.id.option_rv)
        val layoutManager = LinearLayoutManager(this)
        optionRecyclerView.layoutManager = layoutManager

        // CalendarView 초기화
        calendarView = findViewById(R.id.calendarView)

        // ScheduleActivity에서 전달받은 선택된 날짜 가져오기
        val selectedDate = intent.getStringExtra("selectedDate")

        // 선택된 날짜가 null이 아니라면, CalendarView에 설정
        selectedDate?.let {
            val dateFormat = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
            val calendar = Calendar.getInstance()
            calendar.time = dateFormat.parse(selectedDate) ?: Date()
            val milliseconds = calendar.timeInMillis
            calendarView.date = milliseconds

            // moveDate 설정
            val moveDateFormat = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
            moveDate = moveDateFormat.format(calendar.time)
            Log.d("moveDate", moveDate)
        }

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            // 선택된 날짜를 Calendar 객체에 설정
            val calendar = Calendar.getInstance().apply {
                set(year, month, dayOfMonth)
            }

            // 선택된 날짜를 "yyyy.MM.dd" 형식의 문자열로 변환하여 moveDate에 할당
            val moveDateFormat = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
            moveDate = moveDateFormat.format(calendar.time)

            // moveDate 값 로그로 확인
            Log.d("moveDate", moveDate)
        }

        // 데이터 준비
        //var scheduleItems = ArrayList<IsaScheduleItem>()
        //scheduleItems.add(IsaScheduleItem("2023.11.7", "방 확정"))
        //scheduleItems.add(IsaScheduleItem("2023.11.7", "방 확정"))
        //scheduleItems.add(IsaScheduleItem("2023.11.7", "방 확정"))
        //scheduleItems.add(IsaScheduleItem("2023.11.7", "방 확정"))
        //scheduleItems.add(IsaScheduleItem("2023.11.7", "방 확정"))

        // 어댑터 설정
        adapter = IsaScheduleAdapter(scheduleItems, object : IsaScheduleAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                // The following variable is not used in the current implementation.
                val selectedItem = scheduleItems[position]

                // point_gray를 point_blue로 변경
                val pointImageView: ImageView = findViewById(R.id.point_gray)
                pointImageView.setImageResource(R.drawable.point_blue)

                // layout1의 배경을 list4에서 list3로 변경
                val layout1: ConstraintLayout = findViewById(R.id.layout1)
                layout1.setBackgroundResource(R.drawable.list3)
            }
        })
        //optionRecyclerView.adapter = adapter


        //이사일정 등록
        if (isRegistration) {
            deleteSchedule("ONE_MONTH")
        }

        peroid = "ONE_MONTH"
        registerSchedule(peroid, moveDate)

        //이사 일정 상세 조회
        evenschedulelookup()







        // 버튼 초기화
        roundtab_3_Button = findViewById(R.id.roundtab3)
        roundtab_2_Button = findViewById(R.id.roundtab2)
        roundtab_1_Button = findViewById(R.id.roundtab1)
        roundtab_2week_Button = findViewById(R.id.roundtab_2week)
        calendarView = findViewById(R.id.calendarView)

        Button =findViewById(R.id.imageButton5)

        Button.setOnClickListener {
            val intent = Intent(this, MatchingOptionActivity2::class.java)
            startActivity(intent)
        }
        // 3개월 전
        roundtab_3_Button.setOnClickListener {
            selectRoundTab(roundtab_3_Button)
            moveCalendar(-90)
            deleteSchedule("THREE_MONTHS")
            //peroid = "THREE_MONTHS"
            //registerSchedule(peroid, moveDate)
            //optionRecyclerView.adapter = null
            //evenschedulelookup()

        }
        // 2개월 전
        roundtab_2_Button.setOnClickListener {
            selectRoundTab(roundtab_2_Button)
            moveCalendar(-60)
            deleteSchedule("TWO_MONTHS")
            //peroid = "TWO_MONTHS"
            //registerSchedule(peroid, moveDate)
            //optionRecyclerView.adapter = null
            //evenschedulelookup()

        }
        // 1개월 전
        roundtab_1_Button.setOnClickListener {
            selectRoundTab(roundtab_1_Button)
            moveCalendar(-30)
            deleteSchedule("ONE_MONTH")
            //peroid = "ONE_MONTH"
            //registerSchedule(peroid, moveDate)
            //optionRecyclerView.adapter = null
            //evenschedulelookup()
        }
        // 2주 전
        roundtab_2week_Button.setOnClickListener {
            selectRoundTab(roundtab_2week_Button)
            moveCalendar(-14)
            deleteSchedule("TWO_WEEKS")
            //peroid = "TWO_WEEKS"
            //registerSchedule(peroid, moveDate)
            //optionRecyclerView.adapter = null
            //evenschedulelookup()
        }

        // 년도 텍스트뷰 클릭 시 bottom sheet 표시
        val yearTextView: TextView = findViewById(R.id.year_text)
        yearTextView.setOnClickListener {
            showBottomSheetCalendar()
        }

        val imageView10: ImageView = findViewById(R.id.imageView10)
        imageView10.setOnClickListener {
            finish()
        }

        // 편집 누르면 완료로 뜸
        val editingTextView: TextView = findViewById(R.id.editing)
        val editingFinishTextView: TextView = findViewById(R.id.editing_finish)

        // 편집 누르면 매물 옵션 선택하기 버튼 사라짐
        val optionBackgroundConstraintLayout: ConstraintLayout = findViewById(R.id.frame_10000_background)
        val optionChoiceTextView: ImageButton = findViewById(R.id.imageButton5)

        // "편집" 텍스트뷰가 클릭된 상태를 저장하는 변수
        var isEditingClicked = false

        editingTextView.setOnClickListener {
            // 편집 텍스트 버튼이 클릭되었을 때, edit_circle 버튼을 모든 아이템에 보이도록 설정
            adapter.setEditingClicked(true)
            adapter.setEditingMode(true) // 편집 모드를 설정합니다.

            // 나머지 코드는 현재와 동일합니다.
            val adapter = optionRecyclerView.adapter as IsaScheduleAdapter

            // 모든 아이템에 대해 순회하면서 edit_circle ImageButton의 visibility를 VISIBLE로 변경
            for (i in 0 until adapter.itemCount) {
                val viewHolder = optionRecyclerView.findViewHolderForAdapterPosition(i)
                viewHolder?.itemView?.findViewById<ImageButton>(R.id.edit_circle)?.visibility = View.VISIBLE
            }

            // layout1의 배경을 list3에서 btn_agent3로 변경
            val layout1: ConstraintLayout = findViewById(R.id.layout1)
            layout1.setBackgroundResource(R.drawable.btn_agent3)

            // 편집 텍스트 버튼의 가시성을 변경합니다.
            editingTextView.visibility = View.GONE
            editingFinishTextView.visibility = View.VISIBLE

            // 버튼 안 보이게
            optionBackgroundConstraintLayout.visibility = View.GONE
            optionChoiceTextView.visibility = View.GONE

            imageView10.setOnClickListener {
                showCustomDialog()
            }
        }

        editingFinishTextView.setOnClickListener {
            val adapter = optionRecyclerView.adapter as IsaScheduleAdapter

            // 모든 아이템에 대해 순회하면서 edit_circle ImageButton의 visibility를 GONE으로 변경
            for (i in 0 until adapter.itemCount) {
                val viewHolder = optionRecyclerView.findViewHolderForAdapterPosition(i)
                viewHolder?.itemView?.findViewById<ImageButton>(R.id.edit_circle)?.visibility = View.GONE
            }

            editingTextView.visibility = View.VISIBLE
            editingFinishTextView.visibility = View.GONE

            optionBackgroundConstraintLayout.visibility = View.VISIBLE
            optionChoiceTextView.visibility = View.VISIBLE

            adapter.setEditingClicked(false) // 편집 모드 종료
            adapter.setEditingMode(false) // 편집 모드를 종료합니다.
        }
    }

    private fun showCustomDialog() {
        // 다이얼로그 레이아웃을 inflate
        val dialogView = layoutInflater.inflate(R.layout.option_dialogview_calendar, null)

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
            alertDialog.dismiss() // 다이얼로그 닫기
        }

        // 나가기 버튼 클릭 리스너 설정
        confirmButton.setOnClickListener {
            finish() // 현재 엑티비티 종료
            alertDialog.dismiss() // 다이얼로그 닫기

            // IsaScheduleActivity를 시작하여 처음 화면으로 전환
            val intent = Intent(this, IsaScheduleActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
        }

        // AlertDialog 표시
        alertDialog.show()
    }

    // 선택한 roundtab 버튼을 변경하는 메서드
    private fun selectRoundTab(selectedTab: ImageButton) {
        // 모든 라운드탭 버튼을 리스트로 저장
        val allRoundTabs = listOf(roundtab_3_Button, roundtab_2_Button, roundtab_1_Button, roundtab_2week_Button)

        // 현재 선택된 roundtab 버튼과 선택하려는 roundtab 버튼이 같은 경우, 선택 취소
        if (selectedRoundTab === selectedTab) {
            // 이미 선택된 상태이므로 아무것도 하지 않음
            return
        }

        // 선택된 roundtab 버튼 이미지 변경
        selectedTab.setImageResource(getRoundTabImage(selectedTab, isSelected = true))

        // 이전에 선택된 roundtab 버튼이 있는지 확인하고, 있다면 선택 취소
        selectedRoundTab?.let {
            it.setImageResource(getRoundTabImage(it, isSelected = false))
        }

        // 현재 선택된 roundtab 버튼 업데이트
        selectedRoundTab = selectedTab

        // 선택되지 않은 모든 라운드탭 버튼을 gray로 변경
        allRoundTabs.forEach { button ->
            if (button !== selectedRoundTab) {
                button.setImageResource(getRoundTabImage(button, isSelected = false))
            }
        }
    }


    // 선택된 상태에 따라 적절한 이미지 리소스를 반환하는 메서드
    private fun getRoundTabImage(imageButton: ImageButton, isSelected: Boolean): Int {
        return if (isSelected) {
            when (imageButton) {
                roundtab_3_Button -> R.drawable.roundtab_blue_3
                roundtab_2_Button -> R.drawable.roundtab_blue_2
                roundtab_1_Button -> R.drawable.roundtab_blue_1
                roundtab_2week_Button -> R.drawable.roundtab_blue_2week
                else -> R.drawable.roundtab_blue_1 // 기본값 설정
            }
        } else {
            when (imageButton) {
                roundtab_3_Button -> R.drawable.roundtab_3
                roundtab_2_Button -> R.drawable.roundtab_2
                roundtab_1_Button -> R.drawable.roundtab_1
                roundtab_2week_Button -> R.drawable.roundtab_2week
                else -> R.drawable.roundtab_1 // 기본값 설정
            }
        }
    }

    private fun moveCalendar(daysToSubtract: Int) {
        // 현재 선택된 날짜 가져오기
        val selectedDate = getSelectedDate()

        // 선택된 날짜에서 daysToSubtract만큼 이전으로 이동
        val dateFormat = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
        val calendar = Calendar.getInstance()
        calendar.time = dateFormat.parse(selectedDate) ?: Date()
        calendar.add(Calendar.DAY_OF_MONTH, daysToSubtract)

        // 변경된 날짜로 캘린더뷰 설정
        calendarView.date = calendar.timeInMillis
    }

    private fun getSelectedDate(): String {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = calendarView.date
        val dateFormat = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
        return dateFormat.format(calendar.time)
    }

    private fun showBottomSheetCalendar() {
        val bottomSheetDialog = BottomSheetDialog(this)
        val bottomSheetView = LayoutInflater.from(this).inflate(R.layout.bottomsheet_calendar, null)

        // 1월 TextView를 찾습니다.
        val month1TextView: TextView = bottomSheetView.findViewById(R.id.month1)
        // month1을 클릭했을 때의 동작을 정의합니다.
        month1TextView.setOnClickListener {
            // 캘린더뷰의 날짜를 2023년 1월로 설정합니다.
            calendarView.date = Calendar.getInstance().apply {
                set(2023, Calendar.JANUARY, 1)
            }.timeInMillis
            // 바텀 시트를 닫습니다.
            bottomSheetDialog.dismiss()
        }

        // 2월 TextView를 찾습니다.
        val month2TextView: TextView = bottomSheetView.findViewById(R.id.month2)
        // month2을 클릭했을 때의 동작을 정의합니다.
        month2TextView.setOnClickListener {
            // 캘린더뷰의 날짜를 2023년 2월로 설정합니다.
            calendarView.date = Calendar.getInstance().apply {
                set(2023, Calendar.FEBRUARY, 1)
            }.timeInMillis
            // 바텀 시트를 닫습니다.
            bottomSheetDialog.dismiss()
        }

        // 3월 TextView를 찾습니다.
        val month3TextView: TextView = bottomSheetView.findViewById(R.id.month3)
        // month2을 클릭했을 때의 동작을 정의합니다.
        month3TextView.setOnClickListener {
            // 캘린더뷰의 날짜를 2023년 3월로 설정합니다.
            calendarView.date = Calendar.getInstance().apply {
                set(2023, Calendar.MARCH, 1)
            }.timeInMillis
            // 바텀 시트를 닫습니다.
            bottomSheetDialog.dismiss()
        }

        // 4월 TextView를 찾습니다.
        val month4TextView: TextView = bottomSheetView.findViewById(R.id.month4)
        // month2을 클릭했을 때의 동작을 정의합니다.
        month4TextView.setOnClickListener {
            // 캘린더뷰의 날짜를 2023년 4월로 설정합니다.
            calendarView.date = Calendar.getInstance().apply {
                set(2023, Calendar.APRIL, 1)
            }.timeInMillis
            // 바텀 시트를 닫습니다.
            bottomSheetDialog.dismiss()
        }

        // 5월 TextView를 찾습니다.
        val month5TextView: TextView = bottomSheetView.findViewById(R.id.month5)
        // month2을 클릭했을 때의 동작을 정의합니다.
        month5TextView.setOnClickListener {
            // 캘린더뷰의 날짜를 2023년 5월로 설정합니다.
            calendarView.date = Calendar.getInstance().apply {
                set(2023, Calendar.MAY, 1)
            }.timeInMillis
            // 바텀 시트를 닫습니다.
            bottomSheetDialog.dismiss()
        }

        // 6월 TextView를 찾습니다.
        val month6TextView: TextView = bottomSheetView.findViewById(R.id.month6)
        // month2을 클릭했을 때의 동작을 정의합니다.
        month6TextView.setOnClickListener {
            // 캘린더뷰의 날짜를 2023년 6월로 설정합니다.
            calendarView.date = Calendar.getInstance().apply {
                set(2023, Calendar.JUNE, 1)
            }.timeInMillis
            // 바텀 시트를 닫습니다.
            bottomSheetDialog.dismiss()
        }

        // 7월 TextView를 찾습니다.
        val month7TextView: TextView = bottomSheetView.findViewById(R.id.month7)
        // month2을 클릭했을 때의 동작을 정의합니다.
        month7TextView.setOnClickListener {
            // 캘린더뷰의 날짜를 2023년 7월로 설정합니다.
            calendarView.date = Calendar.getInstance().apply {
                set(2023, Calendar.JULY, 1)
            }.timeInMillis
            // 바텀 시트를 닫습니다.
            bottomSheetDialog.dismiss()
        }

        // 8월 TextView를 찾습니다.
        val month8TextView: TextView = bottomSheetView.findViewById(R.id.month8)
        // month2을 클릭했을 때의 동작을 정의합니다.
        month8TextView.setOnClickListener {
            // 캘린더뷰의 날짜를 2023년 8월로 설정합니다.
            calendarView.date = Calendar.getInstance().apply {
                set(2023, Calendar.AUGUST, 1)
            }.timeInMillis
            // 바텀 시트를 닫습니다.
            bottomSheetDialog.dismiss()
        }

        // 9월 TextView를 찾습니다.
        val month9TextView: TextView = bottomSheetView.findViewById(R.id.month9)
        // month2을 클릭했을 때의 동작을 정의합니다.
        month9TextView.setOnClickListener {
            // 캘린더뷰의 날짜를 2023년 9월로 설정합니다.
            calendarView.date = Calendar.getInstance().apply {
                set(2023, Calendar.SEPTEMBER, 1)
            }.timeInMillis
            // 바텀 시트를 닫습니다.
            bottomSheetDialog.dismiss()
        }

        // 10월 TextView를 찾습니다.
        val month10TextView: TextView = bottomSheetView.findViewById(R.id.month10)
        // month2을 클릭했을 때의 동작을 정의합니다.
        month10TextView.setOnClickListener {
            // 캘린더뷰의 날짜를 2023년 10월로 설정합니다.
            calendarView.date = Calendar.getInstance().apply {
                set(2023, Calendar.OCTOBER, 1)
            }.timeInMillis
            // 바텀 시트를 닫습니다.
            bottomSheetDialog.dismiss()
        }

        // 11월 TextView를 찾습니다.
        val month11TextView: TextView = bottomSheetView.findViewById(R.id.month11)
        // month2을 클릭했을 때의 동작을 정의합니다.
        month11TextView.setOnClickListener {
            // 캘린더뷰의 날짜를 2023년 11월로 설정합니다.
            calendarView.date = Calendar.getInstance().apply {
                set(2023, Calendar.NOVEMBER, 1)
            }.timeInMillis
            // 바텀 시트를 닫습니다.
            bottomSheetDialog.dismiss()
        }

        // 12월 TextView를 찾습니다.
        val month12TextView: TextView = bottomSheetView.findViewById(R.id.month12)
        // month2을 클릭했을 때의 동작을 정의합니다.
        month12TextView.setOnClickListener {
            // 캘린더뷰의 날짜를 2023년 12월로 설정합니다.
            calendarView.date = Calendar.getInstance().apply {
                set(2023, Calendar.DECEMBER, 1)
            }.timeInMillis
            // 바텀 시트를 닫습니다.
            bottomSheetDialog.dismiss()
        }

        bottomSheetDialog.setContentView(bottomSheetView)
        bottomSheetDialog.show()
    }

    // 일정 등록
    private fun registerSchedule(str1: String, str2: String)  {
        val call = RetrofitObject.getRetrofitService.schedule("Bearer $token", RetrofitClient2.Requestschedule(str1, str2))
        call.enqueue(object : Callback<RetrofitClient2.Responseschedule> {
            override fun onResponse(
                call: Call<RetrofitClient2.Responseschedule>,
                response: Response<RetrofitClient2.Responseschedule>
            ) {
                Log.d("Retrofit31", response.toString())
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    Log.d("Retrofit3", responseBody.toString())
                    if (responseBody != null && responseBody.isSuccess) {
                        //이사일정 등록 완료
                        isRegistration = true
                        //optionRecyclerView.adapter = null
                        evenschedulelookup()

                    } else {
                        Toast.makeText(
                            this@IsaScheduleActivity,
                            responseBody?.message ?: "Unknown error",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

            override fun onFailure(call: Call<RetrofitClient2.Responseschedule>, t: Throwable) {
                val errorMessage = "Call Failed1: ${t.message}"
                Log.d("RetrofitError", errorMessage)
            }
        })

    }

    // 일정 삭제
    private fun deleteSchedule(peroid : String) {
        val call = RetrofitObject.getRetrofitService.scheduledelete("Bearer $token")
        call.enqueue(object : Callback<RetrofitClient2.Responsescheduledelete> {
            override fun onResponse(
                call: Call<RetrofitClient2.Responsescheduledelete>,
                response: Response<RetrofitClient2.Responsescheduledelete>
            ) {
                Log.d("Retrofit55555", response.toString())
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    Log.d("Retrofit5", responseBody.toString())
                    if (responseBody != null && responseBody.isSuccess) {
                        //이사일정 등록 완료
                        isRegistration = false
                        registerSchedule(peroid, moveDate)
                    } else {
                        Toast.makeText(
                            this@IsaScheduleActivity,
                            responseBody?.message ?: "Unknown error",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

            override fun onFailure(call: Call<RetrofitClient2.Responsescheduledelete>, t: Throwable) {
                val errorMessage = "Call Failed2: ${t.message}"
                Log.d("Retrofit", errorMessage)
            }
        })

    }

    // 상세 일정 조회
    private fun evenschedulelookup() {
        var date = ""
        var description = ""

        val call = RetrofitObject.getRetrofitService.evenschedulelookup("Bearer $token")
        call.enqueue(object : Callback<RetrofitClient2.ResponseEventScheduleLookup> {
            override fun onResponse(
                call: Call<RetrofitClient2.ResponseEventScheduleLookup>,
                response: Response<RetrofitClient2.ResponseEventScheduleLookup>
            ) {
                Log.d("Retrofit777", response.toString())
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    Log.d("Retrofit77777", responseBody.toString())
                    if (responseBody != null && responseBody.isSuccess) {
                        //여기서 리사이클러뷰 설정
                        scheduleItems.clear()
                        for (data in responseBody.data) {
                            date = data.eventDate
                            description = data.eventTitle
                            scheduleItems.add(IsaScheduleItem(date, description))
                        }
                        optionRecyclerView.adapter = null
                        optionRecyclerView.adapter = adapter
                    } else {
                        Toast.makeText(
                            this@IsaScheduleActivity,
                            responseBody?.message ?: "Unknown error",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

            override fun onFailure(call: Call<RetrofitClient2.ResponseEventScheduleLookup>, t: Throwable) {
                val errorMessage = "Call Failed: ${t.message}"
                Log.d("Retrofit", errorMessage)
            }
        })

    }
}