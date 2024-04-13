package kr.hobbly.hobbyweekly.android.data.remote.network.environment

import android.content.Context
import android.content.pm.ApplicationInfo
import kr.hobbly.hobbyweekly.android.data.R
import kr.hobbly.hobbyweekly.android.domain.model.nonfeature.error.UndefinedKeyException
import timber.log.Timber

class ErrorMessageMapper(
    private val context: Context
) {
    fun map(
        key: String
    ): String {
        val isDebug: Boolean =
            (0 != context.applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE)

        val id = when (key) {
            KEY_INTERNAL_SERVER_ERROR -> R.string.error_internal_server_error

            else -> {
                Timber.e(UndefinedKeyException("Undefined error key: $key"))
                R.string.error_unknown
            }
        }

        return if (isDebug) {
            context.getString(id) + " ($key)"
        } else {
            context.getString(id)
        }
    }

    companion object {
        const val KEY_INTERNAL_SERVER_ERROR = "SERVER:INTERNAL_SERVER_ERROR"
    }
}
