package kr.hobbly.hobbyweekly.android.data.remote.network.api.nonfeature

import android.net.Uri
import io.ktor.client.HttpClient
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import java.io.File
import javax.inject.Inject
import kr.hobbly.hobbyweekly.android.data.remote.network.di.AuthHttpClient
import kr.hobbly.hobbyweekly.android.data.remote.network.di.NoAuthHttpClient
import kr.hobbly.hobbyweekly.android.data.remote.network.environment.BaseUrlProvider
import kr.hobbly.hobbyweekly.android.data.remote.network.environment.ErrorMessageMapper
import kr.hobbly.hobbyweekly.android.data.remote.network.model.nonfeature.file.GetPreSignedUrlListRes
import kr.hobbly.hobbyweekly.android.data.remote.network.util.convert

class FileApi @Inject constructor(
    @NoAuthHttpClient private val noAuthClient: HttpClient,
    @AuthHttpClient private val client: HttpClient,
    private val baseUrlProvider: BaseUrlProvider,
    private val errorMessageMapper: ErrorMessageMapper
) {
    private val baseUrl: String
        get() = baseUrlProvider.get()

    suspend fun getPreSignedUrlList(
        count: Int
    ): Result<GetPreSignedUrlListRes> {
        return client.get("$baseUrl/v1/utils/s3") {
            parameter("count", count)
        }.convert(errorMessageMapper::map)
    }

    suspend fun upload(
        preSignedUrl: String,
        imageUri: String,
        fileName: String? = null
    ): Result<Unit> {
        val image = Uri.parse(imageUri)?.path ?: let {
            return Result.failure(IllegalArgumentException("Invalid imageUri"))
        }
        val file = File(image)
        val name = fileName ?: file.name

        return noAuthClient.submitFormWithBinaryData(
            url = preSignedUrl,
            formData = formData {
                append(
                    "image",
                    file.readBytes(),
                    Headers.build {
                        append(HttpHeaders.ContentType, "image/jpeg")
                        append(HttpHeaders.ContentDisposition, "filename=$name")
                    }
                )
            },
            block = {
                method = HttpMethod.Put
            }
        ).convert(errorMessageMapper::map)
    }
}
