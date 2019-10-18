package ru.chieffly.meetroom.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent


class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        println("ON RECIEVE")
        val service = Intent(context, NotificationService::class.java)

        service.putExtra("success", intent.getIntExtra("success",0))
        service.putExtra("room", intent.getStringExtra("room"))
        service.putExtra("timestamp", intent.getLongExtra("timestamp", 0))

        context.startService(service)
    }

}