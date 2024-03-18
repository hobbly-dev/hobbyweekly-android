package kr.hobbly.hobbyweekly.android.data.repository.nonfeature.tracking

import androidx.annotation.Size
import androidx.core.os.bundleOf
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.crashlytics.setCustomKeys
import com.google.firebase.ktx.Firebase
import io.sentry.Sentry
import io.sentry.protocol.User
import javax.inject.Inject
import kr.hobbly.hobbyweekly.android.domain.model.nonfeature.user.Profile
import kr.hobbly.hobbyweekly.android.domain.repository.nonfeature.TrackingRepository

class RealTrackingRepository @Inject constructor() : TrackingRepository {

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
        return runCatching {
            Firebase.analytics.logEvent(
                eventName,
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
}
