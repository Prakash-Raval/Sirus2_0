package com.example.sirus20.firebase

import com.google.firebase.auth.FirebaseAuth


class FireStoreClass {

    //getting id of current user
    fun getCurrentUserID(): String {
        val currentUser = FirebaseAuth.getInstance().currentUser
        var currentUserID = ""
        if (currentUser != null) {
            currentUserID = currentUser.uid
        }
        return currentUserID
    }

}