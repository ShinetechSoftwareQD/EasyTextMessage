package com.sms

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.SmsMessage
import android.util.Log
import java.text.SimpleDateFormat
import java.util.*
import okhttp3.*
import okhttp3.RequestBody;
class SMSBroadcastReceiver:BroadcastReceiver() {
    private val TAG = "TSMSBroadcastReceiver"
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d(TAG, "onReceive intent ")
        val receiveAction = intent!!.getAction()
        Log.d(TAG, "onReceive intent " + receiveAction!!)
        if (receiveAction == "android.provider.Telephony.SMS_RECEIVED") {

            val obj = intent!!.getExtras().get("pdus") as Array<Any>
            val sb = StringBuilder()
            for (pdus in obj) {
                val pdusMsg = pdus as ByteArray
                val sms = SmsMessage.createFromPdu(pdusMsg)
                val mobile = sms.originatingAddress//发送短信的手机号
                val content = sms.messageBody//短信内容
                //下面是获取短信的发送时间
                val date = Date(sms.timestampMillis)
                val date_time = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date)
                //追加到StringBuilder中
                sb.append("短信发送号码：$mobile\n短信内容：$content\n发送时间：$date_time\n\n")

            }
//            SendUtil.send_msg(sb.toString())
            if(context != null){
                val pref = context.getSharedPreferences("data", Context.MODE_PRIVATE)
                val key = pref.getString("key","test")
                WechatMsg.sendMsg(sb.toString(),key)
            }
            Log.d(TAG, "短信：$sb")
        }    }
}