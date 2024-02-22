package kr.hobbly.hobbyweekly.android.data.repository.nonfeature.authentication

import javax.inject.Inject
import kr.hobbly.hobbyweekly.android.data.remote.network.api.nonfeature.AuthenticationApi
import kr.hobbly.hobbyweekly.android.domain.repository.nonfeature.AuthenticationRepository
import kr.hobbly.hobbyweekly.android.domain.repository.nonfeature.TokenRepository

class RealAuthenticationRepository @Inject constructor(
    private val authenticationApi: AuthenticationApi,
    private val tokenRepository: TokenRepository
) : AuthenticationRepository {

    override suspend fun login(
        username: String,
        password: String
    ): Result<Long> {
        return authenticationApi.login(
            username = username,
            password = password,
        ).onSuccess { token ->
            tokenRepository.refreshToken = token.refreshToken
            tokenRepository.accessToken = token.accessToken
        }.map { login ->
            login.id
        }
    }

    override suspend fun logout(): Result<Unit> {
        return authenticationApi.logout()
            .onSuccess {
                tokenRepository.refreshToken = ""
                tokenRepository.accessToken = ""
            }
    }

    override suspend fun register(
        username: String,
        password: String
    ): Result<Long> {
        return authenticationApi.register(
            username = username,
            password = password
        ).onSuccess { register ->
            tokenRepository.refreshToken = register.refreshToken
            tokenRepository.accessToken = register.accessToken
        }.map { register ->
            register.id
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
