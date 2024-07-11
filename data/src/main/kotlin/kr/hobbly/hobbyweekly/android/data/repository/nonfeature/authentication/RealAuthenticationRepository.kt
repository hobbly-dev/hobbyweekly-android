package kr.hobbly.hobbyweekly.android.data.repository.nonfeature.authentication

import javax.inject.Inject
import kr.hobbly.hobbyweekly.android.data.remote.network.api.nonfeature.AuthenticationApi
import kr.hobbly.hobbyweekly.android.domain.repository.nonfeature.AuthenticationRepository
import kr.hobbly.hobbyweekly.android.domain.repository.nonfeature.TokenRepository

class RealAuthenticationRepository @Inject constructor(
    private val authenticationApi: AuthenticationApi,
    private val tokenRepository: TokenRepository
) : AuthenticationRepository {

    override suspend fun logout(): Result<Unit> {
        return authenticationApi.logout()
            .onSuccess {
                tokenRepository.removeToken()
            }
    }

    override suspend fun withdraw(): Result<Unit> {
        return authenticationApi.withdraw()
            .onSuccess {
                tokenRepository.removeToken()
            }
    }
}
