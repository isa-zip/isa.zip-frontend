package com.example.zipfront

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.zipfront.CalendarItem
import com.example.zipfront.R

class CalendarAdapter(private val calendarItems: List<CalendarItem>) :
    RecyclerView.Adapter<CalendarAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_calendar, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: CalendarItem = calendarItems[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return calendarItems.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val monthTextView: TextView = itemView.findViewById(R.id.monthTextView)
        private val dayTextView: TextView = itemView.findViewById(R.id.dayTextView)
        private val dayOfWeekTextView: TextView = itemView.findViewById(R.id.dayOfWeekTextView)

        fun bind(item: CalendarItem) {
            monthTextView.text = item.month
            dayTextView.text = item.day.toString()
            dayOfWeekTextView.text = item.dayOfWeek
        }
    }
}
