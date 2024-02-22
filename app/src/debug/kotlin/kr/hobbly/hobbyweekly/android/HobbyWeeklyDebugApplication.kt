package kr.hobbly.hobbyweekly.android

import timber.log.Timber

class HobbyWeeklyDebugApplication : HobbyWeeklyApplication() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}
