package com.example.sirus20.dashboard.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sirus20.R
import com.example.sirus20.dashboard.modals.MostViewedData


class MostViewedAdapter(
    private val userList: ArrayList<MostViewedData>
) :
    RecyclerView.Adapter<MostViewedAdapter.MyViewHolder>() {

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var image: ImageView = view.findViewById(R.id.mv_image)
        var title: TextView = view.findViewById(R.id.mv_title)
        var desc: TextView = view.findViewById(R.id.mv_desc)
        var rating: RatingBar = view.findViewById(R.id.mv_rating)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_most_view_card, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.image.setImageResource(userList[position].image)
        holder.title.text = userList[position].title
        holder.desc.text = userList[position].desc
        holder.rating.rating = userList[position].rating


    }

    override fun getItemCount(): Int {
        return userList.size
    }
}