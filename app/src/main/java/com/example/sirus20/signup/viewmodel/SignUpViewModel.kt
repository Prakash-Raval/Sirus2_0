package com.example.sirus20.signup.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sirus20.common.ResponseHandler
import com.example.sirus20.signup.model.SignUpModel
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class SignUpViewModel : ViewModel() {

    private var auth: FirebaseAuth? = null
    private var fireStore: FirebaseFirestore? = null
    var response = MutableLiveData<ResponseHandler<String>>()

    init {
        auth = FirebaseAuth.getInstance()
        fireStore = FirebaseFirestore.getInstance()
    }

    fun getSignUpData(signUpModel: SignUpModel) {

        response.value = ResponseHandler.Loading
        signUpModel.email?.let {
            signUpModel.password?.let { it1 ->
                auth?.createUserWithEmailAndPassword(
                    it, it1
                )
                    ?.addOnCompleteListener { task: Task<AuthResult> ->
                        if (task.isSuccessful) {
                            registerUser(signUpModel)
                            response.value = ResponseHandler.OnSuccessResponse
                        } else {


                            response.value = ResponseHandler.OnFailed
                        }
                    }
                    ?.addOnFailureListener {
                        Log.d("TAGff", "getSignUpData: ${it.message}")
                    }
            }
        }
    }

    private fun registerUser(signUpModel: SignUpModel) {
        //creating collection to store data
        response.value = ResponseHandler.Loading
        fireStore?.collection("USER")
            ?.document(auth?.uid.toString())
            ?.set(signUpModel, SetOptions.merge())
            ?.addOnSuccessListener {
                response.value = ResponseHandler.OnSuccessResponse
                Log.d("TAGS", "registerUser: $it")

            }
            ?.addOnFailureListener {
                Log.d("TAGERROR", "registerUser: ${it.message}")
                response.value = ResponseHandler.OnFailed
            }
    }
}


