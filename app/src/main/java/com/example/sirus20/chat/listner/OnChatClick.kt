package com.example.sirus20.chat.listner

interface OnChatClick {
    fun onClick(name: String, uid: String, token: String, image: String)
}