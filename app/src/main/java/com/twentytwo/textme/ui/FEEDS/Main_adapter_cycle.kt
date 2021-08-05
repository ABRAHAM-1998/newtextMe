package com.twentytwo.textme.ui.FEEDS

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.twentytwo.textme.FirestoreClass
import com.twentytwo.textme.Model.Feeds
import com.twentytwo.textme.R
import java.util.*

class Main_Adapter_cycle(
    private val contex: Context,
    private val main_data_list: ArrayList<Feeds>
) : RecyclerView.Adapter<Main_Adapter_cycle.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var main_rec_img = itemView.findViewById<ImageView>(R.id.main_rec_logo)
        var deleteP = itemView.findViewById<Button>(R.id.deletePost)
        var progressBar = itemView.findViewById<ProgressBar>(R.id.progressBar)


    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(contex).inflate(R.layout.main_recycler_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val maindata: Feeds = main_data_list.get(position)
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        if (uid == maindata.uid) {
            holder.main_rec_img.setOnLongClickListener {
                holder.deleteP.visibility = View.VISIBLE
                return@setOnLongClickListener true
            }
            holder.deleteP.setOnClickListener {
                if (maindata.uid == uid) {
                    holder.progressBar.visibility = View.VISIBLE
                    FirestoreClass().DeleteFeed(maindata.uploadedTiem, maindata.imagePath, contex)
                }
            }
        }

        Glide.with(contex)
            .load(maindata.imagePath)
            .placeholder(R.drawable.logo2)
            .into(holder.main_rec_img)
    }


    override fun getItemCount(): Int {
        return main_data_list.size
    }
}