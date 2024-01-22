package com.example.zipfront

import java.util.Date

class CalendarItem(val month: String, val day: Int, val dayOfWeek: String) {
    private var date: Date
    var title: String = ""
    var description: String = ""

    init {
        date = Date()
    }

    fun getDate(): Date {
        return date
    }

    fun setDate(date: Date) {
        this.date = date
    }
}

