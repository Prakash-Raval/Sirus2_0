package com.example.sirus20.chat.listner

import androidx.browser.trusted.Token

interface OnChatClick {
    fun onClick(name: String, uid: String,token: String)
}