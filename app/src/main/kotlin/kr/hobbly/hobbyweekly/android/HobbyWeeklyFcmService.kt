package kr.hobbly.hobbyweekly.android

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class HobbyWeeklyFcmService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token)

        // TODO : Token 저장 및 전달
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        // TODO : Notification 보여주기
    }
}
