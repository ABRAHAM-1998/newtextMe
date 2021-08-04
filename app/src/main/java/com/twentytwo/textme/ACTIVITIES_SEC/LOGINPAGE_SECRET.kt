package com.twentytwo.textme.ACTIVITIES_SEC

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.twentytwo.textme.R
import java.util.*

class LOGINPAGE_SECRET : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loginpage_secret)
        val SecretCode = "NTY3MjQ2OTc0NTk1NDk0OA=="
        val decodedBytes = Base64.getDecoder().decode(SecretCode)
        val decodedString = String(decodedBytes)

        val SECBTN= findViewById<Button>(R.id.SECBTN)
        val secretKeyBOX= findViewById<TextView>(R.id.secretKeyBOX)



        SECBTN.setOnClickListener {
            val Code = secretKeyBOX.text.toString()

            if (Code == decodedString) {
                startActivity(Intent(this, Developer_Update::class.java))
            } else if (Code.isEmpty()) {
                secretKeyBOX.error = "FUCK OFF"

                return@setOnClickListener
            } else {
                secretKeyBOX.error = "FUCK YOU"

                return@setOnClickListener

            }

        }
    }
}