package com.twentytwo.textme.ACTIVITIES_SEC

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.twentytwo.textme.R
import com.twentytwo.textme.RETROFIT.RetrofitClient
import com.twentytwo.textme.RETROFIT.defaultresponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class SpalshScreen : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private val compositeDisposable = CompositeDisposable()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spalsh_screen)
        //---------------------------------------------------------------------------
        //=====================FULL-SCREEN===========================
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
            )
        }
        //--------------------------------------------------------------------
        auth = FirebaseAuth.getInstance()

        //==========================TIMER===========================
        Timer().schedule(object : TimerTask() {
            override fun run() {
                startActivity(Intent(this@SpalshScreen, LoginActivity::class.java))
                finish()

            }
        }, 500L)




    }
}
