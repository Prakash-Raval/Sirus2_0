package com.example.sirus20.notification


import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.sirus20.MainActivity
import com.example.sirus20.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import timber.log.Timber


class PushNotificationService : FirebaseMessagingService() {


    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
        Timber.d("TAG onNewToken:$p0 ")
        // token = p0
        val sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE).edit()
        sharedPreferences.putString("TOKEN", p0).apply()
    }

    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)
        val intent = Intent(this, MainActivity::class.java)
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationId = p0.notification?.channelId?.toInt()

        Timber.d("TAG33 ${p0.notification?.body}")

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_ONE_SHOT else PendingIntent.FLAG_ONE_SHOT
        )

        val builder: NotificationCompat.Builder =
            NotificationCompat.Builder(this, Constant.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification)
                .setAutoCancel(true)
                .setVibrate(longArrayOf(1000, 1000, 1000, 1000))
                .setOnlyAlertOnce(true)
                .setContentIntent(pendingIntent)
                .setContentTitle(p0.notification?.title)
                .setContentText(p0.notification?.body)
        notificationId?.let { notificationManager.notify(it, builder.build()) }


    }


    /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        createNotificationChannel(notificationManager)
    }
*/
/*if (notificationId != null) {
            notificationManager.notify(notificationId, builder.build())
        }*/


    /*val notification = NotificationCompat.Builder(this, CHANNEL_ID)
    .setContentTitle(p0.data["title"])
    .setContentText(p0.data["message"])
    .setSmallIcon(R.drawable.ic_add)
    .setAutoCancel(true)
    .setContentIntent(pendingIntent)
    .build()
*/
    /* @RequiresApi(Build.VERSION_CODES.O)
     private fun createNotificationChannel(notificationManager: NotificationManager) {

         val channelName = "ChannelFirebaseChat"
         val channel = NotificationChannel(
             CHANNEL_ID, channelName,
             NotificationManager.IMPORTANCE_HIGH
         ).apply {
             description = "MY FIREBASE CHAT DESCRIPTION"
             enableLights(true)
             lightColor = Color.WHITE
         }
         notificationManager.createNotificationChannel(channel)

     }
 */
    /* override fun onMessageReceived(message: RemoteMessage) {
         Log.d("TAG1232", "onMessageReceived: ${message.notification?.body}")
         if (message.notification != null) {
             message.notification?.channelId?.toInt()?.let {
                 generateNotification(
                     message.notification?.title ?: "",
                     it, message.notification?.body ?: ""
                 )
             }
         }

     }*/

    /*override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.i("SellerFirebaseService ", "Refreshed token :: $token")
        //sendRegistrationToServer(token)
    }*/

    /* private fun sendRegistrationToServer(token: String) {
         Log.i("SellerFirebaseService ", "Refreshed token :: $token")
     }*/

    /*   private fun generateNotification(title: String, channelID: Int, message: String) {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_ONE_SHOT else PendingIntent.FLAG_ONE_SHOT
        )

          val builder: NotificationCompat.Builder =
                  NotificationCompat.Builder(this, Constant.CHANNEL_ID)
                      .setSmallIcon(R.drawable.ic_notification)
                      .setAutoCancel(true)
                      .setVibrate(longArrayOf(1000, 1000, 1000, 1000))
                      .setOnlyAlertOnce(true)
                      .setContentIntent(pendingIntent)
                      .setContentTitle(title)
                      .setContentText(message)
              val notificationManager =
                  getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

              notificationManager.notify(channelID, builder.build())
          }*/

}

object Constant {
    const val CHANNEL_ID = "Notification Channel Id"
}