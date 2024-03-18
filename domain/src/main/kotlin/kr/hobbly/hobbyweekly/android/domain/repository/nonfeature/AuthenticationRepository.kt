package kr.hobbly.hobbyweekly.android.domain.repository.nonfeature

import kr.hobbly.hobbyweekly.android.domain.model.nonfeature.authentication.SocialType

interface AuthenticationRepository {

    suspend fun login(
        socialId: String,
        socialType: SocialType,
        firebaseToken: String
    ): Result<Unit>

    suspend fun logout(): Result<Unit>

    suspend fun withdraw(): Result<Unit>
}
