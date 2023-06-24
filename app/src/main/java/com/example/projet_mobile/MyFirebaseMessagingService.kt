package com.sna.frwing

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.projet_mobile.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        remoteMessage.notification?.let { notification ->
            getFirebaseMessage(notification.title, notification.body)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getFirebaseMessage(title: String?, msg: String?) {
        val builder = NotificationCompat.Builder(this, "myFirebaseChannel")
            .setSmallIcon(R.drawable.img)
            .setContentTitle(title)
            .setContentText(msg)
            .setAutoCancel(true)

        val notificationManager = NotificationManagerCompat.from(this)
        createNotificationChannel()
        notificationManager.notify(101, builder.build())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        val channelId = "myFirebaseChannel"
        val channelName = "Firebase Notifications"
        val channelDescription = "Notifications from Firebase"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelId, channelName, importance).apply {
            description = channelDescription
            enableLights(true)
            lightColor = Color.GREEN
            enableVibration(true)
            vibrationPattern = longArrayOf(100, 200, 300, 400, 500)
        }

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}
