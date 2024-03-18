package kr.hobbly.hobbyweekly.android.data.repository.nonfeature.tracking

import androidx.annotation.Size
import javax.inject.Inject
import kr.hobbly.hobbyweekly.android.data.remote.local.SharedPreferencesManager
import kr.hobbly.hobbyweekly.android.domain.model.nonfeature.user.Profile
import kr.hobbly.hobbyweekly.android.domain.repository.nonfeature.TrackingRepository

class MockTrackingRepository @Inject constructor(
    private val sharedPreferencesManager: SharedPreferencesManager
) : TrackingRepository {

    override var fcmToken: String
        set(value) = sharedPreferencesManager.setString(FCM_TOKEN, value)
        get() = sharedPreferencesManager.getString(FCM_TOKEN, "")

    override suspend fun setProfile(
        profile: Profile
    ): Result<Unit> {
        return Result.success(Unit)
    }

    override suspend fun logEvent(
        @Size(min = 1, max = 40) eventName: String,
        params: Map<String, Any>
    ): Result<Unit> {
        return if (
            params.values.any {
                it !is String && it !is Int && it !is Long && it !is Float && it !is Double && it !is Boolean
            }
        ) {
            Result.failure(IllegalArgumentException("Invalid value type"))
        } else {
            Result.success(Unit)
        }
    }

    companion object {
        private const val FCM_TOKEN = "fcm_token"
    }
}
