package kr.hobbly.hobbyweekly.android.data.repository.nonfeature.authentication

import javax.inject.Inject
import kr.hobbly.hobbyweekly.android.data.remote.network.api.nonfeature.AuthenticationApi
import kr.hobbly.hobbyweekly.android.domain.model.nonfeature.authentication.SocialType
import kr.hobbly.hobbyweekly.android.domain.repository.nonfeature.AuthenticationRepository
import kr.hobbly.hobbyweekly.android.domain.repository.nonfeature.TokenRepository

class RealAuthenticationRepository @Inject constructor(
    private val authenticationApi: AuthenticationApi,
    private val tokenRepository: TokenRepository
) : AuthenticationRepository {

    override suspend fun login(
        socialId: String,
        socialType: SocialType,
        firebaseToken: String
    ): Result<Unit> {
        return authenticationApi.login(
            socialId = socialId,
            socialType = socialType,
            firebaseToken = firebaseToken
        ).onSuccess { token ->
            tokenRepository.refreshToken = token.refreshToken
            tokenRepository.accessToken = token.accessToken
        }.map { }
    }

    override suspend fun logout(): Result<Unit> {
        return authenticationApi.logout()
            .onSuccess {
                tokenRepository.refreshToken = ""
                tokenRepository.accessToken = ""
            }
    }

    override suspend fun withdraw(): Result<Unit> {
        return authenticationApi.withdraw()
            .onSuccess {
                tokenRepository.refreshToken = ""
                tokenRepository.accessToken = ""
            }
    }
}
