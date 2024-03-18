package kr.hobbly.hobbyweekly.android.data.repository.nonfeature.user

import javax.inject.Inject
import kotlinx.coroutines.delay
import kr.hobbly.hobbyweekly.android.data.remote.local.SharedPreferencesManager
import kr.hobbly.hobbyweekly.android.domain.model.nonfeature.error.ServerException
import kr.hobbly.hobbyweekly.android.domain.model.nonfeature.user.AgreedTerm
import kr.hobbly.hobbyweekly.android.domain.model.nonfeature.user.Profile
import kr.hobbly.hobbyweekly.android.domain.model.nonfeature.user.Term
import kr.hobbly.hobbyweekly.android.domain.repository.nonfeature.TokenRepository
import kr.hobbly.hobbyweekly.android.domain.repository.nonfeature.UserRepository

class MockUserRepository @Inject constructor(
    private val sharedPreferencesManager: SharedPreferencesManager,
    private val tokenRepository: TokenRepository
) : UserRepository {
    override suspend fun getTermList(): Result<List<Term>> {
        randomShortDelay()
        return Result.success(
            listOf(
                Term(
                    id = 0L,
                    name = "개인정보 수집 이용동의",
                    isRequired = true,
                    url = "https://www.naver.com"
                ),
                Term(
                    id = 1L,
                    name = "고유식별 정보처리 동의",
                    isRequired = true,
                    url = "https://www.naver.com"
                ),
                Term(
                    id = 2L,
                    name = "통신사 이용약관 동의",
                    isRequired = true,
                    url = "https://www.naver.com"
                ),
                Term(
                    id = 3L,
                    name = "서비스 이용약관 동의",
                    isRequired = true,
                    url = "https://www.naver.com"
                ),
            )
        )
    }

    override suspend fun agreeTermList(
        termList: List<Long>
    ): Result<Unit> {
        randomShortDelay()
        return Result.success(Unit)
    }

    override suspend fun getTermListAgreeState(): Result<List<AgreedTerm>> {
        randomShortDelay()
        return Result.success(
            listOf(
                AgreedTerm(
                    id = 0L,
                    name = "개인정보 수집 이용동의",
                    isAgreed = true
                ),
                AgreedTerm(
                    id = 1L,
                    name = "고유식별 정보처리 동의",
                    isAgreed = true
                ),
                AgreedTerm(
                    id = 2L,
                    name = "통신사 이용약관 동의",
                    isAgreed = true
                ),
                AgreedTerm(
                    id = 3L,
                    name = "서비스 이용약관 동의",
                    isAgreed = true
                ),
            )
        )
    }

    override suspend fun setHobbyList(
        hobbyList: List<String>
    ): Result<Unit> {
        randomShortDelay()
        return Result.success(Unit)
    }

    override suspend fun editProfile(
        nickname: String,
        image: String
    ): Result<Unit> {
        randomShortDelay()
        return Result.success(Unit)
    }

    override suspend fun getProfile(): Result<Profile> {
        randomShortDelay()
        val isLogined = tokenRepository.accessToken.isNotEmpty()
        return if (isLogined) {
            Result.success(
                Profile(
                    id = 1,
                    nickname = "Ray Jang",
                    image = "https://avatars.githubusercontent.com/u/20411648?v=4",
                    isHobbyChecked = true
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
}
