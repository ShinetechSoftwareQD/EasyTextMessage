package com.sms

import android.content.Context.MODE_PRIVATE
import android.util.Log
import okhttp3.*
import java.io.IOException
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.content.Context.MODE_PRIVATE





class WechatMsg {
    internal var TAG = "DingdingMsg"

    companion object {
        fun sendMsg(msg: String,key:String) {
            //val key = "test";
            val urlAPI = "https://test/send?key=$key"

            val textMsg = "{ \"msgtype\": \"text\", \"text\": {\"content\": \"$msg\"}}"

            val requestBody = RequestBody.create(
                MediaType.parse("application/json;charset=utf-8"),
                textMsg
            )

            val request = Request.Builder()
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .post(requestBody)
                .url(urlAPI).build()


            val client = OkHttpClient()
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.d("DingdingMsg", "onFailure：" + e.message)
                }

                @Throws(IOException::class)
                override fun onResponse(call: Call, response: Response) {
                    val responseStr = response.body().string()
                    Log.d("DingdingMsg", "Code：" + response.code().toString() + responseStr)
                }
            })

        }
    }

}