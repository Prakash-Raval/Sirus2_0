package com.example.sirus20.addplace.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PlaceDataModel(
    var placeImage: String? = "",
    var placeName: String? = "",
    var placeRating: Float? = null,
    var placeDesc: String? = "",
    var placeType: String? = "",
    var placeCategory: String? = "",
    var placeId: String? = ""
) : Parcelable
