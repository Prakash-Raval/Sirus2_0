package com.example.sirus20.dashboard.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sirus20.addplace.model.PlaceDataModel
import com.example.sirus20.dashboard.listner.OnCellClicked
import com.example.sirus20.databinding.ItemMostViewCardBinding


class MostViewedAdapter(
    val onCellClicked: OnCellClicked,
    private val userList: ArrayList<PlaceDataModel>
) :
    RecyclerView.Adapter<MostViewedAdapter.MyViewHolder>() {


    inner class MyViewHolder(val binding: ItemMostViewCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.data = userList[position]
            binding.root.setOnClickListener {
                onCellClicked.onClick(userList[position])
            }
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ItemMostViewCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.mvImage.setImageURI(Uri.parse(userList[position].placeImage))
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return userList.size
    }
}