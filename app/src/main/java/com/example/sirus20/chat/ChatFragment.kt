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
import timber.log.Timber


class ChatFragment : Fragment(), OnChatClick {
    /*
    * variables
    * */
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
                val userName = document["userName"].toString()
                val countryCode = document["countryCode"].toString()
                val day = document["day"].toString().toLong()
                val month = document["month"].toString().toLong()
                val year = document["year"].toString().toLong()
                val gender = document["gender"].toString()
                val mobile = document["mobile"].toString()
                val email = document["email"].toString()


                //adding data of all users except current user
                if (auth.currentUser?.email != document["email"].toString()) {
                    //adding data to list from firebase
                    featuredDataList += listOf(
                        SignUpModel(
                            name = name,
                            email = email,
                            gender = gender,
                            day = day,
                            year = year,
                            userName = userName,
                            countryCode = countryCode,
                            month = month,
                            image = image,
                            uid = uid,
                            token = token,
                            mobile = mobile
                        )
                    )
                }

            }
            Timber.d("TAG  getUsers: $featuredDataList")
            adapter = ChatListAdapter(featuredDataList, this)
            binding.rvChat.adapter = adapter
        }
    }

    /*
    * recycler view click getting data
    * passing data to bundle
    * */
    override fun onClick(signUpModel: SignUpModel) {
        Timber.d("TAGonClick: $signUpModel")
        findNavController().navigate(R.id.action_chatFragment_to_messagesFragment, Bundle().apply {
            putParcelable("SIGNUPMODEL", signUpModel)
        })
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

