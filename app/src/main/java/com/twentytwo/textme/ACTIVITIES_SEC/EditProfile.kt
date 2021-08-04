package com.twentytwo.textme.ACTIVITIES_SEC

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.canhub.cropper.CropImage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.twentytwo.textme.Model.Users
import com.twentytwo.textme.R
import java.util.*

class EditProfile : AppCompatActivity() {
    private var filePath: Uri? = null
    internal var storage: FirebaseStorage? = null
    internal var storageReference: StorageReference? = null


    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        val mUser = intent.extras!!.get("userdetails") as Users
        val usrName = findViewById<EditText>(R.id.usrName)
        val usrNickname = findViewById<EditText>(R.id.userNickName)
        val usrStatus = findViewById<EditText>(R.id.usrAbout)
        usrName.setText(mUser.name)
        usrNickname.setText(mUser.nickname)
        usrStatus.setText(mUser.descreption)

        storage = FirebaseStorage.getInstance()
        storageReference = storage!!.reference
        auth = FirebaseAuth.getInstance()
        val UploadProfile = findViewById<Button>(R.id.UploadProfile)

        UploadProfile.setOnClickListener {
            if (usrName.text.isEmpty()) {
                usrName.error = "Name MUST"
                return@setOnClickListener
            } else if (usrName.text.length >= 20) {
                usrName.error = "nickname must lesthan  20 chars"
                return@setOnClickListener

            } else if (usrNickname.text.length >= 15) {
                usrNickname.error = "nickname must lesthan  15 chars"
                return@setOnClickListener

            } else if (usrStatus.text.length >= 65) {
                usrStatus.error = "nickname must lesthan  65 chars"
                return@setOnClickListener
            } else {


                fileUPload(mUser.proFileImageUrl)
            }
        }
        val ImagePrewiew: ImageView = findViewById(R.id.ImagePrewiew)

        var profilePicture = auth.currentUser?.photoUrl
        Glide.with(this)
            .load(profilePicture)
            .placeholder(R.drawable.logo2)
            .into(ImagePrewiew)

        val chagepic = findViewById<Button>(R.id.selectImage)
        chagepic.setOnClickListener {

            showFileChoser()
        }
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        val db = Firebase.firestore

    }

    private fun fileUPload(proFileImageUrl: String) {

        val usrName = findViewById<EditText>(R.id.usrName)
        val usrNickname = findViewById<EditText>(R.id.userNickName)
        val usrStatus = findViewById<EditText>(R.id.usrAbout)


        if (filePath != null) {
            val progressDialog = ProgressDialog(this)
            progressDialog.setTitle("Uploading")
            progressDialog.show()

            val storageRef = FirebaseStorage.getInstance().getReferenceFromUrl(proFileImageUrl)
            storageRef.delete()
                .addOnSuccessListener {
                    val imageRef = storageReference!!.child(
                        "ProfilePic/" + UUID.randomUUID().toString() + ".jpeg"
                    )
                    imageRef.putFile(filePath!!)
                        .addOnSuccessListener { taskSnapshot ->
                            imageRef.getDownloadUrl().addOnSuccessListener { uri ->
                                Log.d("TAG", "onSuccess: uri= $uri")
                                //////////////////////////////////////////// >
                                val user = auth.currentUser
                                val profileUpdates = UserProfileChangeRequest.Builder()
                                    .setPhotoUri(uri)
                                    .setDisplayName(usrName.text.toString())
                                    .build()

                                user!!.updateProfile(profileUpdates)
                                    .addOnCompleteListener { task ->
                                        if (task.isSuccessful) {
                                            val uid = FirebaseAuth.getInstance().currentUser?.uid
                                            val db = Firebase.firestore

                                            var data =
                                                Users(
                                                    user.uid,
                                                    uri.toString(),
                                                    usrName.text.toString(),
                                                    "",
                                                    usrStatus.text.toString(),
                                                    usrNickname.text.toString(),
                                                    mutableListOf()
                                                )
                                            val docRef =
                                                db.collection("UserSegment").document("$uid")
                                                    .set(data, SetOptions.merge())
                                                    .addOnSuccessListener {
                                                        Toast.makeText(
                                                            this,
                                                            "SUCCESS",
                                                            Toast.LENGTH_SHORT
                                                        ).show()
                                                        val intent = Intent(
                                                            this,
                                                            ProfileActivity::class.java
                                                        )
                                                        intent.flags =
                                                            Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                                                        startActivity(intent)
                                                    }
                                                    .addOnFailureListener {
                                                        Toast.makeText(
                                                            this,
                                                            "Faiilure",
                                                            Toast.LENGTH_SHORT
                                                        ).show()
                                                    }
                                        }
                                    }
                                /////////////////////////////////////
                            }
                            progressDialog.dismiss()
                            Toast.makeText(
                                applicationContext,
                                "FilE UPLOADED SUCCESFULLY",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        .addOnFailureListener {
                            progressDialog.dismiss()
                            Toast.makeText(
                                applicationContext,
                                "FAILED TO UOLOAD",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                        .addOnProgressListener { tasKSnapshot ->
                            val progress =
                                100.0 * tasKSnapshot.bytesTransferred / tasKSnapshot.totalByteCount
                            progressDialog.setMessage("Uploaded   " + progress.toInt() + "   %...")
                        }
                }
                .addOnFailureListener { e -> Log.w("TAG", "Error deleting document", e)
                    val imageRef = storageReference!!.child(
                        "ProfilePic/" + UUID.randomUUID().toString() + ".jpeg"
                    )
                    imageRef.putFile(filePath!!)
                        .addOnSuccessListener { taskSnapshot ->
                            imageRef.getDownloadUrl().addOnSuccessListener { uri ->
                                Log.d("TAG", "onSuccess: uri= $uri")
                                //////////////////////////////////////////// >
                                val user = auth.currentUser
                                val profileUpdates = UserProfileChangeRequest.Builder()
                                    .setPhotoUri(uri)
                                    .setDisplayName(usrName.text.toString())
                                    .build()

                                user!!.updateProfile(profileUpdates)
                                    .addOnCompleteListener { task ->
                                        if (task.isSuccessful) {
                                            val uid = FirebaseAuth.getInstance().currentUser?.uid
                                            val db = Firebase.firestore

                                            var data =
                                                Users(
                                                    user.uid,
                                                    uri.toString(),
                                                    usrName.text.toString(),
                                                    "",
                                                    usrStatus.text.toString(),
                                                    usrNickname.text.toString(),
                                                    mutableListOf()
                                                )
                                            val docRef =
                                                db.collection("UserSegment").document("$uid")
                                                    .set(data, SetOptions.merge())
                                                    .addOnSuccessListener {
                                                        Toast.makeText(
                                                            this,
                                                            "SUCCESS",
                                                            Toast.LENGTH_SHORT
                                                        ).show()
                                                        val intent = Intent(
                                                            this,
                                                            ProfileActivity::class.java
                                                        )
                                                        intent.flags =
                                                            Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                                                        startActivity(intent)
                                                    }
                                                    .addOnFailureListener {
                                                        Toast.makeText(
                                                            this,
                                                            "Faiilure",
                                                            Toast.LENGTH_SHORT
                                                        ).show()
                                                    }
                                        }
                                    }
                                /////////////////////////////////////
                            }
                            progressDialog.dismiss()
                            Toast.makeText(
                                applicationContext,
                                "FilE UPLOADED SUCCESFULLY",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        .addOnFailureListener {
                            progressDialog.dismiss()
                            Toast.makeText(
                                applicationContext,
                                "FAILED TO UOLOAD",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                        .addOnProgressListener { tasKSnapshot ->
                            val progress =
                                100.0 * tasKSnapshot.bytesTransferred / tasKSnapshot.totalByteCount
                            progressDialog.setMessage("Uploaded   " + progress.toInt() + "   %...")
                        }
                }

        } else {
            val progressDialog = ProgressDialog(this)
            progressDialog.setTitle("Uploading")
            progressDialog.show()
            val user = auth.currentUser
            val profileUpdates = UserProfileChangeRequest.Builder()
                .setDisplayName(usrName.text.toString())
                .build()

            user!!.updateProfile(profileUpdates)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val uid = FirebaseAuth.getInstance().currentUser?.uid
                        val db = Firebase.firestore

                        var data =
                            Users(
                                user.uid,
                                proFileImageUrl,
                                usrName.text.toString(),
                                "",
                                usrStatus.text.toString(),
                                usrNickname.text.toString(),
                                mutableListOf()
                            )
                        val docRef =
                            db.collection("UserSegment").document("$uid")
                                .set(data, SetOptions.merge())
                                .addOnSuccessListener {
                                    Toast.makeText(
                                        this,
                                        "SUCCESS",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    val intent = Intent(this, ProfileActivity::class.java)
                                    intent.flags =
                                        Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                                    startActivity(intent)
                                    progressDialog.dismiss()

                                }
                                .addOnFailureListener {
                                    Toast.makeText(
                                        this,
                                        "Faiilure",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                    }
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


    }
}