package kr.hobbly.hobbyweekly.android.data.remote.network.environment

import kr.hobbly.hobbyweekly.android.data.remote.local.SharedPreferencesManager

class BaseUrlProvider(
    private val sharedPreferencesManager: SharedPreferencesManager
) {
    fun get(): String {
        // TODO : Add Dev Flag
        val isDev: Boolean = false

        if (isDev) {
            return DEV_BASE_URL
        } else {
            return RELEASE_BASE_URL
        }
    }

    companion object {
        private const val DEV_BASE_URL = "https://api.hobbly.co.kr"
        private const val RELEASE_BASE_URL = "https://api.hobbly.co.kr"
    }
}
