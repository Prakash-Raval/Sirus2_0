package com.example.sirus20.addplace.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sirus20.addplace.model.PlaceDataModel
import com.example.sirus20.common.ResponseHandler
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class AddPlaceViewModel : ViewModel() {

    private lateinit var mFireStore: FirebaseFirestore
    var response = MutableLiveData<ResponseHandler<String>>()

    fun addPlace(placeDetails: PlaceDataModel) {
        mFireStore = FirebaseFirestore.getInstance()
        response.value = ResponseHandler.Loading
        //adding collection to fire store
        mFireStore.collection("PlaceDetails")
            .document()
            .set(placeDetails, SetOptions.merge())
            .addOnSuccessListener {
                response.value = ResponseHandler.OnSuccessResponse
                Log.d("onSuccess", "addPlace:")
            }

            .addOnFailureListener { e ->
                response.value = ResponseHandler.OnFailed
                Log.d("onFailed", "addPlace: $e")
            }
    }

}