package kr.hobbly.hobbyweekly.android.domain.repository.nonfeature

import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.EventFlow
import kr.hobbly.hobbyweekly.android.domain.model.nonfeature.authentication.JwtToken
import kr.hobbly.hobbyweekly.android.domain.model.nonfeature.authentication.SocialType

interface TokenRepository {

    var refreshToken: String

    var accessToken: String

    val refreshFailEvent: EventFlow<Unit>

    suspend fun login(
        socialId: String,
        socialType: SocialType,
        firebaseToken: String
    ): Result<Unit>

    suspend fun refreshToken(
        refreshToken: String
    ): Result<JwtToken>
}
