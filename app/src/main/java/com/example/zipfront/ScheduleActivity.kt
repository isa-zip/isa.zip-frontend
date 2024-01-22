package com.example.zipfront

import android.icu.text.SimpleDateFormat
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class ScheduleActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CalendarAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule)

        recyclerView = findViewById(R.id.calendarRecyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 7)
        val calendarItems = generateCalendarData()

        adapter = CalendarAdapter(calendarItems)
        recyclerView.adapter = adapter
    }

    private fun generateCalendarData(): List<CalendarItem> {
        val calendarItems = mutableListOf<CalendarItem>()
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_MONTH, 1)

        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)

        val daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        for (day in 1..daysInMonth) {
            calendarItems.add(CalendarItem(day, month, year))
            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }

        return calendarItems
    }
}
