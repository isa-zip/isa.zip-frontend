package com.example.zipfront

import java.util.Date

class CalendarItem(val day: Int, val month: Int, val year: Int) {
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
