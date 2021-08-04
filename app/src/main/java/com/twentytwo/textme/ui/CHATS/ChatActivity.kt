package com.twentytwo.textme.ui.CHATS

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.widget.*
import androidx.annotation.WorkerThread
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.twentytwo.textme.FirestoreClass
import com.twentytwo.textme.Model.*
import com.twentytwo.textme.R
import com.twentytwo.textme.RETROFIT.SocketHandler
import com.twentytwo.textme.StorageUtil
import com.twentytwo.textme.webrtccall.Constants
import com.twentytwo.textme.webrtccall.RTCActivity
import io.ktor.util.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*
import android.widget.Toast
import com.twentytwo.textme.ACTIVITIES_SEC.DeveloperActivity
import com.twentytwo.textme.ACTIVITIES_SEC.EditProfile
import com.twentytwo.textme.ACTIVITIES_SEC.ProfileActivity
import com.twentytwo.textme.RETROFIT.RetrofitClient
import com.twentytwo.textme.RETROFIT.defaultresponse
import com.twentytwo.textme.RETROFIT.result
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


private const val RC_SELECT_IMAGE = 2

class ChatActivity : AppCompatActivity() {
    //=========================================//REAL TIME DATABASE INGITTO
    private lateinit var mDatabase: DatabaseReference

    //===========================================//
    private var rootRef: FirebaseFirestore? = null
    private var fromUid: String? = ""
    private var adapter: MessageAdapter? = null

    private lateinit var currentChannelId: String
//    private lateinit var intentchannelId: String

    private lateinit var toUid: String
    //create an instance of the composite disposable
    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
//==========================================================================
        val chatshome = findViewById<LinearLayout>(R.id.chatshome)
        chatshome.setBackgroundResource(R.drawable.wall2)


        rootRef = FirebaseFirestore.getInstance()
        fromUid = FirebaseAuth.getInstance().currentUser!!.uid

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val toUser = intent.extras!!.get("toUser") as Users
        toUid = toUser.uid
        title = toUser.name
//        intentchannelId = intent.extras!!.get("ChannelIds") as String

        //=================================================
        Timer().scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                Log.i("tag", "A Kiss every 5 seconds")
                //make the network call to retrieve all the movies
                val date = Calendar.getInstance().time

                val sdf = SimpleDateFormat("dd-MMM hh:mm a")
                val formatedDate = sdf.format(date)
                compositeDisposable.add(
                    RetrofitClient.instance.LastSceeen(fromUid!!,formatedDate.toString(),toUid)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe({ response ->
                            Log.d("TAG", "onCreate: ${response.message}")
                            if(formatedDate.toString() == response.message){
                                supportActionBar!!.subtitle = "online"
                            }else{
                                supportActionBar!!.subtitle = "L-seen: ${response.message}"
                            }

                        }, { t ->

                            Log.d("TAG", "onCreate: $t")
                        })
                )
            }
        }, 0, 5000)





        //====================
//        FirestoreClass().getStatus(toUid, intentchannelId) {
//            supportActionBar!!.subtitle = it
//        }
//================================================================================================================
        FirestoreClass().getOrCreateChatChannel(toUid) { channelId ->
            currentChannelId = channelId


            //////////////////////////////////////////////////////////////////////////////////////////
            var button = findViewById<FloatingActionButton>(R.id.voiceRecordingOrSend)
            val edit_text = findViewById<EditText>(R.id.edit_text)
            //////////////////////////////////////////////////////////////////////////////////////////

            edit_text.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable)
                {

                }
                override fun beforeTextChanged(
                    s: CharSequence, start: Int,
                    count: Int, after: Int
                ) {

                }
                override fun onTextChanged(
                    s: CharSequence, start: Int,
                    before: Int, count: Int
                ) {

                }
            })
            //================================
            button.setOnClickListener {
                var messageText = edit_text.text.toString()
                val messageToSend =
                    FirebaseAuth.getInstance().currentUser?.displayName?.let {
                        TextMessage(
                            "",
                            messageText,
                            Calendar.getInstance().time,
                            fromUid!!,
                            toUid,
                            "",
                            "",
                            0
                        )
                    }
                edit_text.text.clear()
                if (messageText.isNotEmpty()) {
                    FirestoreClass().sendMessage(messageToSend, channelId)
                }
            } ///////////////////////////////////////////////////////////////////////////////////
        }
//================================================================================================================================
        val fab_send_image = findViewById<ImageView>(R.id.addAttachment)
        fab_send_image.setOnClickListener {
            val intent = Intent().apply {
                type = "image/*"
                action = Intent.ACTION_GET_CONTENT
                putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("image/jpeg", "image/png"))
            }
            startActivityForResult(Intent.createChooser(intent, "Select Image"), RC_SELECT_IMAGE)
        }
        //==========================================================================================================

        val handler = Handler()
        handler.postDelayed({
            if (this::currentChannelId.isInitialized) {
                // do something after 1000ms
                val query =
                    rootRef!!.collection("chatChannels").document(currentChannelId)
                        .collection("messages")
                        .limitToLast(50)
                        .orderBy("time", Query.Direction.ASCENDING)

                val options =
                    FirestoreRecyclerOptions.Builder<TextMessage>()
                        .setQuery(query, TextMessage::class.java)
                        .build()
                adapter = MessageAdapter(options)

                val recycler_view = findViewById<RecyclerView>(R.id.recycler_view)
                recycler_view.adapter = adapter

                recycler_view.layoutManager = LinearLayoutManager(this@ChatActivity)

                val itemTouchHelperCallback =
                    object :
                        ItemTouchHelper.SimpleCallback(
                            0,
                            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
                        ) {
                        override fun onMove(
                            recyclerView: RecyclerView,
                            viewHolder: RecyclerView.ViewHolder,
                            target: RecyclerView.ViewHolder
                        ) = false

                        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                            val position = viewHolder.adapterPosition
                            Snackbar.make(
                                chatshome, // The ID of your coordinator_layout
                                getString(R.string.messagedelete),
                                Snackbar.LENGTH_LONG
                            ).apply {
                                setAction("Deleted") {
                                    // If you're not using LiveData you might need to tell the adapter
                                    // that an item was inserted: notifyItemInserted(position);
                                    recycler_view.scrollToPosition(position)
                                }
                                setActionTextColor(Color.RED)
                            }.show()


                        }
                    }
                ItemTouchHelper(itemTouchHelperCallback).apply {
                    attachToRecyclerView(recycler_view)
                }
            }
        }, 500)
    }


    //=========================================================================================RUNNERRRR============

    //===================================================================================================

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SELECT_IMAGE && resultCode == Activity.RESULT_OK &&
            data != null && data.data != null
        ) {
            val selectedImagePath = data.data

            val selectedImageBmp =
                MediaStore.Images.Media.getBitmap(contentResolver, selectedImagePath)

            val outputStream = ByteArrayOutputStream()

            selectedImageBmp.compress(Bitmap.CompressFormat.JPEG, 10, outputStream)
            val selectedImageBytes = outputStream.toByteArray()

            StorageUtil.uploadMessageImage(selectedImageBytes) { imagePath ->
                val messageToSend =
                    fromUid?.let {
                        TextMessage(
                            imagePath, "", Calendar.getInstance().time, it, toUid, "fgfd", "", 0
                        )
                    }
                FirestoreClass().sendMessage(messageToSend, currentChannelId)
            }
        }
    }


    inner class MessageViewHolder internal constructor(private val view: View) :

        RecyclerView.ViewHolder(view) {
        @SuppressLint("SimpleDateFormat")
        @KtorExperimentalAPI
        internal fun setMessage(message: TextMessage) {
            if (!message.text.isEmpty() && message.senderId != fromUid) {
                val textView = view.findViewById<TextView>(R.id.text_view)
                textView.text = message.text
                val message_root = view.findViewById<RelativeLayout>(R.id.message_root)
                message_root.setBackgroundResource(R.drawable.message_to)
            }

            if (message.imagePath.isNotEmpty()) {
                val imageView = view.findViewById<ImageView>(R.id.imageView)
                Glide.with(this@ChatActivity)
                    .load(message.imagePath)
                    .into(imageView)
            }
            if (!message.text.isEmpty() && message.type == "video" && message.senderId == toUid) {
                val textView = view.findViewById<TextView>(R.id.text_view)
                val message_root = view.findViewById<RelativeLayout>(R.id.message_root)
                textView.text = "INCOMMING CALL ACCEPT"
                message_root.setBackgroundResource(R.drawable.message_to_call)
                textView.setOnClickListener {
                    joinVideoCall(message.senderName)
                }
            } else if (!message.text.isEmpty() && message.type == "video" && message.senderId == fromUid) {
                val textView = view.findViewById<TextView>(R.id.text_view)
                textView.text = "Outgoing Video Call"
                textView.setTextColor(Color.CYAN)

            } else if (!message.text.isEmpty()) {
                val textView = view.findViewById<TextView>(R.id.text_view)
                textView.text = message.text
                textView.setTextColor(Color.WHITE)
                if (message.senderId === toUid) {
                    val message_root = findViewById<RelativeLayout>(R.id.message_root)
                    message_root.setBackgroundResource(R.drawable.message_to)

                }
            }
            if (message.seen == 1 && message.senderId == fromUid) {
                val tickread = view.findViewById<ImageView>(R.id.tickread)
                tickread.visibility = View.VISIBLE
                val textTime = view.findViewById<TextView>(R.id.textTime)
                val sdf = SimpleDateFormat("dd-MMM hh:mm a")
                val formatedDate = sdf.format(message.time)
                textTime.text = formatedDate

            } else if (message.seen == 0 && message.senderId == fromUid) {
                val tickread = view.findViewById<ImageView>(R.id.tickread)
                tickread.visibility = View.INVISIBLE
                val textTime = view.findViewById<TextView>(R.id.textTime)
                val sdf = SimpleDateFormat("dd-MMM hh:mm a")
                val formatedDate = sdf.format(message.time)
                textTime.text = formatedDate
            } else {
                val textTime = view.findViewById<TextView>(R.id.textTime)
                val sdf = SimpleDateFormat("dd-MMM hh:mm a")
                val formatedDate = sdf.format(message.time)
                textTime.text = formatedDate

            }
        }
    }

    @KtorExperimentalAPI
    private fun joinVideoCall(senderName: String) {

        if (senderName.isEmpty()) {
            Toast.makeText(this, "Failed to ACCEPT CAll", Toast.LENGTH_SHORT).show()
        } else {
            val intent = Intent(this, RTCActivity::class.java)
            intent.putExtra("meetingID", senderName)
            intent.putExtra("isJoin", true)
            startActivity(intent)
        }

    }

    inner class MessageAdapter internal constructor(options: FirestoreRecyclerOptions<TextMessage>) :
        FirestoreRecyclerAdapter<TextMessage, MessageViewHolder>(options) {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
            if (viewType == R.layout.item_message_to) {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_message_to, parent, false)
                return MessageViewHolder(view)
            } else if (viewType == R.layout.item_message_from) {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_message_from, parent, false)
                return MessageViewHolder(view)

            } else if (viewType == R.layout.item_message_from_image) {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_message_from_image, parent, false)
                return MessageViewHolder(view)

            } else {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_message_to_image, parent, false)
                return MessageViewHolder(view)
            }
        }

        override fun onBindViewHolder(
            holder: MessageViewHolder,
            position: Int,
            model: TextMessage
        ) {
            holder.setMessage(model)
        }
///////////////////////////////////////////////////////////////////////////////////////////////

        override fun getItemViewType(position: Int): Int {
            if (fromUid != getItem(position).senderId && getItem(position).imagePath.isEmpty()) {
                return R.layout.item_message_to
            } else if (fromUid == getItem(position).senderId && getItem(position).imagePath.isEmpty()) {
                return R.layout.item_message_from
            } else if (fromUid == getItem(position).senderId && getItem(position).imagePath.isNotEmpty()) {
                return R.layout.item_message_from_image
            } else {
                return R.layout.item_message_to_image
            }

        }

        override fun onDataChanged() {
            val recycler_view = findViewById<RecyclerView>(R.id.recycler_view)


            recycler_view.layoutManager?.scrollToPosition(itemCount - 1)
            val messageCoolect = rootRef?.collection("chatChannels")?.document(currentChannelId)
                ?.collection("messages")


            messageCoolect?.whereEqualTo("recipientId", fromUid)?.get()
                ?.addOnCompleteListener { t ->
                    if (t.isSuccessful) {
                        val msgModelLIst = ArrayList<TextMessage>()
                        for (d in t.result!!) {
                            val msgList = d.toObject(TextMessage::class.java)
                            msgList.senderName =d.id
                            msgModelLIst.add(msgList)
//                            messageCoolect.document(d.id).update("seen", 1)
                        }
                        if (!msgModelLIst.isEmpty()) {
                            for (ss in msgModelLIst){
                                if (ss.seen == 0) {
                                    messageCoolect.document(ss.senderName).update("seen", 1)

                                }
                            }

                        }

                    }
                }
        }
    }


    override fun onStart() {
        if (adapter != null) {

        }
        super.onStart()
        val handler = Handler()
        handler.postDelayed({
            if (adapter != null) {
                adapter!!.startListening()

            }
        }, 500)
    }

    override fun onStop() {
        super.onStop()
        if (adapter != null) {
            adapter!!.stopListening()
        }
    }

    //creatiing Menu itemsas
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_chat, menu)
        return true
    }

    @ExperimentalCoroutinesApi
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId;
        if (id == R.id.logout_tile) {
            startVideoCall()
            return true
        } else if (id == R.id.note_profile && this::toUid.isInitialized) {
            val intent = Intent(this, ProfileActivity::class.java)
            intent.putExtra("touserId", toUid)
            startActivity(intent)
            return true
        }else if (id == R.id.developerid) {
            startActivity(Intent(this, DeveloperActivity::class.java))
            return true
        }

        return true
    }


    @ExperimentalCoroutinesApi
    @KtorExperimentalAPI
    private fun startVideoCall() {
        val db = Firebase.firestore
        Constants.isIntiatedNow = true
        Constants.isCallEnded = true

        val meeting_id = (Calendar.getInstance().time).toString()
        if (meeting_id.isEmpty())
            Toast.makeText(this, "ERROR UNABLE TO CREATE CALL", Toast.LENGTH_SHORT).show()
        else {
            db.collection("calls")
                .document(meeting_id)
                .get()
                .addOnSuccessListener {
                    if (it["type"] == "OFFER" || it["type"] == "ANSWER" || it["type"] == "END_CALL") {
                        Toast.makeText(this, "PLEASE TRY LATER", Toast.LENGTH_SHORT).show()
                    } else {
                        sendVideoCannelId(it.id)
                        val intent = Intent(this, RTCActivity::class.java)
                        intent.putExtra("meetingID", it.id)
                        intent.putExtra("isJoin", false)
                        startActivity(intent)
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(this, "FAILED TO CREATE MEETING", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun sendVideoCannelId(id: String) {
        val messageToSend =
            FirebaseAuth.getInstance().currentUser?.displayName?.let {
                TextMessage(
                    "",
                    "ACCEPT",
                    Calendar.getInstance().time,
                    fromUid!!,
                    toUid,
                    id,
                    "video",
                    0
                )
            }
        FirestoreClass().sendMessage(messageToSend, currentChannelId)
    }

}
