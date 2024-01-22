package com.example.zipfront

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CalendarAdapter(private val items: List<CalendarItem>) : RecyclerView.Adapter<CalendarAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.calendar_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val calendarItemText1: TextView = itemView.findViewById(R.id.calendarItemText1)
        private val calendarItemText2: TextView = itemView.findViewById(R.id.calendarItemText2)
        private val calendarItemText3: TextView = itemView.findViewById(R.id.calendarItemText3)
        private val calendarItemText4: TextView = itemView.findViewById(R.id.calendarItemText4)
        private val calendarItemText5: TextView = itemView.findViewById(R.id.calendarItemText5)
        private val calendarItemText6: TextView = itemView.findViewById(R.id.calendarItemText6)
        private val calendarItemText7: TextView = itemView.findViewById(R.id.calendarItemText7)

        fun bind(item: CalendarItem) {
            // Set data from CalendarItem to each TextView
            calendarItemText1.text = item.day.toString()
            calendarItemText2.text = item.day.toString()
            calendarItemText3.text = item.day.toString()
            calendarItemText4.text = item.day.toString()
            calendarItemText5.text = item.day.toString()
            calendarItemText6.text = item.day.toString()
            calendarItemText7.text = item.day.toString()
        }
    }
}
