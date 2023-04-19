package com.example.sirus20.notification.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.annotations.SerializedName

data class NotificationData(
    @SerializedName("title")
    var title: String,
    @SerializedName("body")
    var body: String = "Hello",
   /* @SerializedName("receiverToken")
    val receiverToken: String = "",
    @SerializedName("type")
    val type: String = "",
    @SerializedName("senderToken")
    val senderToken: String = "",
    @SerializedName("body")
    val data: String = "",
    @SerializedName("body")
    val name: String = "",
    @SerializedName("body")
    val email: String = "",
    @SerializedName("body")
    val meetingRoom: String = "",
    @SerializedName("body")
    val meetingType: String = ""*/
)
