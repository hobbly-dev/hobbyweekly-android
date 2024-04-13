package kr.hobbly.hobbyweekly.android.data.remote.network.util

import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.sentry.Sentry
import io.sentry.SentryLevel
import kr.hobbly.hobbyweekly.android.data.remote.mapper.DataMapper
import kr.hobbly.hobbyweekly.android.data.remote.network.environment.ErrorMessageMapper
import kr.hobbly.hobbyweekly.android.data.remote.network.model.nonfeature.error.ErrorRes
import kr.hobbly.hobbyweekly.android.domain.model.nonfeature.error.BadRequestServerException
import kr.hobbly.hobbyweekly.android.domain.model.nonfeature.error.InternalServerException
import kr.hobbly.hobbyweekly.android.domain.model.nonfeature.error.InvalidStandardResponseException
import timber.log.Timber

val HttpResponse.isSuccessful: Boolean
    get() = status.value in 200..299

val HttpResponse.isBadRequest: Boolean
    get() = status.value in 400..499


val HttpResponse.isInternalServerError: Boolean
    get() = status.value in 500..599

inline fun <reified T : DataMapper<R>, R : Any> Result<T>.toDomain(): Result<R> {
    return map { it.toDomain() }
}

suspend inline fun <reified T : Any> HttpResponse.convert(
    noinline errorMessageMapper: (Long) -> String
): Result<T> {
    return runCatching {
        if (isSuccessful) {
            return@runCatching body<T?>()
                ?: throw InvalidStandardResponseException("Response Empty Body")
        } else {
            throw this.toThrowable(errorMessageMapper)
        }
    }.onFailure { exception ->
        Timber.d(exception)
        Sentry.withScope {
            it.level = SentryLevel.INFO
            Sentry.captureException(exception)
        }
        Firebase.crashlytics.recordException(exception)
    }
}

suspend inline fun HttpResponse.toThrowable(
    noinline errorMessageMapper: (Long) -> String
): Throwable {
    return runCatching {
        if (isInternalServerError) {
            return@runCatching InternalServerException(
                ErrorMessageMapper.KEY_INTERNAL_SERVER_ERROR.toString(),
                errorMessageMapper(ErrorMessageMapper.KEY_INTERNAL_SERVER_ERROR)
            )
        }

        return@runCatching this.body<ErrorRes?>()?.let { errorRes ->
            BadRequestServerException(
                errorRes.detailStatusCode.toString(),
                errorMessageMapper(errorRes.detailStatusCode)
            )
        } ?: InvalidStandardResponseException("Response Empty Body")
    }.getOrElse { exception ->
        exception
    }
}
