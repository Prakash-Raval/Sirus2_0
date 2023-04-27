package com.example.sirus20.user.model

import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductModel(
    @JsonProperty("category")
    var category: String? = null,
    @JsonProperty("description")
    var description: String? = null,
    @JsonProperty("id")
    var id: Int? = null,
    @JsonProperty("image")
    var image: String? = null,
    @JsonProperty("price")
    var price: Double? = null,
    @JsonProperty("rating")
    var rating: Rating? = null,
    @JsonProperty("title")
    var title: String? = null
) : Parcelable
