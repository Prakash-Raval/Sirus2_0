package com.example.sirus20.message

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.fragment.findNavController
import com.example.sirus20.R
import com.example.sirus20.common.Validation
import com.example.sirus20.databinding.FragmentMessagesBinding
import com.example.sirus20.extension.setLocalImage
import com.example.sirus20.message.adapter.MessageAdapter
import com.example.sirus20.message.model.MessageModel
import com.example.sirus20.notification.model.NotificationData
import com.example.sirus20.notification.model.PushNotification
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jitsi.meet.sdk.*
import timber.log.Timber
import java.net.MalformedURLException
import java.net.URL

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

    private val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            onBroadcastReceived(intent)
        }
    }

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
        // Initialize default options for Jitsi Meet conferences.
        val serverURL: URL = try {
            // When using JaaS, replace "https://meet.jit.si" with the proper serverURL
            URL("https://meet.jit.si")
        } catch (e: MalformedURLException) {
            e.printStackTrace()
            throw RuntimeException("Invalid server URL!")
        }
        val defaultOptions = JitsiMeetConferenceOptions.Builder()
            .setServerURL(serverURL)
            // When using JaaS, set the obtained JWT here
            //.setToken("MyJWT")
            // Different features flags can be set
            //.setFeatureFlag("toolbox.enabled", false)
            //.setFeatureFlag("filmstrip.enabled", false)
            .setFeatureFlag("welcomepage.enabled", false)
            .build()
        JitsiMeet.setDefaultConferenceOptions(defaultOptions)

        registerForBroadcastMessages()
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
        val args = this.arguments
        binding.inToolbar.imgBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.inToolbar.txtToolbarHeader.text = args?.getString("NAME")
        binding.inToolbar.imgCall.visibility = View.VISIBLE

        binding.inToolbar.imgCall.setOnClickListener {
            // Build options object for joining the conference. The SDK will merge the default
            // one we set earlier and this one when joining.
            val options = JitsiMeetConferenceOptions.Builder()
                .setRoom("text")
                // Settings for audio and video
                .setAudioMuted(true)
                .setVideoMuted(true)
                .setFeatureFlag("prejoinpage.enabled", false)
                .setFeatureFlag("chat.enabled", false)
                .setFeatureFlag("invite.enabled", false)
                .build()
            // Launch the new activity with the given options. The launch() method takes care
            // of creating the required Intent and passing the options.


            JitsiMeetActivity.launch(activity?.applicationContext, options)

        }
        binding.inToolbar.imgProfile.visibility = View.VISIBLE
        binding.inToolbar.imgProfile.setLocalImage(Uri.parse(args?.getString("IMAGE")), true)
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

                    Timber.d("TAG1 Response: $response")
                    //Log.d("TAG1", "Response: ${Gson().toJson(response)}")
                } else {
                    response.errorBody()?.string()?.let { Timber.e("TAG1 $it") }
                }
            } catch (e: Exception) {
                Timber.e("TAG1 ${e.toString()}")
            }
        }

    private fun registerForBroadcastMessages() {
        val intentFilter = IntentFilter()

        /* This registers for every possible event sent from JitsiMeetSDK
           If only some of the events are needed, the for loop can be replaced
           with individual statements:
           ex:  intentFilter.addAction(BroadcastEvent.Type.AUDIO_MUTED_CHANGED.action);
                intentFilter.addAction(BroadcastEvent.Type.CONFERENCE_TERMINATED.action);
                ... other events
         */
        for (type in BroadcastEvent.Type.values()) {
            intentFilter.addAction(type.action)
        }

        LocalBroadcastManager.getInstance(requireContext())
            .registerReceiver(broadcastReceiver, intentFilter)
    }

    override fun onDestroy() {
        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(broadcastReceiver)
        super.onDestroy()
    }

    // Example for handling different JitsiMeetSDK events
    private fun onBroadcastReceived(intent: Intent?) {
        if (intent != null) {
            val event = BroadcastEvent(intent)
            when (event.type) {
                BroadcastEvent.Type.CONFERENCE_JOINED -> Timber.i(
                    "Conference Joined with url%s",
                    event.data["url"]
                )
                BroadcastEvent.Type.PARTICIPANT_JOINED -> Timber.i(
                    "Participant joined%s",
                    event.data["name"]
                )
                else -> Timber.i("Received event: %s", event.type)
            }
        }
    }

    // Example for sending actions to JitsiMeetSDK
    private fun hangUp() {
        val hangupBroadcastIntent: Intent = BroadcastIntentHelper.buildHangUpIntent()
        LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(hangupBroadcastIntent)
    }

}