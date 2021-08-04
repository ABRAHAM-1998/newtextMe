package com.twentytwo.textme.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import com.twentytwo.textme.ACTIVITIES_SEC.DeveloperActivity
import com.twentytwo.textme.ACTIVITIES_SEC.LoginActivity
import com.twentytwo.textme.ACTIVITIES_SEC.ProfileActivity
import com.twentytwo.textme.Model.Users
import com.twentytwo.textme.Model.UsersChats
import com.twentytwo.textme.R
import com.twentytwo.textme.RETROFIT.RetrofitClient
import com.twentytwo.textme.ui.CHATS.ChatActivity
import com.twentytwo.textme.ui.CONTACTS.ADD_CONTACTS
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class ChatsHome : Fragment() {
    private var myRef: DatabaseReference? = null
    private var mFirebaseDatabase: FirebaseDatabase? = null

    private var firebaseAuth: FirebaseAuth? = null
    private var authStateListener: FirebaseAuth.AuthStateListener? = null
    private val compositeDisposable = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Timer().scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                Log.i("tag", "A Kiss every 5 seconds")
                //make the network call to retrieve all the movies
                val uid = FirebaseAuth.getInstance().currentUser?.uid.toString()


                val date = Calendar.getInstance().time
                val sdf = SimpleDateFormat("dd-MMM hh:mm a")
                val formatedDate = sdf.format(date)
                compositeDisposable.add(
                    RetrofitClient.instance.LastSceeen(uid!!,formatedDate.toString(),uid)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe({ response ->
                            Log.d("TAG", "onCreate: ${response.message}")

                        }, { t ->

                            Log.d("TAG", "onCreate: $t")
                        })
                )
            }
        }, 0, 5000)


        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_chats_home, container, false)
        view.apply {


            this@ChatsHome.authStateListener?.let { firebaseAuth!!.addAuthStateListener(it) }

            val firebaseUser = FirebaseAuth.getInstance().currentUser

            if (firebaseUser != null) {
                val fromUid = firebaseUser.uid
                val rootRef = FirebaseFirestore.getInstance()
                val uidRef = rootRef.collection("UserSegment").document(fromUid)
                    .collection("engagedChatChannels")
                uidRef.get().addOnCompleteListener { t ->
                    if (t.isSuccessful) {
                        val listids = ArrayList<String>()
                        val ChannelIds = ArrayList<String>()

                        for (d in t.result!!) {
                            listids.add(d.id)
                            var userQ = d.toObject(UsersChats::class.java)
                            ChannelIds.add(userQ.channelId)

                        }
                        if (listids.isNotEmpty()) {
                            val uidRefernce = rootRef.collection("UserSegment")
                                .whereIn("uid", listids)
                            uidRefernce.get().addOnCompleteListener { t ->
                                if (t.isSuccessful) {
                                    val listUsers = ArrayList<Users>()
                                    for (d in t.result!!) {
                                        val toUser = d.toObject(Users::class.java)
                                        listUsers.add(toUser)
                                    }
                                    var list_viw = findViewById<ListView>(R.id.list_viw)
                                    list_viw.adapter = MyListAdapter(context, listUsers)
                                    list_viw.onItemClickListener =
                                        AdapterView.OnItemClickListener { _, _, position, _ ->
                                            val intent = Intent(context, ChatActivity::class.java)
                                            intent.putExtra("toUser", listUsers[position])
                                            intent.putExtra("ChannelIds", ChannelIds[position])


                                            startActivity(intent)
                                        }


                                }

                            }
                        }
                    }
                }
            }
            return view
        }
    }

    //enable options menu in this fragment
    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    //inflate the menu
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater!!.inflate(R.menu.main_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    //handle item clicks of menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //get item id to handle item clicks
        val id = item!!.itemId
        //handle item clicks
        if (id == R.id.profile) {
            //do your action here, im just showing toast
            startActivity(Intent(context, ProfileActivity::class.java))
        }
        if (id == R.id.addcontact) {
            val intent = Intent(context, ADD_CONTACTS::class.java)
            startActivity(intent)
//            startActivity(Intent(context, LoginActivity::class.java))
        }
        if (id == R.id.settings) {

            Toast.makeText(context, "sorry, COMMING SOON", Toast.LENGTH_SHORT).show()

//            startActivity(Intent(context, LoginActivity::class.java))
        }
        if (id == R.id.developer) {
            val intent = Intent(context, DeveloperActivity::class.java)
            startActivity(intent)
//            startActivity(Intent(context, LoginActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

}
