package kr.hobbly.hobbyweekly.android

import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.net.toUri
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.coroutines.runBlocking
import kr.hobbly.hobbyweekly.android.domain.usecase.nonfeature.tracking.SetFcmTokenUseCase
import kr.hobbly.hobbyweekly.android.presentation.R
import kr.hobbly.hobbyweekly.android.presentation.common.DOMAIN
import kr.hobbly.hobbyweekly.android.presentation.common.util.showNotification
import kr.hobbly.hobbyweekly.android.presentation.ui.main.MainActivity

@AndroidEntryPoint
class HobbyWeeklyFcmService : FirebaseMessagingService() {

    @Inject
    lateinit var setFcmTokenUseCase: SetFcmTokenUseCase

    override fun onNewToken(token: String) {
        super.onNewToken(token)

        runBlocking {
            setFcmTokenUseCase(token)
        }
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        fun getDeeplinkPendingIntent(): PendingIntent? {
            return message.data["deeplink"]
                ?.takeIf { it.startsWith(DOMAIN) }
                ?.runCatching { toUri() }
                ?.getOrNull()
                ?.let { uri ->
                    Intent(
                        Intent.ACTION_VIEW,
                        uri,
                        this@HobbyWeeklyFcmService,
                        MainActivity::class.java
                    )
                }?.let { intent ->
                    TaskStackBuilder.create(this@HobbyWeeklyFcmService)
                        .addNextIntentWithParentStack(intent)
                        .getPendingIntent(
                            0,
                            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                        )
                }
        }

        showNotification(
            channelId = getString(R.string.channel_community),
            notificationId = message.sentTime.toInt(),
            title = message.notification?.title,
            content = message.notification?.body,
            icon = R.drawable.ic_launcher,
            group = null,
            pendingIntent = getDeeplinkPendingIntent(),
            priority = NotificationCompat.PRIORITY_DEFAULT
        )
    }
}
