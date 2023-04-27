package com.example.sirus20.user.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sirus20.databinding.ItemRecyclerviewBinding
import com.example.sirus20.user.listner.OnProductClick
import com.example.sirus20.user.model.ProductModel

class Adapter(private var list: ArrayList<ProductModel>, private var onProductClick: OnProductClick) :
    RecyclerView.Adapter<Adapter.ViewHolder>() {



    inner class ViewHolder(private val binding: ItemRecyclerviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.data = list[position]
            binding.root.setOnClickListener {
                onProductClick.onClick(list[position])
            }

        }
    }


    override fun getItemId(position: Int): Long {
        return getItemId(position)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemRecyclerviewBinding =
            ItemRecyclerviewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }


}