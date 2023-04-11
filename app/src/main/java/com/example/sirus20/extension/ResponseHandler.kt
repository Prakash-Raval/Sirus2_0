package com.example.sirus20.extension

sealed class ResponseHandler<out T> {
    object Loading : ResponseHandler<Nothing>()
    object OnFailed :
        ResponseHandler<Nothing>()
    object OnSuccessResponse : ResponseHandler<Nothing>()
}