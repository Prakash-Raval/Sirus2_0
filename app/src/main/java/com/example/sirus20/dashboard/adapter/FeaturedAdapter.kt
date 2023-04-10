package com.example.sirus20.dashboard.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sirus20.R
import com.example.sirus20.dashboard.modals.FeaturedData


class FeaturedAdapter(
    private val userList: ArrayList<FeaturedData>
) :
    RecyclerView.Adapter<FeaturedAdapter.MyViewHolder>() {

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var fetimage: ImageView = view.findViewById(R.id.featured_image)
        var fetTitle: TextView = view.findViewById(R.id.featured_title)
        var fetRating: RatingBar = view.findViewById(R.id.featured_rating)
        var fetDesc: TextView = view.findViewById(R.id.featured_desc)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_featured_card, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.fetimage.setImageResource(userList[position].image)
        holder.fetTitle.text = userList[position].name
        holder.fetDesc.text = userList[position].desc
        holder.fetRating.rating = userList[position].rating
    }

    override fun getItemCount(): Int {
        return userList.size
    }
}