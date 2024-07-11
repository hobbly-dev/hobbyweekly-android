package kr.hobbly.hobbyweekly.android.data.remote.network.api.nonfeature

import io.ktor.client.HttpClient
import io.ktor.client.request.post
import javax.inject.Inject
import kr.hobbly.hobbyweekly.android.data.remote.network.di.AuthHttpClient
import kr.hobbly.hobbyweekly.android.data.remote.network.environment.BaseUrlProvider
import kr.hobbly.hobbyweekly.android.data.remote.network.environment.ErrorMessageMapper
import kr.hobbly.hobbyweekly.android.data.remote.network.util.convert

class AuthenticationApi @Inject constructor(
    @AuthHttpClient private val client: HttpClient,
    private val baseUrlProvider: BaseUrlProvider,
    private val errorMessageMapper: ErrorMessageMapper
) {
    private val baseUrl: String
        get() = baseUrlProvider.get()

    suspend fun logout(): Result<Unit> {
        return client.post("$baseUrl/v1/auth/logout")
            .convert(errorMessageMapper::map)
    }

    suspend fun withdraw(): Result<Unit> {
        return client.post("$baseUrl/v1/auth/withdraw")
            .convert(errorMessageMapper::map)
    }
}
