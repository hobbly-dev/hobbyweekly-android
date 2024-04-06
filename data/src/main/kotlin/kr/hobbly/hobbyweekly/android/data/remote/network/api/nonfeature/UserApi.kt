package kr.hobbly.hobbyweekly.android.data.remote.network.api.nonfeature

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import javax.inject.Inject
import kr.hobbly.hobbyweekly.android.common.util.takeIfNotEmpty
import kr.hobbly.hobbyweekly.android.data.remote.network.di.AuthHttpClient
import kr.hobbly.hobbyweekly.android.data.remote.network.environment.BaseUrlProvider
import kr.hobbly.hobbyweekly.android.data.remote.network.environment.ErrorMessageMapper
import kr.hobbly.hobbyweekly.android.data.remote.network.model.nonfeature.user.EditProfileReq
import kr.hobbly.hobbyweekly.android.data.remote.network.model.nonfeature.user.GetProfileRes
import kr.hobbly.hobbyweekly.android.data.remote.network.model.nonfeature.user.SetHobbyListReq
import kr.hobbly.hobbyweekly.android.data.remote.network.util.convert

class UserApi @Inject constructor(
    @AuthHttpClient private val client: HttpClient,
    private val baseUrlProvider: BaseUrlProvider,
    private val errorMessageMapper: ErrorMessageMapper
) {
    private val baseUrl: String
        get() = baseUrlProvider.get()

    suspend fun setHobbyList(
        hobbyList: List<String>
    ): Result<Unit> {
        return client.post("$baseUrl/v1/hobbies/me") {
            setBody(
                SetHobbyListReq(
                    hobbies = hobbyList
                )
            )
        }.convert(errorMessageMapper::map)
    }

    suspend fun editProfile(
        nickname: String,
        image: String
    ): Result<Unit> {
        return client.patch("$baseUrl/v1/profiles/me") {
            setBody(
                EditProfileReq(
                    nickname = nickname.takeIfNotEmpty(),
                    image = image.takeIfNotEmpty()
                )
            )
        }.convert(errorMessageMapper::map)
    }

    suspend fun getProfile(): Result<GetProfileRes> {
        return client.get("$baseUrl/v1/profiles/me")
            .convert(errorMessageMapper::map)
    }
}
