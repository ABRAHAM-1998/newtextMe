package com.twentytwo.textme.ACTIVITIES_SEC

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.twentytwo.textme.R

class FAQ : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_faq)
        val FAQ_delete_chat = findViewById<TextView>(R.id.FAQ_delete_chat)
        FAQ_delete_chat.setText("Press and hold to select chat,then to delete! \n" +
                "Only if the repecipten viewed or seen messages")

        val FAQ_forgetpassword = findViewById<TextView>(R.id.FAQ_forgetpassword)
        FAQ_forgetpassword.setText("To change the password,you need to logout and use forget passwprd methord")
    }
}