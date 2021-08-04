package com.twentytwo.textme.ACTIVITIES_SEC

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.twentytwo.textme.Model.devUpate
import com.twentytwo.textme.R

class DeveloperActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_developer)

        val secretbtn = findViewById<TextView>(R.id.secretbtn)
        val linkInsta = findViewById<TextView>(R.id.linkInsta)
        val instFG = findViewById<TextView>(R.id.instFG)
        val instaFOLOERs = findViewById<TextView>(R.id.instaFOLOERs)
        val linkFB = findViewById<TextView>(R.id.linkFB)
        val linkWEB = findViewById<TextView>(R.id.linkWEB)

        secretbtn.setOnClickListener {
            startActivity(Intent(this, LOGINPAGE_SECRET::class.java))
        }
        val db = Firebase.firestore
        val docRef = db.collection("DEVELOPER").document("SECRET")
        docRef.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Document found in the offline cache
                val document = task.result
                if (document != null) {
                    val users = document.toObject<devUpate>()
                    if (users != null) {
                        linkInsta.text = users.instalink
                        instaFOLOERs.text = users.followers
                        instFG.text = users.following
                        linkFB.text = users.facebooklink
                        linkWEB.text = users.weblink
                    }
                }
            }
        }
    }
}
