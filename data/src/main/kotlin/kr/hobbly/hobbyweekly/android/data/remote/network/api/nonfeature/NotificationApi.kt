package kr.hobbly.hobbyweekly.android.data.remote.network.api.nonfeature

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.patch
import io.ktor.client.request.post
import javax.inject.Inject
import kr.hobbly.hobbyweekly.android.data.remote.network.di.AuthHttpClient
import kr.hobbly.hobbyweekly.android.data.remote.network.environment.BaseUrlProvider
import kr.hobbly.hobbyweekly.android.data.remote.network.environment.ErrorMessageMapper
import kr.hobbly.hobbyweekly.android.data.remote.network.model.nonfeature.notification.GetNotificationListRes
import kr.hobbly.hobbyweekly.android.data.remote.network.util.convert

class NotificationApi @Inject constructor(
    @AuthHttpClient private val client: HttpClient,
    private val baseUrlProvider: BaseUrlProvider,
    private val errorMessageMapper: ErrorMessageMapper
) {
    private val baseUrl: String
        get() = baseUrlProvider.get()

    suspend fun getNotificationList(
        pageNum: Int,
        pageSize: Int
    ): Result<GetNotificationListRes> {
        return client.get("$baseUrl/v1/notifications/me") {
            parameter("pageNum", pageNum)
            parameter("pageSize", pageSize)
        }.convert(errorMessageMapper::map)
    }

    suspend fun checkNotification(
        id: Long
    ): Result<Unit> {
        return client.patch("$baseUrl/v1/notifications/$id")
            .convert(errorMessageMapper::map)
    }

    suspend fun blockUser(
        id: Long
    ): Result<Unit> {
        return client.post("$baseUrl/v1/profiles/$id/block")
            .convert(errorMessageMapper::map)
    }
}
