package com.example.ukrgram.broadcastReceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class MyBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val action = intent?.action

        if (action == Intent.ACTION_POWER_CONNECTED) {
            Toast.makeText(context, "Зарядное устройство подключено", Toast.LENGTH_SHORT).show()
        } else if (action == Intent.ACTION_POWER_DISCONNECTED) {
            Toast.makeText(context, "Зарядное устройство отключено", Toast.LENGTH_SHORT).show()
        } else if (action == "com.example.MY_CUSTOM_ACTION") {
            // Действия при получении пользовательского намерения
            val message = intent.getStringExtra("message")
            Toast.makeText(context,
                "Получено пользовательское сообщение: $message",
                Toast.LENGTH_SHORT).show()
        }
    }
}
