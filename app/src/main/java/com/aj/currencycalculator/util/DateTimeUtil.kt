package com.aj.currencycalculator.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

class DateTimeUtil {

    companion object {

        // TODO: Remove
        @SuppressLint("SimpleDateFormat")
        fun getMillisOfLastXDays(days: Int): Date? {
            val day = -days
            val cal: Calendar = Calendar.getInstance()
            cal.add(Calendar.DAY_OF_YEAR, day)
            return Date(cal.timeInMillis)
        }

        fun getDate(date: Date?): String? {
            date?.let {
                val sdf = SimpleDateFormat("dd-MM-yyyy")
                return sdf.format(date)
            }
            return ""
        }

        fun getAPIDate(date: Date?): String? {
            date?.let {
                val sdf = SimpleDateFormat("yyyy-MM-dd")
                return sdf.format(date)
            }
            return ""
        }
    }
}
