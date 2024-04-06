package kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.routine

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetLatestRoutineRes(
    @SerialName("routines")
    val routines: List<RoutineRes>
)
