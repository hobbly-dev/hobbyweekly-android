package kr.hobbly.hobbyweekly.android.data.remote.network.environment

import kr.hobbly.hobbyweekly.android.data.remote.local.SharedPreferencesManager

class BaseUrlProvider(
    private val sharedPreferencesManager: SharedPreferencesManager
) {
    fun get(): String {
        val serverFlag = sharedPreferencesManager.getString(SERVER_FLAG)

        when (serverFlag) {
            SERVER_FLAG_DEVELOPMENT -> return DEVELOPMENT_BASE_URL
            SERVER_FLAG_PRODUCTION -> return PRODUCTION_BASE_URL
        }

        throw IllegalArgumentException("Invalid server flag")
    }

    fun initialize(
        defaultFlag: String
    ) {
        sharedPreferencesManager.getString(SERVER_FLAG, defaultFlag).let { serverFlag ->
            sharedPreferencesManager.setString(SERVER_FLAG, serverFlag)
        }
    }

    companion object {
        private const val DEVELOPMENT_BASE_URL = "https://dev.hobbly.co.kr"
        private const val PRODUCTION_BASE_URL = "https://api.hobbly.co.kr"

        private const val SERVER_FLAG = "server_flag"
        const val SERVER_FLAG_DEVELOPMENT = "development"
        const val SERVER_FLAG_PRODUCTION = "production"
    }
}
