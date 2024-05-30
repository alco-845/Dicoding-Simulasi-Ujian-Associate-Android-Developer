package com.dicoding.todoapp.utils

import java.text.SimpleDateFormat
import java.util.*

object DateConverter {
    fun convertMillisToString(timeMillis: Long): String {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timeMillis
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return sdf.format(calendar.time)
    }

    fun convertStringToMillis(date: String): Long {
        val df = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return df.parse(date).time
    }
}