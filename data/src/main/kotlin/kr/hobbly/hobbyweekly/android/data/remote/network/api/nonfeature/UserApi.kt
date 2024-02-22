package kr.hobbly.hobbyweekly.android.data.remote.network.api.nonfeature

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import javax.inject.Inject
import kr.hobbly.hobbyweekly.android.data.remote.network.di.AuthHttpClient
import kr.hobbly.hobbyweekly.android.data.remote.network.environment.BaseUrlProvider
import kr.hobbly.hobbyweekly.android.data.remote.network.environment.ErrorMessageMapper
import kr.hobbly.hobbyweekly.android.data.remote.network.model.nonfeature.user.ProfileRes
import kr.hobbly.hobbyweekly.android.data.remote.network.util.convert

class UserApi @Inject constructor(
    @AuthHttpClient private val client: HttpClient,
    private val baseUrlProvider: BaseUrlProvider,
    private val errorMessageMapper: ErrorMessageMapper
) {
    private val baseUrl: String
        get() = baseUrlProvider.get()

    suspend fun getProfile(): Result<ProfileRes> {
        return client.get("$baseUrl/api/v1/user/profile")
            .convert(errorMessageMapper::map)
    }
}
