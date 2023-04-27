package com.example.sirus20.notification.model

import com.google.gson.annotations.SerializedName

data class NotificationData(
    @SerializedName("title")
    var title: String,
    @SerializedName("body")
    var body: String = "Hello",

)
