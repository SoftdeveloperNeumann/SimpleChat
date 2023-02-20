package com.example.simplechat

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.DropBoxManager
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.google.firebase.database.*

class NotificationService : Service() {

    lateinit var db: FirebaseDatabase
    lateinit var reference: DatabaseReference
    lateinit var username: String
    lateinit var chatPartner: String

    override fun onCreate() {
        super.onCreate()
        db = FirebaseDatabase.getInstance()

        username = MainActivity.userName
        chatPartner = MainActivity.chatPartner
        reference = db.getReference("messages/${username}_$chatPartner")

        reference.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val typeIndicator = object : GenericTypeIndicator<Map<String, String>>() {}
                val map = snapshot.getValue(typeIndicator)
                val user = map?.get("user")
                val message = map?.get("message")
                if (user != username) {
                    sendNotification(message)
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onChildRemoved(snapshot: DataSnapshot) {

            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    private fun sendNotification(message: String?) {
        val NOTIFICATIO_CHANNEL_ID = packageName
        val channelName = "SimpleChatMessage"

        val intent = Intent(this, ChatAktivity::class.java).apply {
            putExtra("user", username)
            putExtra("other", chatPartner)
        }

        val pendingIntent = PendingIntent.getActivity(applicationContext, 0, intent, 0)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NOTIFICATIO_CHANNEL_ID,
                channelName,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
            val builder = NotificationCompat.Builder(this, NOTIFICATIO_CHANNEL_ID)
            val notification = builder.setSmallIcon(R.drawable.bubble_in)
                .setContentTitle("Sie haben eine neue Nachricht")
                .setContentText(message)
                .setPriority(NotificationManager.IMPORTANCE_DEFAULT)
                .setCategory(Notification.CATEGORY_MESSAGE)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build()
            startForeground(1, notification)
        }else{
            startForeground(2,Notification())
    }
}

override fun onBind(intent: Intent?): IBinder? {
    return null
}
}