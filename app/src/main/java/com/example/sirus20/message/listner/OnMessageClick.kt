package com.example.sirus20.message.listner

import com.example.sirus20.message.model.MessageModel

interface OnMessageClick {
    fun onClick(messageModel: MessageModel)
}