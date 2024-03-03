package kr.hobbly.hobbyweekly.android

import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import kr.hobbly.hobbyweekly.android.presentation.R
import kr.hobbly.hobbyweekly.android.presentation.common.util.showNotification

@AndroidEntryPoint
class HobbyWeeklyFcmService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token)

        // TODO : Token 저장 및 전달
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        showNotification(
            channelId = getString(R.string.channel_community),
            notificationId = message.sentTime.toInt(),
            title = message.notification?.title,
            content = message.notification?.body,
            icon = R.drawable.ic_launcher,
            group = null,
            priority = NotificationCompat.PRIORITY_DEFAULT
        )
    }
}
