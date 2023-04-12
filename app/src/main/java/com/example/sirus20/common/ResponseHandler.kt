package com.example.sirus20.common

sealed class ResponseHandler<out T> {
    object Loading : ResponseHandler<Nothing>()
    object OnFailed :
        ResponseHandler<Nothing>()
    object OnSuccessResponse : ResponseHandler<Nothing>()
}