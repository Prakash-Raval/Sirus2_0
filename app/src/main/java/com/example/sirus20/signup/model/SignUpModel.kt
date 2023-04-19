package com.example.sirus20.signup.model

data class SignUpModel(
    var name: String? = "",
    var userName: String? = "",
    var email: String? = "",
    var password: String? = "",
    var countryCode: String? = "",
    var day: Long? = null,
    var month: Long? = null,
    var year: Long? = null,
    var gender: String? = "",
    var mobile: String? = "",
    var image: String? = "",
    var uid: String? = "",
    var token: String? = ""
)
