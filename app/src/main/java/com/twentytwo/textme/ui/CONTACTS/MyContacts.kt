package com.twentytwo.textme.ui.CONTACTS

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.twentytwo.textme.ACTIVITIES_SEC.DeveloperActivity
import com.twentytwo.textme.ACTIVITIES_SEC.FAQ
import com.twentytwo.textme.ACTIVITIES_SEC.LoginActivity
import com.twentytwo.textme.ACTIVITIES_SEC.ProfileActivity
import com.twentytwo.textme.Model.Users
import com.twentytwo.textme.R
import com.twentytwo.textme.RETROFIT.RetrofitClient
import com.twentytwo.textme.ui.CHATS.ChatActivity
import com.twentytwo.textme.ui.home.MyListAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class MyContacts : Fragment() {
    private val compositeDisposable = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_my_contacts, container, false)
        view.apply {
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

            val float_add_contact = findViewById<FloatingActionButton>(R.id.float_add_contact)
            float_add_contact.setOnClickListener {
                startActivity(Intent(context, ADD_CONTACTS::class.java))
            }
            val firebaseUser = FirebaseAuth.getInstance().currentUser
            if (firebaseUser != null) {
                val fromUid = firebaseUser.uid
                val rootRef = FirebaseFirestore.getInstance()
                val uidRef =
                    rootRef.collection("UserSegment").document(fromUid).collection("contacts")
                uidRef.get().addOnCompleteListener { t ->
                    if (t.isSuccessful) {
                        val listofids = ArrayList<String>()
                        for (d in t.result!!) {
                            listofids.add(d.id)
                        }
                        if (!listofids.isEmpty()) {
                            val uidRefernce = rootRef.collection("UserSegment")
                                .whereIn("uid", listofids)
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
                                            intent.putExtra("contact", "true")
                                            intent.putExtra("ChannelIds", "")

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
            startActivity(Intent(context, FAQ::class.java))

        }
        if (id == R.id.developer) {
            val intent = Intent(context, DeveloperActivity::class.java)
            startActivity(intent)
//            startActivity(Intent(context, LoginActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }
}