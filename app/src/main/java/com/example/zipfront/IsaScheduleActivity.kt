package com.example.zipfront

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.CalendarView
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
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

    private var selectedRoundTab: ImageButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_isa_schedule)

        // 리사이클러뷰 초기화
        optionRecyclerView = findViewById(R.id.option_rv)
        val layoutManager = LinearLayoutManager(this)
        optionRecyclerView.layoutManager = layoutManager


        // 데이터 준비
        val scheduleItems = ArrayList<IsaScheduleItem>()
        scheduleItems.add(IsaScheduleItem("2023.11.7", "방 확정"))
        scheduleItems.add(IsaScheduleItem("2023.11.7", "방 확정"))
        scheduleItems.add(IsaScheduleItem("2023.11.7", "방 확정"))
        scheduleItems.add(IsaScheduleItem("2023.11.7", "방 확정"))
        scheduleItems.add(IsaScheduleItem("2023.11.7", "방 확정"))

        // 어댑터 설정
        val adapter = IsaScheduleAdapter(scheduleItems, object : IsaScheduleAdapter.OnItemClickListener {
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
        optionRecyclerView.adapter = adapter

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
        // 버튼에 클릭 리스너 설정
        roundtab_3_Button.setOnClickListener {
            selectRoundTab(roundtab_3_Button)
            moveCalendar(-90)
        }
        // 버튼에 클릭 리스너 설정
        roundtab_2_Button.setOnClickListener {
            selectRoundTab(roundtab_2_Button)
            moveCalendar(-60)
        }
        // 버튼에 클릭 리스너 설정
        roundtab_1_Button.setOnClickListener {
            selectRoundTab(roundtab_1_Button)
            moveCalendar(-30)
        }
        // 버튼에 클릭 리스너 설정
        roundtab_2week_Button.setOnClickListener {
            selectRoundTab(roundtab_2week_Button)
            moveCalendar(-14)
        }

        // imageView10을 클릭했을 때 액티비티 종료
        val imageView10: ImageView = findViewById(R.id.imageView10)
        imageView10.setOnClickListener {
            finish()
        }

        // 년도 텍스트뷰 클릭 시 bottom sheet 표시
        val yearTextView: TextView = findViewById(R.id.year_text)
        yearTextView.setOnClickListener {
            showBottomSheetCalendar()
        }
    }

    // 선택한 roundtab 버튼을 변경하는 메서드
    private fun selectRoundTab(selectedTab: ImageButton) {
        // 모든 라운드탭 버튼을 리스트로 저장
        val allRoundTabs = listOf(roundtab_3_Button, roundtab_2_Button, roundtab_1_Button, roundtab_2week_Button)

        // 현재 선택된 roundtab 버튼과 선택하려는 roundtab 버튼이 같은 경우, 선택 취소
        if (selectedRoundTab === selectedTab) {
            // 선택된 roundtab 버튼 이미지 변경
            selectedTab.setImageResource(getRoundTabImage(selectedTab, isSelected = false))
            // 현재 선택된 roundtab 버튼 초기화
            selectedRoundTab = null
        } else {
            // 선택된 roundtab 버튼 이미지 변경
            selectedTab.setImageResource(getRoundTabImage(selectedTab, isSelected = true))

            // 이전에 선택된 roundtab 버튼이 있는지 확인하고, 있다면 선택 취소
            selectedRoundTab?.let {
                it.setImageResource(getRoundTabImage(it, isSelected = false))
            }

            // 현재 선택된 roundtab 버튼 업데이트
            selectedRoundTab = selectedTab
        }

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
}
