package com.example.sirus20.user.model

import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.parcelize.Parcelize

@Parcelize
data class Rating(
    @JsonProperty("count")
    var count: Int? = null,
    @JsonProperty("rate")
    var rate: Double? = null
) : Parcelable