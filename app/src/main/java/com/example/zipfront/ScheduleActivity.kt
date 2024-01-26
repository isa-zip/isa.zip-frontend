package com.example.zipfront

import android.content.Intent
import android.os.Bundle
import android.widget.CalendarView
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class ScheduleActivity : AppCompatActivity() {

    private lateinit var calendarView: CalendarView
    private lateinit var selectedDateTextView: TextView
    private lateinit var registerButton: ImageButton
    private lateinit var selectedDate: String // 선택된 날짜를 저장할 변수 추가

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule)

        calendarView = findViewById(R.id.calendarView)
        selectedDateTextView = findViewById(R.id.textViewInside)
        registerButton = findViewById(R.id.imageButton5)

        // 초기에는 버튼을 비활성화
        registerButton.isEnabled = false

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            // 달력의 날짜가 변경될 때의 동작
            selectedDate = formatSelectedDate(year, month, dayOfMonth)
            selectedDateTextView.text = selectedDate
            updateRegisterButtonImage()
        }

        // 이미지 버튼 클릭 시 동작
        registerButton.setOnClickListener {
            // 선택한 날짜를 IsaScheduleActivity로 전달
            val intent = Intent(this, IsaScheduleActivity::class.java)
            intent.putExtra("selectedDate", selectedDate)
            startActivity(intent)
        }
    }

    private fun formatSelectedDate(year: Int, month: Int, dayOfMonth: Int): String {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)
        val dateFormat = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
        return dateFormat.format(calendar.time)
    }

    private fun updateRegisterButtonImage() {
        // 선택된 날짜가 있을 때만 버튼을 활성화
        registerButton.isEnabled = true
        // 아무 날짜가 선택되었을 때 항상 btn_register_blue.xml로 버튼 이미지 변경
        registerButton.setImageResource(R.drawable.btn_register_blue)
    }
}
