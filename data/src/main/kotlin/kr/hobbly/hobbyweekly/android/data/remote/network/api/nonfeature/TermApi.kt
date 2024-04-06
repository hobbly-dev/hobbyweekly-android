package kr.hobbly.hobbyweekly.android.data.remote.network.api.nonfeature

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import javax.inject.Inject
import kr.hobbly.hobbyweekly.android.data.remote.network.di.AuthHttpClient
import kr.hobbly.hobbyweekly.android.data.remote.network.di.NoAuthHttpClient
import kr.hobbly.hobbyweekly.android.data.remote.network.environment.BaseUrlProvider
import kr.hobbly.hobbyweekly.android.data.remote.network.environment.ErrorMessageMapper
import kr.hobbly.hobbyweekly.android.data.remote.network.model.nonfeature.term.AgreeTermListReq
import kr.hobbly.hobbyweekly.android.data.remote.network.model.nonfeature.term.GetTermListAgreeStateListRes
import kr.hobbly.hobbyweekly.android.data.remote.network.model.nonfeature.term.GetTermListRes
import kr.hobbly.hobbyweekly.android.data.remote.network.util.convert

class TermApi @Inject constructor(
    @NoAuthHttpClient private val noAuthClient: HttpClient,
    @AuthHttpClient private val client: HttpClient,
    private val baseUrlProvider: BaseUrlProvider,
    private val errorMessageMapper: ErrorMessageMapper
) {
    private val baseUrl: String
        get() = baseUrlProvider.get()

    suspend fun getTermList(): Result<GetTermListRes> {
        return client.get("$baseUrl/v1/terms")
            .convert(errorMessageMapper::map)
    }

    suspend fun agreeTermList(
        termList: List<Long>
    ): Result<Unit> {
        return client.post("$baseUrl/v1/terms/me") {
            setBody(
                AgreeTermListReq(
                    terms = termList
                )
            )
        }.convert(errorMessageMapper::map)
    }

    suspend fun getTermListAgreeState(): Result<GetTermListAgreeStateListRes> {
        return client.get("$baseUrl/v1/terms/me")
            .convert(errorMessageMapper::map)
    }
}
