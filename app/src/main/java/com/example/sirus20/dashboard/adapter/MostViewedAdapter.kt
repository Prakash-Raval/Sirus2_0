package com.example.sirus20.dashboard.adapter

import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sirus20.R
import com.example.sirus20.addplace.model.PlaceDataModel


class MostViewedAdapter(
    private val userList: ArrayList<PlaceDataModel>
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
        Log.d("T12A22G22", "onBindViewHolder: $userList")

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.image.setImageURI(Uri.parse(userList[position].placeImage))
        holder.title.text = userList[position].placeName
        holder.desc.text = userList[position].placeDesc
        holder.rating.rating = userList[position].placeRating?.toFloat()!!

        Log.d("T12A22G22", "onBindViewHolder: $userList")
    }

    override fun getItemCount(): Int {
        return userList.size
    }
}