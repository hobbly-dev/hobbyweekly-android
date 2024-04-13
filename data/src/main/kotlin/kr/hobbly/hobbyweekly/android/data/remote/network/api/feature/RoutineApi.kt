package kr.hobbly.hobbyweekly.android.data.remote.network.api.feature

import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import javax.inject.Inject
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kr.hobbly.hobbyweekly.android.data.remote.network.di.AuthHttpClient
import kr.hobbly.hobbyweekly.android.data.remote.network.environment.BaseUrlProvider
import kr.hobbly.hobbyweekly.android.data.remote.network.environment.ErrorMessageMapper
import kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.routine.AddRoutineReq
import kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.routine.AddRoutineRes
import kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.routine.EditRoutineReq
import kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.routine.GetCurrentRoutineRes
import kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.routine.GetLatestRoutineRes
import kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.routine.GetRoutineStatisticsRes
import kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.routine.RoutineRes
import kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.routine.SwitchRoutineAlarmReq
import kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.routine.WriteRoutinePostReq
import kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.routine.WriteRoutinePostRes
import kr.hobbly.hobbyweekly.android.data.remote.network.util.convert

class RoutineApi @Inject constructor(
    @AuthHttpClient private val client: HttpClient,
    private val baseUrlProvider: BaseUrlProvider,
    private val errorMessageMapper: ErrorMessageMapper
) {
    private val baseUrl: String
        get() = baseUrlProvider.get()

    suspend fun getCurrentRoutineList(): Result<GetCurrentRoutineRes> {
        return client.get("$baseUrl/v1/routines/me")
            .convert(errorMessageMapper::map)
    }

    suspend fun getRoutine(
        id: Long
    ): Result<RoutineRes> {
        return client.get("$baseUrl/v1/routines/$id")
            .convert(errorMessageMapper::map)
    }

    suspend fun writeRoutinePost(
        id: Long,
        title: String,
        content: String,
        isAnonymous: Boolean,
        isSecret: Boolean,
        imageList: List<String>
    ): Result<WriteRoutinePostRes> {
        return client.post("$baseUrl/v1/routines/posts/$id") {
            setBody(
                WriteRoutinePostReq(
                    title = title,
                    content = content,
                    isAnonymous = isAnonymous,
                    isSecret = isSecret,
                    images = imageList
                )
            )
        }.convert(errorMessageMapper::map)
    }

    suspend fun getLatestRoutineList(): Result<GetLatestRoutineRes> {
        return client.get("$baseUrl/v1/routines/me/latest")
            .convert(errorMessageMapper::map)
    }

    suspend fun addRoutine(
        title: String,
        blockId: Long,
        daysOfWeek: List<Int>,
        alarmTime: LocalTime?
    ): Result<AddRoutineRes> {
        return client.post("$baseUrl/v1/routines") {
            setBody(
                AddRoutineReq(
                    title = title,
                    blockId = blockId,
                    daysOfWeek = daysOfWeek,
                    alarmTime = alarmTime
                )
            )
        }.convert(errorMessageMapper::map)
    }

    suspend fun editRoutine(
        id: Long,
        daysOfWeek: List<Int>,
        alarmTime: LocalTime?
    ): Result<Unit> {
        return client.put("$baseUrl/v1/routines/$id") {
            setBody(
                EditRoutineReq(
                    daysOfWeek = daysOfWeek,
                    alarmTime = alarmTime
                )
            )
        }.convert(errorMessageMapper::map)
    }

    suspend fun removeRoutine(
        id: Long
    ): Result<Unit> {
        return client.post("$baseUrl/v1/routines/$id/terminate")
            .convert(errorMessageMapper::map)
    }

    suspend fun switchRoutineAlarm(
        id: Long,
        isEnabled: Boolean
    ): Result<Unit> {
        return client.patch("$baseUrl/v1/routines/$id/alarm") {
            setBody(
                SwitchRoutineAlarmReq(
                    isAlarmOn = isEnabled
                )
            )
        }.convert(errorMessageMapper::map)
    }

    suspend fun getRoutineStatisticsList(
        id: Long,
        date: LocalDate
    ): Result<GetRoutineStatisticsRes> {
        return client.get("$baseUrl/v1/routines/me/statistics") {
            parameter("blockId", id.takeIf { it != -1L })
            parameter("date", date) // TODO : DateTime String Format 체크
        }.convert(errorMessageMapper::map)
    }
}
