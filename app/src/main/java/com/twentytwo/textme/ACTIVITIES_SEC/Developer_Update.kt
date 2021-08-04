package com.twentytwo.textme.ACTIVITIES_SEC


import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.twentytwo.textme.FirestoreClass
import com.twentytwo.textme.Model.devUpate
import com.twentytwo.textme.R

class Developer_Update : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_developer_update)
        val FG = findViewById<EditText>(R.id.FG)
        val FS = findViewById<EditText>(R.id.FS)
        val INSTAID = findViewById<EditText>(R.id.INSTAID)
        val FBID = findViewById<EditText>(R.id.FBID)
        val WEBID = findViewById<EditText>(R.id.WEBID)


        val db = Firebase.firestore
        val docRef = db.collection("DEVELOPER").document("SECRET")
        docRef.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Document found in the offline cache
                val document = task.result
                if (document != null) {
                    val users = document.toObject<devUpate>()
                    if (users != null) {
                        FG.setText(users.following)
                        FS.setText(users.followers)
                        INSTAID.setText(users.instalink)
                        FBID.setText(users.facebooklink)
                        WEBID.setText(users.weblink)
                    }
                }
            }
        }
        val devUpdateBtn = findViewById<Button>(R.id.devUpdateBtn)
        devUpdateBtn.setOnClickListener {
            val data = devUpate(
                FS.text.toString(),
                FG.text.toString(),
                INSTAID.text.toString(),
                FBID.text.toString(),
                WEBID.text.toString()
            )

            FirestoreClass().CreateDEV(this@Developer_Update, data)

        }
    }

    fun DevFAILED() {
        Toast.makeText(this, "FAILEDD", Toast.LENGTH_SHORT).show()
    }

    fun DevSUCCESS() {
        Toast.makeText(this, "SUCCESS", Toast.LENGTH_SHORT).show()
    }
}