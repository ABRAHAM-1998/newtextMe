package com.twentytwo.textme.ui.FEEDS

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.twentytwo.textme.ACTIVITIES_SEC.DeveloperActivity
import com.twentytwo.textme.ACTIVITIES_SEC.FAQ
import com.twentytwo.textme.ACTIVITIES_SEC.LoginActivity
import com.twentytwo.textme.ACTIVITIES_SEC.ProfileActivity
import com.twentytwo.textme.Model.Feeds
import com.twentytwo.textme.R
import com.twentytwo.textme.ui.CONTACTS.ADD_CONTACTS
import com.twentytwo.textme.ui.home.FeedsAdapter


class FragmentFeeds : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_feeds, container, false)
        view.apply {
            val float_add_feeds = findViewById<FloatingActionButton>(R.id.float_add_feeds)
            float_add_feeds.setOnClickListener {
                startActivity(Intent(context, ADD_FEEDS::class.java))
            }


            ///////////////////////
            val uid = FirebaseAuth.getInstance().currentUser?.uid
            val db = Firebase.firestore

            val query = db.collection("FEEDS")
                .orderBy("uploadedTiem", Query.Direction.DESCENDING)
//                .whereEqualTo("id", uid)
            query.get().addOnCompleteListener { t ->
                if (t.isSuccessful) {
                    val FeedsItems = ArrayList<Feeds>()
                    for (d in t.result!!) {
                        val feeds = d.toObject(Feeds::class.java)
                        FeedsItems.add(feeds)
                    }
                    var list_viw = findViewById<ListView>(R.id.list_viw)
                    list_viw.adapter = FeedsAdapter(context, FeedsItems)

                }
            }


        }
        return view
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

