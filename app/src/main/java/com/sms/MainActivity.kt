package com.sms

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pref = getSharedPreferences("data", Context.MODE_PRIVATE)
        val key = pref.getString("key","50f1fb37-358d-4dd6-8199-049a328151ed")

        val editText = findViewById<EditText>(R.id.editText)
        editText.text = Editable.Factory.getInstance().newEditable(key);

        val textView = findViewById<TextView>(R.id.textView)
        textView.text = Editable.Factory.getInstance().newEditable(key);

        editText.addTextChangedListener(object :TextWatcher{
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
        })


        val pm = packageManager
        val permission_receive_sms =
            PackageManager.PERMISSION_GRANTED == pm.checkPermission(
                "android.permission.RECEIVE_SMS",
                this.packageName
            )

        if (!(permission_receive_sms)) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.RECEIVE_SMS),
                0x01
            )
        }
        val intent = Intent(this, SMSService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent)
        } else {
            startService(intent)
        }
    }

    fun saveKey(view: View) {
        val editText = findViewById<EditText>(R.id.editText)
        if (editText.text.toString() != "")
        {
            var textView = findViewById<TextView>(R.id.textView)
            var newKey = editText.text.toString();
            textView.text = newKey;
            var pref = getSharedPreferences("data", Context.MODE_PRIVATE).edit()
            pref.putString("key", newKey)
            pref.commit()
        }
    }
}
