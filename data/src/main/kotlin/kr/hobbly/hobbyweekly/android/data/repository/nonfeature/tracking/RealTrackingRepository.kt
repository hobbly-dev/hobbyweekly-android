package kr.hobbly.hobbyweekly.android.data.repository.nonfeature.tracking

import android.content.Context
import android.content.pm.ApplicationInfo
import androidx.annotation.Size
import androidx.core.os.bundleOf
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.crashlytics.setCustomKeys
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.qualifiers.ApplicationContext
import io.sentry.Sentry
import io.sentry.protocol.User
import javax.inject.Inject
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kr.hobbly.hobbyweekly.android.domain.model.nonfeature.user.Profile
import kr.hobbly.hobbyweekly.android.domain.repository.nonfeature.TrackingRepository

class RealTrackingRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val dataStore: DataStore<Preferences>
) : TrackingRepository {

    override suspend fun setFcmToken(token: String) {
        dataStore.edit { preferences ->
            preferences[stringPreferencesKey(FCM_TOKEN)] = token
        }
    }

    override suspend fun getFcmToken(): String {
        return dataStore.data.map { preferences ->
            preferences[stringPreferencesKey(FCM_TOKEN)]
        }.first().orEmpty()
    }

    override suspend fun setProfile(
        profile: Profile
    ): Result<Unit> {
        return runCatching {
            Sentry.setUser(
                User().apply {
                    this.id = profile.id.toString()
                    this.username = profile.nickname
                }
            )
            Firebase.analytics.run {
                setUserId(profile.id.toString())
                setUserProperty("nickname", profile.nickname)
            }
            Firebase.crashlytics.run {
                setUserId(profile.id.toString())
                setCustomKeys {
                    key("nickname", profile.nickname)
                }
            }
        }
    }

    override suspend fun logEvent(
        @Size(min = 1, max = 40) eventName: String,
        params: Map<String, Any>
    ): Result<Unit> {
        val isDebug: Boolean =
            (0 != context.applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE)

        val newEventName = if (isDebug) {
            "debug_$eventName"
        } else {
            eventName
        }
        return runCatching {
            Firebase.analytics.logEvent(
                newEventName,
                bundleOf(
                    *params.map { (key, value) ->
                        when (value) {
                            is String -> key to value
                            is Int -> key to value.toLong()
                            is Long -> key to value
                            is Float -> key to value.toDouble()
                            is Double -> key to value
                            is Boolean -> key to value.toString()
                            else -> throw IllegalArgumentException("Invalid value type")
                        }
                    }.toTypedArray()
                )
            )
        }
    }

    companion object {
        private const val FCM_TOKEN = "fcm_token"
    }
}
