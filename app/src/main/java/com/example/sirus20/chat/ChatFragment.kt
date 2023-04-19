package com.example.sirus20.chat

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.sirus20.R
import com.example.sirus20.chat.adapter.ChatListAdapter
import com.example.sirus20.chat.listner.OnChatClick
import com.example.sirus20.databinding.FragmentChatBinding
import com.example.sirus20.signup.model.SignUpModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class ChatFragment : Fragment(), OnChatClick {
    private lateinit var binding: FragmentChatBinding
    private lateinit var adapter: ChatListAdapter
    private var featuredDataList = ArrayList<SignUpModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.fragment_chat,
            container,
            false
        )
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        featuredDataList.clear()
        getUsers()
        setToolbar()
    }

    /*
    * getting user data
    * */
    private fun getUsers() {

        val db = Firebase.firestore
        val auth = FirebaseAuth.getInstance()
        //getting data from firebase fire store
        db.collection("USER").get().addOnSuccessListener { documents ->
            for (document in documents) {
                val name = document["name"].toString()
                val uid = document["uid"].toString()
                val image = document["image"].toString()
                val token = document["token"].toString()
                if (auth.currentUser?.email != document["email"].toString()) {
                    //adding data to list from firebase
                    featuredDataList += listOf(
                        SignUpModel(
                            name = name,
                            image = image,
                            uid = uid,
                            token = token
                        )
                    )
                }

            }
            Log.d("TAG", "getUsers: $featuredDataList")
            adapter = ChatListAdapter(featuredDataList, this)
            binding.rvChat.adapter = adapter
        }
    }

    /*
    * adapter click
    * */
    override fun onClick(name: String, uid: String,token : String, image : String) {
        val bundle = Bundle()
        bundle.putString("NAME", name)
        bundle.putString("UID", uid)
        bundle.putString("TOKEN",token)
        bundle.putString("IMAGE",image)
        findNavController().navigate(R.id.action_chatFragment_to_messagesFragment, bundle)
    }

    /*
    * setting up toolbar
    * */
    private fun setToolbar() {
        binding.chatBar.imgBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.chatBar.txtToolbarHeader.text = getString(R.string.messages)

    }
}

