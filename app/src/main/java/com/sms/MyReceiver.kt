package com.sms

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class MyReceiver: BroadcastReceiver()
{
    override fun onReceive(Context: Context?, intent: Intent?) {
        if (intent != null) {
            if (intent.getAction().equals("com.example.demo.destroy")) {
                if (Context != null) {
                    val sevice=Intent(Context, SMSService::class.java)
                    Context.startService(sevice)
                }
            }
        }
    }
}