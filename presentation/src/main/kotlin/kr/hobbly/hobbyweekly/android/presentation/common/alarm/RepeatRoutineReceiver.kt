package kr.hobbly.hobbyweekly.android.presentation.common.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import kr.hobbly.hobbyweekly.android.common.util.orZero
import kr.hobbly.hobbyweekly.android.presentation.R
import kr.hobbly.hobbyweekly.android.presentation.common.util.showNotification

class RepeatRoutineReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        val notificationId = intent?.getIntExtra(NOTIFICATION_ID, 0).orZero()
        val title = intent?.getStringExtra(NOTIFICATION_TITLE).orEmpty()
        val content = intent?.getStringExtra(NOTIFICATION_CONTENT).orEmpty()
        val channelId =
            context.getString(R.string.channel_routine)
        val icon = R.drawable.ic_launcher
        val group = null
        val priority = NotificationCompat.PRIORITY_DEFAULT

        context.showNotification(
            channelId = channelId,
            notificationId = notificationId,
            title = title,
            content = content,
            icon = icon,
            group = group,
            priority = priority
        )
    }

    companion object {
        const val NOTIFICATION_ID = "notification_id"
        const val NOTIFICATION_TITLE = "notification_title"
        const val NOTIFICATION_CONTENT = "notification_content"
    }
}
