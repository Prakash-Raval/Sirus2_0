package com.example.sirus20.dashboard.adapter

import android.annotation.SuppressLint
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.sirus20.addplace.model.PlaceDataModel
import com.example.sirus20.dashboard.listner.OnCellClicked
import com.example.sirus20.databinding.ItemMostViewCardBinding


class MostViewedAdapter(
    val onCellClicked: OnCellClicked,
    private var userList: ArrayList<PlaceDataModel>
) :
    RecyclerView.Adapter<MostViewedAdapter.MyViewHolder>(), Filterable {

    var list = ArrayList<PlaceDataModel>()

    init {
        list = userList
    }
    inner class MyViewHolder(val binding: ItemMostViewCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.data = list[position]
            binding.root.setOnClickListener {
                onCellClicked.onClick(list[position])
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
        holder.binding.mvImage.setImageURI(Uri.parse(list[position].placeImage))
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {

                val charSequence = constraint.toString()
                list = if (charSequence.isEmpty()) {
                    userList
                } else {
                    val resultList = ArrayList<PlaceDataModel>()
                    for (row in list) {
                        if (row.placeName?.lowercase()
                                ?.contains(constraint.toString().lowercase()) == true
                        ) {
                            resultList.add(row)
                        }
                    }
                    resultList
                }
                val results = FilterResults()
                results.values = list
                return results
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                list = results?.values as ArrayList<PlaceDataModel>
                notifyDataSetChanged()
            }
        }
    }

}