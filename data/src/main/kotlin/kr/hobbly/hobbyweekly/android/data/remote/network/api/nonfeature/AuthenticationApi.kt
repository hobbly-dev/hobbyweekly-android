package kr.hobbly.hobbyweekly.android.data.remote.network.api.nonfeature

import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import javax.inject.Inject
import kr.hobbly.hobbyweekly.android.data.remote.network.di.AuthHttpClient
import kr.hobbly.hobbyweekly.android.data.remote.network.di.NoAuthHttpClient
import kr.hobbly.hobbyweekly.android.data.remote.network.environment.BaseUrlProvider
import kr.hobbly.hobbyweekly.android.data.remote.network.environment.ErrorMessageMapper
import kr.hobbly.hobbyweekly.android.data.remote.network.model.nonfeature.authentication.LoginReq
import kr.hobbly.hobbyweekly.android.data.remote.network.model.nonfeature.authentication.LoginRes
import kr.hobbly.hobbyweekly.android.data.remote.network.util.convert
import kr.hobbly.hobbyweekly.android.domain.model.nonfeature.authentication.SocialType

class AuthenticationApi @Inject constructor(
    @NoAuthHttpClient private val noAuthClient: HttpClient,
    @AuthHttpClient private val client: HttpClient,
    private val baseUrlProvider: BaseUrlProvider,
    private val errorMessageMapper: ErrorMessageMapper
) {
    private val baseUrl: String
        get() = baseUrlProvider.get()

    suspend fun login(
        socialId: String,
        socialType: SocialType,
        firebaseToken: String
    ): Result<LoginRes> {
        return noAuthClient.post("$baseUrl/v1/auth/login/social") {
            setBody(
                LoginReq(
                    socialId = socialId,
                    socialType = socialType.value,
                    firebaseToken = firebaseToken
                )
            )
        }.convert(errorMessageMapper::map)
    }

    suspend fun logout(): Result<Unit> {
        return client.post("$baseUrl/v1/auth/logout")
            .convert(errorMessageMapper::map)
    }

    suspend fun withdraw(): Result<Unit> {
        return client.post("$baseUrl/v1/auth/withdraw")
            .convert(errorMessageMapper::map)
    }
}
