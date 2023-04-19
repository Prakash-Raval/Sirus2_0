package com.example.sirus20.message

import com.example.sirus20.common.Constant
import com.example.sirus20.notification.`interface`.Api
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitInstance {

    companion object{
        private val retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        val api: Api.NotificationApi by lazy {
            retrofit.create(Api.NotificationApi::class.java)
        }
    }
}