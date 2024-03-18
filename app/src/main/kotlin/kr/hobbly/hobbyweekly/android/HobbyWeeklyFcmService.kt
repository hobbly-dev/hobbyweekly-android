package kr.hobbly.hobbyweekly.android

import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kr.hobbly.hobbyweekly.android.domain.repository.nonfeature.TrackingRepository
import kr.hobbly.hobbyweekly.android.domain.usecase.nonfeature.tracking.SetFcmTokenUseCase
import kr.hobbly.hobbyweekly.android.presentation.R
import kr.hobbly.hobbyweekly.android.presentation.common.util.showNotification

@AndroidEntryPoint
class HobbyWeeklyFcmService : FirebaseMessagingService() {

    @Inject
    lateinit var setFcmTokenUseCase: SetFcmTokenUseCase

    override fun onNewToken(token: String) {
        super.onNewToken(token)

        setFcmTokenUseCase(token)
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
