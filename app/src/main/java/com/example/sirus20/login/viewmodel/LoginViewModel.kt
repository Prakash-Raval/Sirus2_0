package com.example.sirus20.login.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sirus20.common.ResponseHandler
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class LoginViewModel : ViewModel() {
    private var auth: FirebaseAuth? = null
    private var fireStore: FirebaseFirestore? = null
    var response = MutableLiveData<ResponseHandler<String>>()

    init {
        auth = FirebaseAuth.getInstance()
        fireStore = FirebaseFirestore.getInstance()
    }


    fun authenticateUser(email: String, password: String) {
        response.value = ResponseHandler.Loading
        auth?.let { login ->
            login.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task: Task<AuthResult> ->
                    if (task.isSuccessful) {
                        response.value = ResponseHandler.OnSuccessResponse
                    } else {
                        response.value = ResponseHandler.OnFailed
                    }
                }
        }
    }
}