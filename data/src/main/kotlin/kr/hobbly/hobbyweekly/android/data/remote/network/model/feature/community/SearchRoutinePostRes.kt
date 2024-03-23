package kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchRoutinePostRes(
    @SerialName("result")
    val result: List<RoutinePostRes>,
    @SerialName("hasPrevious")
    val hasPrevious: Boolean,
    @SerialName("hasNext")
    val hasNext: Boolean,
    @SerialName("currentPage")
    val currentPage: Int,
    @SerialName("sort")
    val sort: SearchRoutinePostSortRes
)

@Serializable
data class SearchRoutinePostSortRes(
    @SerialName("sorted")
    val sorted: Boolean,
    @SerialName("direction")
    val direction: String,
    @SerialName("orderProperty")
    val orderProperty: String
)
