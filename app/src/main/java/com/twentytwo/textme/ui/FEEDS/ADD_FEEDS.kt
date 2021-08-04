package com.twentytwo.textme.ui.FEEDS

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.canhub.cropper.CropImage
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.twentytwo.textme.ACTIVITIES_SEC.ProfileActivity
import com.twentytwo.textme.FirestoreClass
import com.twentytwo.textme.Model.Feeds
import com.twentytwo.textme.Model.UsersReg
import com.twentytwo.textme.Model.certific
import com.twentytwo.textme.R
import java.text.SimpleDateFormat
import java.util.*

class ADD_FEEDS : AppCompatActivity() {
    private var filePath: Uri? = null
    internal var storage: FirebaseStorage? = null
    internal var storageReference: StorageReference? = null
    private val PICK_IMAGE_REQUEST = 1244
    private var NameUser: String = ""
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_feeds)

        storage = FirebaseStorage.getInstance()
        storageReference = storage!!.reference
        auth = FirebaseAuth.getInstance()


        val chooseBtnIDCARD = findViewById<Button>(R.id.chooseBtnIDCARD)
        chooseBtnIDCARD.setOnClickListener {
            showFileChoser()
        }
        val uploadBtnIDcards = findViewById<Button>(R.id.uploadBtnIDcards)
        uploadBtnIDcards.setOnClickListener {
            val desIDCARD = findViewById<EditText>(R.id.desIDCARD)
            val location_feeds = findViewById<EditText>(R.id.location_feeds)
            val userinfo = FirebaseAuth.getInstance().currentUser

            if (desIDCARD.text.isEmpty()) {
                desIDCARD.error = "DESCREPTION MUST"
                return@setOnClickListener
            } else if (location_feeds.text.isEmpty()) {
                location_feeds.error = "location must MUST"
                return@setOnClickListener
            } else if (userinfo != null) {
                if (userinfo.displayName.isNullOrEmpty()) {
                    setDisplayname()

                } else {
                    fileUPload()

                }
            }
        }
    }

    private fun setDisplayname() {
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        val db = Firebase.firestore

        val docRef = db.collection("USERDETAILS").document("$uid")
        docRef.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Document found in the offline cache
                val document = task.result
                if (document != null) {
                    val users = document.toObject<UsersReg>()

                    val user = auth.currentUser
                    val profileUpdates = UserProfileChangeRequest.Builder()
                        .setDisplayName(users?.name)
                        .build()

                    user!!.updateProfile(profileUpdates)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                fileUPload()
                            }
                        }
                        .addOnFailureListener {
                            Toast.makeText(this, "Displayname set failed", Toast.LENGTH_SHORT)
                                .show()
                        }

                }
            }
        }
        ///////////////////////////
    }

    @SuppressLint("SimpleDateFormat")
    private fun fileUPload() {
        val sdf = SimpleDateFormat("dd/M/yyy hh:mm a")
        val currentday = sdf.calendar.time.toString()

        val uid = FirebaseAuth.getInstance().currentUser!!.uid

        if (filePath != null) {
            val progressDialog = ProgressDialog(this)
            progressDialog.setTitle("Uploading")
            progressDialog.show()

            val imageRef =
                storageReference!!.child("FEEDS/" + UUID.randomUUID().toString() + ".jpeg")
            imageRef.putFile(filePath!!)
                .addOnSuccessListener { taskSnapshot ->
                    val names = taskSnapshot.metadata!!.name
                    val userinfo = FirebaseAuth.getInstance().currentUser

                    imageRef.getDownloadUrl().addOnSuccessListener(
                        OnSuccessListener<Uri> { uri ->
                            Log.d("TAG", "onSuccess: uri= $uri")

                            ////////////////////////////////////////////
                            val desIDCARD = findViewById<EditText>(R.id.desIDCARD)
                            val location_feeds = findViewById<EditText>(R.id.location_feeds)

                            val certdata =
                                Feeds(
                                    uid,
                                    uri.toString(),
                                    location_feeds.text.toString(),
                                    userinfo?.displayName.toString(),
                                    desIDCARD.text.toString(),
                                    Calendar.getInstance().time,
                                    userinfo?.photoUrl.toString()
                                )

                            if (certdata != null) {
                                FirestoreClass().createIdCards(this@ADD_FEEDS, certdata)
                            }
                            /////////////////////////////////////
                        })


                    progressDialog.dismiss()
                    Toast.makeText(
                        applicationContext,
                        "FILE UPLOADED SUCCESFULLY",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                .addOnFailureListener {
                    progressDialog.dismiss()
                    Toast.makeText(applicationContext, "FAILED TO UPLOAD", Toast.LENGTH_SHORT)
                        .show()
                }
                .addOnProgressListener { tasKSnapshot ->
                    val progress =
                        100.0 * tasKSnapshot.bytesTransferred / tasKSnapshot.totalByteCount
                    progressDialog.setMessage("Uploaded   " + progress.toInt() + "   %...")
                }

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (result != null) {
                val ImagePrewiew = findViewById<ImageView>(R.id.ImagePrewiew)
                ImagePrewiew.setImageURI(result.uriContent)
            }
//            val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, result?.uriContent)
            if (data != null) {
                if (result != null) {
                    filePath = result.uriContent
                }
            }
        }
    }

    private fun showFileChoser() {
        CropImage
            .activity()
            .setAspectRatio(1, 1)
            .setOutputCompressQuality(50)
            .setOutputCompressFormat(Bitmap.CompressFormat.JPEG)
            .setBorderLineColor(Color.RED)
            .setActivityTitle("NOTE BOX CROPPER")
            .setCropMenuCropButtonTitle("Save Image")
            .start(this)

//        val intent = Intent()
//        intent.type = "image/*"
//        intent.action = Intent.ACTION_GET_CONTENT
//        startActivityForResult(Intent.createChooser(intent, "SELECT PICTURE"), PICK_IMAGE_REQUEST)
    }

    fun certiSuccess() {
        val intent = Intent(this, ProfileActivity::class.java)
        intent.flags =
            Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)

        Toast.makeText(this, "SUCCESFULLY CRAEATED", Toast.LENGTH_SHORT).show()
    }

    fun Certifail() {
        Toast.makeText(this, "FAILED TO CREATE", Toast.LENGTH_SHORT).show()

    }


}