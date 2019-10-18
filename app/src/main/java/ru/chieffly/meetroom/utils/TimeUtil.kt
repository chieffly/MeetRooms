package ru.chieffly.meetroom.utils

import java.text.SimpleDateFormat
import java.util.*

object TimeUtil {
    private const val SECOND_MILLIS = 1000
    private const val MINUTE_MILLIS = 60 * SECOND_MILLIS
    private const val HOUR_MILLIS = 60 * MINUTE_MILLIS
    private const val DAY_MILLIS = 24 * HOUR_MILLIS

    val dateFormatToSeconds: SimpleDateFormat by lazy { SimpleDateFormat("yyyy-MM-dd HH:mm:ss z") }
    val dateFormatToMinutes : SimpleDateFormat by lazy { SimpleDateFormat("yyyy-MM-dd HH:mm z")}
    val dateFormatToDays : SimpleDateFormat by lazy { SimpleDateFormat("dd.MM.yyyy")}
    val dateFormatOnlyHours : SimpleDateFormat by lazy { SimpleDateFormat("HH:mm")}


    fun getNow () = Date().time
    fun getNowFormated (sdf: SimpleDateFormat) : String {
        return parseUnixTimeFormated(getNow(), sdf)
    }

    fun parseUnixTime(unixSeconds: Long): String {
        val date = Date(unixSeconds * 1000L)
        dateFormatToDays.timeZone = TimeZone.getTimeZone("GMT+3")
        return dateFormatToDays.format(date)
    }

    fun parseUnixTimeFormated(unixSeconds: Long, sdf: SimpleDateFormat): String {
        val date = Date(unixSeconds * 1000L)
        sdf.timeZone = TimeZone.getTimeZone("GMT+3")
        return sdf.format(date)
    }

    fun getCalendarTimeFormated(calendar: Calendar, sdf: SimpleDateFormat): String {
        sdf.setTimeZone(calendar.getTimeZone());

        return sdf.format(calendar.time)
    }

    fun getDate (unixSeconds: Long) : String {

        return dateFormatToDays.format(unixSeconds)
    }

    fun getTimeAgo(time: Long): String? {
        var time = time
        if (time < 1000000000000L) {
            time *= 1000
        }

        val now = System.currentTimeMillis()
        if (time > now || time <= 0) {
            return null
        }

        val diff = now - time
        return when {
            diff < MINUTE_MILLIS -> " только что"
            diff < 2 * MINUTE_MILLIS -> " минуты назад"
            diff < 50 * MINUTE_MILLIS -> (diff / MINUTE_MILLIS).toString() + " минут назад"
            diff < 90 * MINUTE_MILLIS -> " час назад"
            diff < 24 * HOUR_MILLIS -> (diff / HOUR_MILLIS).toString() + " часов назад"
            diff < 48 * HOUR_MILLIS -> "вчера"
            else -> (diff / DAY_MILLIS).toString() + " дней назад"
        }
    }
}