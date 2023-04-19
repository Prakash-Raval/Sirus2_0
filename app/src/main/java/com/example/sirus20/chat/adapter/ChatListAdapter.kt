package com.example.sirus20.chat.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sirus20.chat.listner.OnChatClick
import com.example.sirus20.databinding.ItemChatLayoutBinding
import com.example.sirus20.signup.model.SignUpModel
import com.google.firebase.auth.FirebaseAuth


class ChatListAdapter(
    private var userList: ArrayList<SignUpModel>,
    private var onChatClick: OnChatClick
) :
    RecyclerView.Adapter<ChatListAdapter.MyViewHolder>() {


    inner class MyViewHolder(val binding: ItemChatLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.data = userList[position]
            binding.root.setOnClickListener {
                userList[position].name?.let { it1 ->
                    userList[position].uid?.let { it2 ->
                        userList[position].token?.let { it3 ->
                            onChatClick.onClick(
                                it1,
                                it2, token = it3
                            )
                        }
                    }
                }
            }
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ItemChatLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return userList.size
    }


}