package com.example.zipfront

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.zipfront.CalendarAdapter
import com.example.zipfront.CalendarItem
import com.example.zipfront.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class CalendarFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.column_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val calendarRecyclerView: RecyclerView = view.findViewById(R.id.calendarRecyclerView)

        // 캘린더 아이템 리스트를 생성
        val calendarItems = generateCalendarItems(2023, 2024)

        val calendarAdapter = CalendarAdapter(calendarItems)

        val layoutManager = LinearLayoutManager(requireContext())
        calendarRecyclerView.layoutManager = layoutManager

        calendarRecyclerView.adapter = calendarAdapter
    }

    private fun generateCalendarItems(startYear: Int, endYear: Int): List<CalendarItem> {
        val calendarItems = mutableListOf<CalendarItem>()

        for (year in startYear..endYear) {
            for (month in 1..12) {
                val calendar = Calendar.getInstance()
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month - 1)

                val daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

                for (day in 1..daysInMonth) {
                    calendar.set(Calendar.DAY_OF_MONTH, day)

                    val monthName = SimpleDateFormat("MMMM", Locale.getDefault()).format(calendar.time)
                    val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
                    val dayOfWeek = SimpleDateFormat("EEEE", Locale.getDefault()).format(calendar.time)

                    val calendarItem = CalendarItem(monthName, dayOfMonth, dayOfWeek)
                    calendarItems.add(calendarItem)
                }
            }
        }

        return calendarItems
    }
}
