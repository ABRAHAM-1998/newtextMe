package com.twentytwo.textme.ACTIVITIES_SEC

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.twentytwo.textme.Model.Feeds
import com.twentytwo.textme.Model.Users
import com.twentytwo.textme.R
import com.twentytwo.textme.ui.CHATS.ChatActivity
import com.twentytwo.textme.ui.FEEDS.Main_Adapter_cycle
import java.util.*


class ProfileActivity : AppCompatActivity() {

    private var filePath: Uri? = null
    internal var storage: FirebaseStorage? = null
    internal var storageReference: StorageReference? = null
    private var NameUser: String = ""
    private var nickname: String = ""
    private var status: String = ""
    private lateinit var mUser: String

    private lateinit var auth: FirebaseAuth

    private var alphaAdapters: Main_Adapter_cycle? = null
//////////////////////////////////////////////////////////////////////////////////////////////////////////

    private var gridLayoutManager: GridLayoutManager? = null
    private var reccyclerViewMain: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP

        storage = FirebaseStorage.getInstance()

        storageReference = storage!!.reference

        auth = FirebaseAuth.getInstance()

        val ImagePrewiew: ImageView = findViewById(R.id.ImagePrewiew)
        val editprofile = findViewById<Button>(R.id.bt_editProfile)
        var LogOutBtm = findViewById<TextView>(R.id.LogOutBtm)

        LogOutBtm.visibility = View.INVISIBLE
        editprofile.visibility = View.INVISIBLE


        //=======================================================================================================================================
        if (intent.hasExtra("touserId")) {
            val bundle = intent.extras
            if (bundle!!.getString("touserId") != null) {
                mUser = intent.extras!!.getString("touserId") as String
                val db = Firebase.firestore
                val docRef = db.collection("UserSegment").document("${mUser}")
                docRef.get().addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Document found in the offline cache
                        val document = task.result
                        if (document != null) {
                            val users = document.toObject<Users>()
                            val tv_name = findViewById<TextView>(R.id.tv_name)
                            val tv_status = findViewById<TextView>(R.id.tv_status)
                            val tv_Nickname = findViewById<TextView>(R.id.tv_Nickname)
                            var profilePicture = users?.proFileImageUrl.toString()
                            Glide.with(this)
                                .load(profilePicture)
                                .placeholder(R.drawable.logo2)
                                .into(ImagePrewiew)

                            tv_name.text = users?.name.toString()
                            tv_Nickname.text = users?.nickname.toString()
                            tv_status.text = users?.descreption.toString()

                            if (users != null) {
                                NameUser = users.name

                                val editprofile = findViewById<Button>(R.id.bt_editProfile)


                                val sendMessageProfile =
                                    findViewById<Button>(R.id.sendMessageProfile)
                                sendMessageProfile.visibility = View.VISIBLE
                                editprofile.visibility = View.INVISIBLE
                                sendMessageProfile.setOnClickListener {
                                    val intent = Intent(this, ChatActivity::class.java)
                                    intent.putExtra("toUser", users)
                                    startActivity(intent)
                                }
                            }
                        }
                    }
                }
//--------------------------------------------------------------------------------------------------------------------
                gridLayoutManager =
                    GridLayoutManager(applicationContext, 3, LinearLayoutManager.VERTICAL, false)

                reccyclerViewMain = findViewById<RecyclerView>(R.id.main_recyclerView)
                reccyclerViewMain?.layoutManager = gridLayoutManager
                reccyclerViewMain?.setHasFixedSize(true)

                val query = db.collection("FEEDS")
                    .whereEqualTo("uid", mUser)
                query.get().addOnCompleteListener { t ->
                    if (t.isSuccessful) {
                        val FeedsItems = ArrayList<Feeds>()
                        for (d in t.result!!) {
                            val feeds = d.toObject(Feeds::class.java)
                            FeedsItems.add(feeds)
                        }
                        alphaAdapters = Main_Adapter_cycle(applicationContext, FeedsItems!!)
                        reccyclerViewMain?.adapter = alphaAdapters
                    }
                }
            } else {
            }
        } else if(!intent.hasExtra("touserId")){
//================================================================================================================================================
            val uid = FirebaseAuth.getInstance().currentUser?.uid
            val db = Firebase.firestore

            val docRef = db.collection("UserSegment").document("$uid")
            docRef.get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Document found in the offline cache
                    val document = task.result
                    if (document != null) {
                        val users = document.toObject<Users>()
                        val tv_name = findViewById<TextView>(R.id.tv_name)
                        val tv_status = findViewById<TextView>(R.id.tv_status)
                        val tv_Nickname = findViewById<TextView>(R.id.tv_Nickname)

                        tv_name.text = users?.name.toString()
                        tv_Nickname.text = users?.nickname.toString()
                        tv_status.text = users?.descreption.toString()
                        var profilePicture = users?.proFileImageUrl
                        Glide.with(this)
                            .load(profilePicture)
                            .placeholder(R.drawable.logo2)
                            .into(ImagePrewiew)

                        if (users != null) {
                            NameUser = users.name
                            var LogOutBtm = findViewById<TextView>(R.id.LogOutBtm)
                            LogOutBtm.visibility = View.VISIBLE
                            LogOutBtm.setOnClickListener {
                                showAlert()
                            }

                            val editprofile = findViewById<Button>(R.id.bt_editProfile)
                            editprofile.visibility = View.VISIBLE

                            editprofile.setOnClickListener {
                                val intent = Intent(this, EditProfile::class.java)
                                intent.putExtra("userdetails", users)
                                startActivity(intent)
                            }
                        }
                    }
                }
            }
//-------------------------------------------------------------------------------------------------------------------------
            gridLayoutManager =
                GridLayoutManager(applicationContext, 3, LinearLayoutManager.VERTICAL, false)

            reccyclerViewMain = findViewById<RecyclerView>(R.id.main_recyclerView)
            reccyclerViewMain?.layoutManager = gridLayoutManager
            reccyclerViewMain?.setHasFixedSize(true)
            ///////////////////////

            val query = db.collection("FEEDS")
                .whereEqualTo("uid", uid)
            query.get().addOnCompleteListener { t ->
                if (t.isSuccessful) {
                    val FeedsItems = ArrayList<Feeds>()
                    for (d in t.result!!) {
                        val feeds = d.toObject(Feeds::class.java)
                        FeedsItems.add(feeds)
                    }
                    alphaAdapters = Main_Adapter_cycle(applicationContext, FeedsItems!!)
                    reccyclerViewMain?.adapter = alphaAdapters
                }
            }
        }
    }

    //===================================A=L=E=R=T==L=O==G=O=U=T===========================================================================
    private fun showAlert() {
        lateinit var dialog: AlertDialog
        val alertDialog: AlertDialog.Builder = AlertDialog.Builder(this)
        alertDialog.setTitle("TeXt~Me")
        alertDialog.setMessage("Do you want to logout? ")

        val dilaogOnClickListener = DialogInterface.OnClickListener { _, which ->
            when (which) {
                DialogInterface.BUTTON_NEGATIVE -> Toast.makeText(
                    this,
                    "Dismissed",
                    Toast.LENGTH_SHORT
                ).show()
                DialogInterface.BUTTON_POSITIVE -> {
                    signOut()
                    Toast.makeText(this, "Logout Success", Toast.LENGTH_SHORT).show()
                }
            }
        }
        alertDialog.setPositiveButton("yes", dilaogOnClickListener)
        alertDialog.setNegativeButton("no", dilaogOnClickListener)
        dialog = alertDialog.create()
        dialog.show()
    }

    //===================================================================================================================================
    private fun signOut() {
        FirebaseAuth.getInstance().signOut()
        var intent = Intent(this, LoginActivity::class.java)

        intent.flags =
            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
    //=============================================================================================================================
}