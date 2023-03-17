package com.sms

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.support.annotation.Nullable
import android.util.Log

class SMSService : Service() {
    override fun onBind(intent: Intent?): IBinder? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private val TAG = "FrontService"
    override fun onCreate() {
        Log.d(TAG, "onCreate")
        super.onCreate()
        val builder = Notification.Builder(this)
        builder.setSmallIcon(R.mipmap.ic_launcher)
        builder.setContentTitle("转")
        builder.setContentText("转发信息")
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        builder.setContentIntent(pendingIntent)

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            //修改安卓8.1以上系统报错
            val notificationChannel =
                NotificationChannel(TAG, TAG, NotificationManager.IMPORTANCE_MIN)
            notificationChannel.enableLights(false)//如果使用中的设备支持通知灯，则说明此通知通道是否应显示灯
            notificationChannel.setShowBadge(false)//是否显示角标
            notificationChannel.lockscreenVisibility = Notification.VISIBILITY_SECRET
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(notificationChannel)
            builder.setChannelId(TAG)
        }

        val notification = builder.build()
        startForeground(1, notification)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "onStartCommand")
        return Service.START_STICKY
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy")
        stopForeground(true)
        val intent = Intent("com.example.demo.destroy")
        sendBroadcast(intent)
        super.onDestroy()
    }
}
