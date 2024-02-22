package kr.hobbly.hobbyweekly.android.data.remote.network.api.nonfeature

import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import javax.inject.Inject
import kr.hobbly.hobbyweekly.android.data.remote.network.di.AuthHttpClient
import kr.hobbly.hobbyweekly.android.data.remote.network.di.NoAuthHttpClient
import kr.hobbly.hobbyweekly.android.data.remote.network.environment.BaseUrlProvider
import kr.hobbly.hobbyweekly.android.data.remote.network.environment.ErrorMessageMapper
import kr.hobbly.hobbyweekly.android.data.remote.network.model.nonfeature.authentication.LoginReq
import kr.hobbly.hobbyweekly.android.data.remote.network.model.nonfeature.authentication.LoginRes
import kr.hobbly.hobbyweekly.android.data.remote.network.model.nonfeature.authentication.RegisterReq
import kr.hobbly.hobbyweekly.android.data.remote.network.model.nonfeature.authentication.RegisterRes
import kr.hobbly.hobbyweekly.android.data.remote.network.util.convert

class AuthenticationApi @Inject constructor(
    @NoAuthHttpClient private val noAuthClient: HttpClient,
    @AuthHttpClient private val client: HttpClient,
    private val baseUrlProvider: BaseUrlProvider,
    private val errorMessageMapper: ErrorMessageMapper
) {
    private val baseUrl: String
        get() = baseUrlProvider.get()

    suspend fun login(
        username: String,
        password: String
    ): Result<LoginRes> {
        return noAuthClient.post("$baseUrl/api/v1/auth/login") {
            setBody(
                LoginReq(
                    username = username,
                    password = password
                )
            )
        }.convert(errorMessageMapper::map)
    }

    suspend fun logout(): Result<Unit> {
        return client.post("$baseUrl/api/v1/auth/logout")
            .convert(errorMessageMapper::map)
    }

    suspend fun register(
        username: String,
        password: String
    ): Result<RegisterRes> {
        return noAuthClient.post("$baseUrl/api/v1/auth/register") {
            setBody(
                RegisterReq(
                    username = username,
                    password = password
                )
            )
        }.convert(errorMessageMapper::map)
    }

    suspend fun withdraw(): Result<Unit> {
        return client.delete("$baseUrl/api/v1/auth/withdraw")
            .convert(errorMessageMapper::map)
    }
}
