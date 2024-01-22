package com.example.zipfront

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.zipfront.CalendarAdapter
import com.example.zipfront.CalendarItem
import com.example.zipfront.R

class ScheduleActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CalendarAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule)

        recyclerView = findViewById(R.id.calendarRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val calendarItems = mutableListOf<CalendarItem>()
        // 캘린더 항목을 추가하거나 적절한 데이터를 가져와서 calendarItems에 추가

        adapter = CalendarAdapter(calendarItems)
        recyclerView.adapter = adapter
    }
}