package kr.hobbly.hobbyweekly.android.domain.repository.nonfeature

import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.EventFlow
import kr.hobbly.hobbyweekly.android.domain.model.nonfeature.authentication.JwtToken
import kr.hobbly.hobbyweekly.android.domain.model.nonfeature.authentication.SocialType

interface TokenRepository {

    val refreshFailEvent: EventFlow<Unit>

    suspend fun login(
        socialId: String,
        socialType: SocialType,
        firebaseToken: String
    ): Result<Unit>

    suspend fun getRefreshToken(): String

    suspend fun getAccessToken(): String

    suspend fun refreshToken(
        refreshToken: String
    ): Result<JwtToken>

    suspend fun removeToken(): Result<Unit>
}
