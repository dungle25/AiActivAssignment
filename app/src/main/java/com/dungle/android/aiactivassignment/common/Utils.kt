package com.dungle.android.aiactivassignment.common

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class Utils {
    companion object {

        @RequiresApi(Build.VERSION_CODES.O)
        @SuppressLint("SimpleDateFormat")
        fun getDate(data: String) : String {
            val formatter: DateTimeFormatter =
                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH)
            val date: LocalDateTime = LocalDateTime.parse(data, formatter)
            return "${date.month.name.take(3)}. ${date.dayOfMonth}"
        }

        @RequiresApi(Build.VERSION_CODES.O)
        @SuppressLint("SimpleDateFormat")
        fun getTime(data: String) : String {
            val formatter: DateTimeFormatter =
                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH)
            val date: LocalDateTime = LocalDateTime.parse(data, formatter)
            return date.hour.toString()
        }
    }
}