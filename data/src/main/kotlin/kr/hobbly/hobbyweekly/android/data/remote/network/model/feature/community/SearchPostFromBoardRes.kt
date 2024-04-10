package kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchPostFromBlockRes(
    @SerialName("result")
    val result: List<PostRes>,
    @SerialName("hasPrevious")
    val hasPrevious: Boolean,
    @SerialName("hasNext")
    val hasNext: Boolean,
    @SerialName("currentPage")
    val currentPage: Int,
    @SerialName("sort")
    val sort: SearchPostFromBlockSortRes
)

@Serializable
data class SearchPostFromBlockSortRes(
    @SerialName("sorted")
    val sorted: Boolean,
    @SerialName("direction")
    val direction: String,
    @SerialName("orderProperty")
    val orderProperty: String
)
