package kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchBlockRes(
    @SerialName("result")
    val result: List<BlockRes>,
    @SerialName("hasPrevious")
    val hasPrevious: Boolean,
    @SerialName("hasNext")
    val hasNext: Boolean,
    @SerialName("currentPage")
    val currentPage: Int,
    @SerialName("sort")
    val sort: SearchBlockSortRes
)

@Serializable
data class SearchBlockSortRes(
    @SerialName("sorted")
    val sorted: Boolean,
    @SerialName("direction")
    val direction: String,
    @SerialName("orderProperty")
    val orderProperty: String
)
