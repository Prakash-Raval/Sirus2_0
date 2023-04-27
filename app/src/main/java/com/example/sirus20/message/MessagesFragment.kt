package com.example.sirus20.message

import android.annotation.SuppressLint
import android.net.Uri
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
import com.example.sirus20.extension.setLocalImage
import com.example.sirus20.message.adapter.MessageAdapter
import com.example.sirus20.message.model.MessageModel
import com.example.sirus20.notification.model.NotificationData
import com.example.sirus20.notification.model.PushNotification
import com.example.sirus20.signup.model.SignUpModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

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
    private val args = this.arguments
    private val senderID = FirebaseAuth.getInstance().currentUser?.uid


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
                    Timber.d("SnapShot onDataChange: $snapshot")
                    list.clear()
                    for (post in snapshot.children) {
                        for (i in post.children) {
                            for (j in i.children) {
                                Timber.d("PostValue onDataChange: $j")
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
                    Timber.d("PostValue onDataChange: $list")
                    adapter.notifyDataSetChanged()
                    if (list.size > 0)
                        binding.rvMessage.smoothScrollToPosition(list.size - 1)
                }

                override fun onCancelled(error: DatabaseError) {
                    Timber.d("PostValue onCancelled: ${error.message}")
                }
            })


        Timber.d("MessageModelList setAdapter: $list")
        adapter = MessageAdapter(list)
        binding.rvMessage.adapter = adapter
    }


    /*
    * fun set up toolbar
    * */
    private fun setToolbar() {
        binding.inToolbar.imgBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.inToolbar.txtToolbarHeader.text = (args?.get("SIGNUPMODEL") as SignUpModel).name

        binding.inToolbar.imgProfile.visibility = View.VISIBLE
        binding.inToolbar.imgProfile.setLocalImage(
            Uri.parse((args.get("SIGNUPMODEL") as SignUpModel).image),
            true
        )
    }

    /*
    * creating id's for sender and receiver
    * */

    private fun createID() {
        val receiverID = (args?.get("SIGNUPMODEL") as SignUpModel).uid
        senderRoom = receiverID + senderID
        receiverRoom = senderID + receiverID
    }

    /*
    * send button click
    * */
    @SuppressLint("LogNotTimber")
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
                            val topic = (args?.get("SIGNUPMODEL") as SignUpModel).token

                            PushNotification(
                                to = topic,
                                NotificationData(
                                    title = (args.get("SIGNUPMODEL") as SignUpModel).name ?: "",
                                    body = binding.edtMessage.text.toString().trim()
                                )
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

    private fun sendNotification(notification: PushNotification) =
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitInstance.api.postNotification(notification)
                if (response.isSuccessful) {

                    Timber.d("TAG1 Response: $response")
                    //Log.d("TAG1", "Response: ${Gson().toJson(response)}")
                } else {
                    response.errorBody()?.string()?.let { Timber.e("TAG1 $it") }
                }
            } catch (e: Exception) {
                Timber.e("TAG1 $e")
            }
        }


}



