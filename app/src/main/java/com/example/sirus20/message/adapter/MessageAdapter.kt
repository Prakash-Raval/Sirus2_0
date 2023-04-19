package com.example.sirus20.message.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.sirus20.databinding.ItemMessageEndBinding
import com.example.sirus20.databinding.ItemMessageStartBinding
import com.example.sirus20.message.model.MessageModel
import com.google.firebase.auth.FirebaseAuth


class MessageAdapter(
    private var userList: ArrayList<MessageModel>
) : RecyclerView.Adapter<ViewHolder>() {

    private val layoutOne = 0 // start message
    private val layoutTwo = 1 // end message

    inner class ViewHolderStart(val binding: ItemMessageStartBinding) :
        ViewHolder(binding.root) {
        fun bind(position: Int) {
            Log.d("OnStart", "bind: ${userList[position].textMessage}")
            binding.textStartMessage.text = userList[position].textMessage
        }
    }

    inner class ViewHolderEnd(val binding: ItemMessageEndBinding) :
        ViewHolder(binding.root) {
        fun bind(position: Int) {
            Log.d("OnEnd", "bind: ${userList[position].textMessage}")
            binding.textEndMessage.text = userList[position].textMessage
        }

    }

    override fun getItemViewType(position: Int): Int {

        return if (FirebaseAuth.getInstance().currentUser?.uid == userList[position].textID) {
            layoutOne
        } else {
            layoutTwo
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return when (viewType) {
            layoutOne -> {
                ViewHolderStart(
                    ItemMessageStartBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            else -> {
                ViewHolderEnd(
                    ItemMessageEndBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }


    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (holder.javaClass == ViewHolderStart::class.java) {
            val viewHolderStart = holder as ViewHolderStart
            viewHolderStart.bind(position)
        } else {
            val viewHolderEnd = holder as ViewHolderEnd
            viewHolderEnd.bind(position)

        }
    }
}
