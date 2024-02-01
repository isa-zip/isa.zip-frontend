package com.example.zipfront

import android.os.Bundle
import android.widget.CalendarView
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

class IsaScheduleActivity : AppCompatActivity() {
    private lateinit var calendarView: CalendarView
    private lateinit var roundtab_3_Button: ImageButton
    private lateinit var roundtab_2_Button: ImageButton
    private lateinit var roundtab_1_Button: ImageButton
    private lateinit var roundtab_2week_Button: ImageButton
    private lateinit var optionRecyclerView: RecyclerView

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
                // 아이템이 클릭되었을 때 처리할 내용
                // 선택된 아이템의 배경과 이미지를 변경
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
    }

    private fun setCalendarDate(selectedDate: String) {
        // SimpleDateFormat을 사용하여 문자열 형태의 날짜를 Date 객체로 변환
        val dateFormat = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
        val date = dateFormat.parse(selectedDate)

        // Date 객체에서 년, 월, 일을 추출하여 캘린더뷰에 설정
        val calendar = Calendar.getInstance()
        calendar.time = date ?: Date()

        // 캘린더뷰의 날짜 설정
        calendarView.date = calendar.timeInMillis
    }

    // 3개월 전
    private fun calculateDateMinus90(calendar: Calendar): Calendar {
        val newCalendar_3 = calendar.clone() as Calendar
        newCalendar_3.add(Calendar.DAY_OF_YEAR, -90)
        return newCalendar_3
    }

    // 2개월 전
    private fun calculateDateMinus60(calendar: Calendar): Calendar {
        val newCalendar_2 = calendar.clone() as Calendar
        newCalendar_2.add(Calendar.DAY_OF_YEAR, -60)
        return newCalendar_2
    }

    // 1개월 전
    private fun calculateDateMinus30(calendar: Calendar): Calendar {
        val newCalendar_1 = calendar.clone() as Calendar
        newCalendar_1.add(Calendar.DAY_OF_YEAR, -30)
        return newCalendar_1
    }

    // 2주 전
    private fun calculateDateMinus14(calendar: Calendar): Calendar {
        val newCalendar_2week = calendar.clone() as Calendar
        newCalendar_2week.add(Calendar.DAY_OF_YEAR, -14)
        return newCalendar_2week
    }

    private fun getFormattedDate(calendar: Calendar): String {
        val dateFormat = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
        return dateFormat.format(calendar.time)
    }
}
