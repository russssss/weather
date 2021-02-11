package com.android.weatherapp.utils

import java.text.SimpleDateFormat
import java.util.*

class TimeUtils {
    companion object {
        val d = Date()
        fun formatTime(t: Long): String {
            d.time = t * 1000
            val formatDate = SimpleDateFormat("dd.MM.yyyy")
            val t = formatDate.format(d)
            return t
        }
    }
}