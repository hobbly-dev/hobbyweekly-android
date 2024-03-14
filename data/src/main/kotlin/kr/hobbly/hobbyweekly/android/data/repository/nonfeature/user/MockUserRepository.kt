package kr.hobbly.hobbyweekly.android.data.repository.nonfeature.user

import javax.inject.Inject
import kotlinx.coroutines.delay
import kr.hobbly.hobbyweekly.android.data.remote.local.SharedPreferencesManager
import kr.hobbly.hobbyweekly.android.domain.model.nonfeature.error.ServerException
import kr.hobbly.hobbyweekly.android.domain.model.nonfeature.user.Profile
import kr.hobbly.hobbyweekly.android.domain.repository.nonfeature.TokenRepository
import kr.hobbly.hobbyweekly.android.domain.repository.nonfeature.UserRepository

class MockUserRepository @Inject constructor(
    private val sharedPreferencesManager: SharedPreferencesManager,
    private val tokenRepository: TokenRepository
) : UserRepository {

    override suspend fun getProfile(): Result<Profile> {
        randomShortDelay()
        val isLogined = tokenRepository.accessToken.isNotEmpty()
        return if (isLogined) {
            Result.success(
                Profile(
                    id = 1,
                    name = "장성혁",
                    nickname = "Ray Jang",
                    email = "ajou4095@gmail.com",
                    thumbnail = "https://avatars.githubusercontent.com/u/20411648?v=4"
                )
            )
        } else {
            Result.failure(
                ServerException("MOCK_ERROR", "로그인이 필요합니다.")
            )
        }
    }

    private suspend fun randomShortDelay() {
        delay(LongRange(100, 500).random())
    }

    private suspend fun randomLongDelay() {
        delay(LongRange(500, 2000).random())
    }

    companion object {
        private const val IS_REGISTERED = "mock_is_registered"
    }
}
