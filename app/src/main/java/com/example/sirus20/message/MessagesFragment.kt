package com.example.sirus20.message

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.sirus20.R
import com.example.sirus20.common.Validation
import com.example.sirus20.databinding.FragmentMessagesBinding
import com.example.sirus20.message.adapter.MessageAdapter
import com.example.sirus20.message.model.MessageModel
import com.example.sirus20.notification.model.NotificationData
import com.example.sirus20.notification.model.PushNotification
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MessagesFragment : Fragment() {

    /*
    * variables
    * */
    private lateinit var binding: FragmentMessagesBinding
    private lateinit var adapter: MessageAdapter
    private var list = ArrayList<MessageModel>()
    private lateinit var dbReference: DatabaseReference
    private var senderRoom: String? = ""
    private var receiverRoom: String? = ""
    private val senderID = FirebaseAuth.getInstance().currentUser?.uid
//    private val receiverID = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.fragment_messages,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*
        * method calls
        * */
        dbReference = FirebaseDatabase.getInstance().reference
        setAdapter()
        sendMessage()
        setToolbar()
        createID()
    }


    /*
    * set adapter
    * adding data to list via database
    * */
    private fun setAdapter() {

        dbReference.child("CHAT")
            .child(senderRoom ?: "")
            .addValueEventListener(object : ValueEventListener {
                @SuppressLint("NotifyDataSetChanged")
                override fun onDataChange(snapshot: DataSnapshot) {
                    Log.d("SnapShot", "onDataChange: $snapshot")
                    list.clear()
                    for (post in snapshot.children) {
                        for (i in post.children) {
                            for (j in i.children) {
                                Log.d("PostValue", "onDataChange: $j")
                                val msg = j.getValue(MessageModel::class.java)
                                if (msg != null) {
                                    list.add(msg)
                                    list.sortBy {
                                        it.time
                                    }
                                }
                            }
                        }

                    }
                    Log.d("PostValue", "onDataChange: $list")
                    adapter.notifyDataSetChanged()
                    if (list.size > 0)
                        binding.rvMessage.smoothScrollToPosition(list.size - 1)
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.d("PostValue", "onCancelled: ${error.message}")

                }

            })


        Log.d("MessageModelList", "setAdapter: $list")
        adapter = MessageAdapter(list)
        binding.rvMessage.adapter = adapter
    }


    /*
    * fun set up toolbar
    * */
    private fun setToolbar() {
        val args = this.arguments
        binding.inToolbar.imgBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.inToolbar.txtToolbarHeader.text = args?.getString("NAME")
    }

    /*
    * creating id's for sender and receiver
    * */

    private fun createID() {
        val args = this.arguments
        val receiverID = args?.getString("UID")
        senderRoom = receiverID + senderID
        receiverRoom = senderID + receiverID
    }

    /*
    * send button click
    * */
    private fun sendMessage() {
        binding.imgSend.setOnClickListener {
            if (!Validation.isNotNull(binding.edtMessage.text.toString().trim())) {
                Toast.makeText(requireContext(), "Enter message", Toast.LENGTH_SHORT).show()
            } else {
                /*
                * adding messages to data base
                * */
                val messageModel = MessageModel(
                    binding.edtMessage.text.toString().trim(),
                    senderID,
                    System.currentTimeMillis().toString()
                )

                /*creating node for data base chat
                * second child as sender room
                * third child as messages
                * */
                senderRoom?.let { it1 ->
                    dbReference.child("CHAT")
                        .child(it1)
                        .child("Messages")
                        .push()
                        .setValue(messageModel)
                        .addOnSuccessListener {
                            val args = this.arguments
                            val topic = args?.getString("TOKEN")
                            args?.getString("NAME")?.let { it2 ->
                                NotificationData(
                                    it2,
                                    binding.edtMessage.text.toString().trim()
                                )
                            }?.let { it3 ->
                                PushNotification(
                                    to = topic,
                                    it3
                                ).also {
                                    sendNotification(it)
                                    binding.edtMessage.setText("")
                                    Log.d("TAG12", "sendMessage: $it")
                                }
                            }
                        }
                }
            }
        }
    }

    private fun sendNotification(notification: PushNotification) =
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitInstance.api.postNotification(notification)
                if (response.isSuccessful) {

                    Log.d("TAG1", "Response: $response")
                    //Log.d("TAG1", "Response: ${Gson().toJson(response)}")
                } else {
                    response.errorBody()?.string()?.let { Log.e("TAG1", it) }
                }
            } catch (e: Exception) {
                Log.e("TAG1", e.toString())
            }
        }
}