package kr.hobbly.hobbyweekly.android.data.repository.nonfeature.user

import androidx.paging.PagingData
import javax.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atTime
import kotlinx.datetime.minus
import kotlinx.datetime.todayIn
import kr.hobbly.hobbyweekly.android.data.remote.local.SharedPreferencesManager
import kr.hobbly.hobbyweekly.android.domain.model.nonfeature.error.ServerException
import kr.hobbly.hobbyweekly.android.domain.model.nonfeature.notification.Notification
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

    override suspend fun getAgreedTermList(): Result<List<Long>> {
        randomShortDelay()
        return Result.success(
            listOf(0L, 1L, 2L, 3L)
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

    override suspend fun getNotificationPaging(): Flow<PagingData<Notification>> {
        randomShortDelay()

        return flowOf(
            PagingData.from(
                listOf(
                    Notification(
                        id = 1,
                        content = "종국님이 좋아요를 눌렀습니다",
                        isRead = false,
                        createdAt = Clock.System.todayIn(TimeZone.currentSystemDefault())
                            .atTime(0, 0, 0),
                    ),
                    Notification(
                        id = 2,
                        content = "새로운 답글이 달렸어요: 쓴소리 고맙다",
                        isRead = false,
                        createdAt = Clock.System.todayIn(TimeZone.currentSystemDefault())
                            .minus(1, DateTimeUnit.DAY)
                            .atTime(0, 0, 0),
                    ),
                    Notification(
                        id = 3,
                        content = "새로운 댓글이 달렸어요 : 홧팅이여!! ❤",
                        isRead = false,
                        createdAt = Clock.System.todayIn(TimeZone.currentSystemDefault())
                            .minus(7, DateTimeUnit.DAY)
                            .atTime(0, 0, 0),
                    )
                )
            )
        )
    }

    override suspend fun checkNotification(
        id: Long
    ): Result<Unit> {
        randomShortDelay()

        return Result.success(Unit)
    }

    private suspend fun randomShortDelay() {
        delay(LongRange(100, 500).random())
    }

    private suspend fun randomLongDelay() {
        delay(LongRange(500, 2000).random())
    }
}
