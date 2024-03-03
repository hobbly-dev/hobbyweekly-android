package kr.hobbly.hobbyweekly.android

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
open class HobbyWeeklyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        initializeChannel()
        initializeFirebase()
        initializeKakaoTalk()
    }

    private fun initializeChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelCommunity = NotificationChannel(
                getString(kr.hobbly.hobbyweekly.android.presentation.R.string.channel_community),
                "커뮤니티",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "하비 위클리 커뮤니티 메세지"
            }
            val channelAlarm = NotificationChannel(
                getString(kr.hobbly.hobbyweekly.android.presentation.R.string.channel_routine),
                "블록 루틴 알람",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "하비 위클리 블록 루틴 알람"
            }

//            val group = NotificationChannelGroup(
//                CHANNEL_GROUP_1,
//                "group 1"
//            ).apply {
//                channels.add(channel1)
//            }

            getSystemService(NotificationManager::class.java).run {
                createNotificationChannel(channelCommunity)
                createNotificationChannel(channelAlarm)
//                createNotificationChannelGroup(group)
            }
        }
    }

    private fun initializeFirebase() {
        Firebase.analytics
        Firebase.crashlytics
    }

    private fun initializeKakaoTalk() {
        KakaoSdk.init(this, getString(R.string.key_kakao_app))
    }
}
