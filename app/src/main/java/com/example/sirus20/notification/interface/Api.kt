package com.example.sirus20.notification.`interface`

import com.example.sirus20.common.Constant.CONTENT_TYPE
import com.example.sirus20.common.Constant.SERVER_KEY
import com.example.sirus20.notification.model.PushNotification
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface Api {
    interface NotificationApi {

        @Headers("Authorization: key=$SERVER_KEY", "Content-type:$CONTENT_TYPE")
        @POST("fcm/send")
        suspend fun postNotification(
            @Body notification: PushNotification
        ): Response<ResponseBody>
    }

}