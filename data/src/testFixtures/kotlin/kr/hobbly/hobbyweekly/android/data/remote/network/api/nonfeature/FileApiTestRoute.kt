package kr.hobbly.hobbyweekly.android.data.remote.network.api.nonfeature

import io.ktor.client.engine.mock.MockRequestHandleScope
import io.ktor.client.request.HttpRequestData
import io.ktor.client.request.HttpResponseData
import io.ktor.http.HttpMethod
import kr.hobbly.hobbyweekly.android.data.remote.network.api.ApiRoute
import kr.hobbly.hobbyweekly.android.data.remote.network.api.ApiTestConstant

class FileApiTestRoute : ApiRoute {

    override fun MockRequestHandleScope.routing(
        request: HttpRequestData
    ): HttpResponseData? {
        val method = request.method
        val domain = request.url.host
        val path = request.url.encodedPath

        return when {
            method == HttpMethod.Get && domain.endsWith(ApiTestConstant.SERVER_HOST) && path == "/v1/util/s3/url" -> {
                getPreSignedUrlList(request)
            }

            method == HttpMethod.Get && domain == ApiTestConstant.S3_HOST -> {
                upload(request)
            }

            else -> null
        }
    }

    private fun MockRequestHandleScope.getPreSignedUrlList(
        request: HttpRequestData
    ): HttpResponseData {
        val imageNum = request.url.parameters["imageNum"]?.toInt()
        val isSuccess = imageNum?.let { it >= 0 } == true

        return if (isSuccess) {
            respond(
                content = ByteReadChannel(
                    Json.encodeToString(
                        GetPreSignedUrlListRes(
                            preSignedUrlList = List(imageNum.toInt()) { "https://${ApiTestConstant.S3_HOST}/$it" }
                        )
                    )
                ),
                status = HttpStatusCode.OK,
                headers = defaultHeaders
            )
        } else {
            defaultError
        }
    }

    private fun MockRequestHandleScope.upload(
        request: HttpRequestData
    ): HttpResponseData {
        return defaultSuccess
    }
}