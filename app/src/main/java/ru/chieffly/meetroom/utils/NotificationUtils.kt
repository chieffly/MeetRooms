package ru.chieffly.meetroom.utils

import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import ru.chieffly.meetroom.model.rooms.Meetroom
import java.util.*

class NotificationUtils {

    fun setNotification(room: Meetroom, activity: Activity) {
        val timeInMilliSeconds = Calendar.getInstance().timeInMillis + 50

        if (timeInMilliSeconds > 0) {

            val alarmManager = activity.getSystemService(Activity.ALARM_SERVICE) as AlarmManager
            val alarmIntent = Intent(activity.applicationContext, AlarmReceiver::class.java)

            //TODO Тестовая генерация контента, имитация PUSH уведомления
            val random = Random()

            val success = random.nextInt(2)
            alarmIntent.putExtra("success", success)
            alarmIntent.putExtra("room", room.name)
            alarmIntent.putExtra("timestamp", timeInMilliSeconds)


            val calendar = Calendar.getInstance()
            calendar.timeInMillis = timeInMilliSeconds

            val pendingIntent = PendingIntent.getBroadcast(activity, 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT)
            alarmManager.set(AlarmManager.RTC, System.currentTimeMillis() + 4000, pendingIntent)
        }
    }
}