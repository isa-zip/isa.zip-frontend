package com.example.zipfront

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.CalendarView
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class IsaScheduleActivity : AppCompatActivity() {
    private lateinit var calendarView: CalendarView
    private lateinit var editTextView: TextView
    private lateinit var roundtab_3_Button: ImageButton
    private lateinit var roundtab_2_Button: ImageButton
    private lateinit var roundtab_1_Button: ImageButton
    private lateinit var roundtab_2week_Button: ImageButton
    private var selectedCalendar: Calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_isa_schedule)

        // 전달된 날짜 받기
        val selectedDate = intent.getStringExtra("selectedDate")

        calendarView = findViewById(R.id.calendarView)

        // 선택한 날짜가 있다면 해당 날짜로 캘린더뷰 설정
        if (!selectedDate.isNullOrEmpty()) {
            setCalendarDate(selectedDate)
        }

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            selectedCalendar.set(year, month, dayOfMonth)
        }

        editTextView = findViewById(R.id.editing)
        editTextView.setOnClickListener {
            showEditDialog(selectedCalendar)
        }

        // 3개월(roundtab3) 버튼 참조
        roundtab_3_Button = findViewById(R.id.roundtab3)

        // roundtab3 버튼 클릭 리스너 설정
        roundtab_3_Button.setOnClickListener {
            // 90일을 뺀 날짜 계산
            val newCalendar_3 = calculateDateMinus90(selectedCalendar)

            // 계산된 날짜로 캘린더뷰 설정
            setCalendarDate(getFormattedDate(newCalendar_3))

            // 계산된 날짜로 선택한 캘린더에 날짜 설정
            selectedCalendar = newCalendar_3

            // 버튼 누르면 색 변경
            roundtab_1_Button.setImageResource(R.drawable.roundtab_1)
            roundtab_2_Button.setImageResource(R.drawable.roundtab_2)
            roundtab_3_Button.setImageResource(R.drawable.roundtab_blue_3)
            roundtab_2week_Button.setImageResource(R.drawable.roundtab_2week)
        }

        // 2개월(roundtab2) 버튼 참조
        roundtab_2_Button = findViewById(R.id.roundtab2)

        // 2개월(roundtab2) 버튼 클릭 리스너 설정
        roundtab_2_Button.setOnClickListener {
            // 60일을 뺀 날짜 계산
            val newCalendar_2 = calculateDateMinus60(selectedCalendar)

            // 계산된 날짜로 캘린더뷰 설정
            setCalendarDate(getFormattedDate(newCalendar_2))

            // 계산된 날짜로 선택한 캘린더에 날짜 설정
            selectedCalendar = newCalendar_2

            // 버튼 누르면 색 변경
            roundtab_1_Button.setImageResource(R.drawable.roundtab_1)
            roundtab_2_Button.setImageResource(R.drawable.roundtab_blue_2)
            roundtab_3_Button.setImageResource(R.drawable.roundtab_3)
            roundtab_2week_Button.setImageResource(R.drawable.roundtab_2week)
        }

        // 1개월(roundtab1) 버튼 참조
        roundtab_1_Button = findViewById(R.id.roundtab1)

        // 1개월(roundtab1) 버튼 클릭 리스너 설정
        roundtab_1_Button.setOnClickListener {
            // 30일을 뺀 날짜 계산
            val newCalendar_1 = calculateDateMinus30(selectedCalendar)

            // 계산된 날짜로 캘린더뷰 설정
            setCalendarDate(getFormattedDate(newCalendar_1))

            // 계산된 날짜로 선택한 캘린더에 날짜 설정
            selectedCalendar = newCalendar_1

            // 버튼 누르면 색 변경
            roundtab_1_Button.setImageResource(R.drawable.roundtab_blue_1)
            roundtab_2_Button.setImageResource(R.drawable.roundtab_2)
            roundtab_3_Button.setImageResource(R.drawable.roundtab_3)
            roundtab_2week_Button.setImageResource(R.drawable.roundtab_2week)
        }

        // 2주(roundtab_2week) 버튼 참조
        roundtab_2week_Button = findViewById(R.id.roundtab_2week)

        // 1개월(roundtab_2week) 버튼 클릭 리스너 설정
        roundtab_2week_Button.setOnClickListener {
            // 30일을 뺀 날짜 계산
            val newCalendar_2week = calculateDateMinus14(selectedCalendar)

            // 계산된 날짜로 캘린더뷰 설정
            setCalendarDate(getFormattedDate(newCalendar_2week))

            // 계산된 날짜로 선택한 캘린더에 날짜 설정
            selectedCalendar = newCalendar_2week

            // 버튼 누르면 색 변경
            roundtab_1_Button.setImageResource(R.drawable.roundtab_1)
            roundtab_2_Button.setImageResource(R.drawable.roundtab_2)
            roundtab_3_Button.setImageResource(R.drawable.roundtab_3)
            roundtab_2week_Button.setImageResource(R.drawable.roundtab_blue_2week)
        }

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

        // 선택한 캘린더에 날짜 설정
        selectedCalendar.time = date ?: Date()
    }

    private fun showEditDialog(selectedCalendar: Calendar) {
        // AlertDialog.Builder를 사용하여 일정 추가 다이얼로그 생성
        val builder = AlertDialog.Builder(this)
        builder.setTitle("일정 추가")
        builder.setMessage("'${getFormattedDate(selectedCalendar)}'에 일정을 추가합니다.")

        // 일정 추가 버튼 클릭 시
        builder.setPositiveButton("일정 추가", DialogInterface.OnClickListener { dialog, _ ->
            // 여기에 일정 추가 동작을 구현
            // 예: 데이터를 저장하거나 다른 처리를 수행
            dialog.dismiss() // 다이얼로그 닫기
        })

        // 취소 버튼 클릭 시 동작 설정
        builder.setNegativeButton("취소", DialogInterface.OnClickListener { dialog, _ ->
            dialog.dismiss() // 다이얼로그 닫기
        })

        // 다이얼로그 표시
        builder.create().show()
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

    // 추가된 함수: 편집 버튼 클릭 시 호출됨
    fun onEditClicked(view: View) {
        // 일정 편집 다이얼로그 표시
        showEditDialog(selectedCalendar)
    }
}
