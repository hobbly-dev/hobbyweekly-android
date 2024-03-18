package kr.hobbly.hobbyweekly.android.data.repository.nonfeature.authentication

import javax.inject.Inject
import kotlinx.coroutines.delay
import kr.hobbly.hobbyweekly.android.data.remote.local.SharedPreferencesManager
import kr.hobbly.hobbyweekly.android.domain.model.nonfeature.authentication.SocialType
import kr.hobbly.hobbyweekly.android.domain.model.nonfeature.error.ServerException
import kr.hobbly.hobbyweekly.android.domain.repository.nonfeature.AuthenticationRepository
import kr.hobbly.hobbyweekly.android.domain.repository.nonfeature.TokenRepository

class MockAuthenticationRepository @Inject constructor(
    private val sharedPreferencesManager: SharedPreferencesManager,
    private val tokenRepository: TokenRepository
) : AuthenticationRepository {

    private var isRegistered: Boolean
        set(value) = sharedPreferencesManager.setBoolean(IS_REGISTERED, value)
        get() = sharedPreferencesManager.getBoolean(IS_REGISTERED, false)

    override suspend fun login(
        socialId: String,
        socialType: SocialType,
        firebaseToken: String
    ): Result<Unit> {
        randomShortDelay()
        return Result.success(Unit).onSuccess { token ->
            tokenRepository.refreshToken = "mock_access_token"
            tokenRepository.accessToken = "mock_refresh_token"
            isRegistered = true
        }
    }

    override suspend fun logout(): Result<Unit> {
        randomShortDelay()
        return when {
            tokenRepository.accessToken.isEmpty() || tokenRepository.refreshToken.isEmpty() -> {
                Result.failure(ServerException("MOCK_ERROR", "로그인 상태가 아닙니다."))
            }

            !isRegistered -> {
                Result.failure(ServerException("MOCK_ERROR", "가입된 사용자가 아닙니다."))
            }

            else -> {
                Result.success(Unit)
            }
        }.onSuccess {
            tokenRepository.refreshToken = ""
            tokenRepository.accessToken = ""
        }
    }

    override suspend fun withdraw(): Result<Unit> {
        randomLongDelay()
        return when {
            tokenRepository.accessToken.isEmpty() || tokenRepository.refreshToken.isEmpty() -> {
                Result.failure(ServerException("MOCK_ERROR", "로그인 상태가 아닙니다."))
            }

            !isRegistered -> {
                Result.failure(ServerException("MOCK_ERROR", "가입된 사용자가 아닙니다."))
            }

            else -> {
                Result.success(Unit)
            }
        }.onSuccess {
            tokenRepository.refreshToken = ""
            tokenRepository.accessToken = ""
            isRegistered = false
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
