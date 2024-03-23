package kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoadBoardCommentRes(
    @SerialName("result")
    val result: List<BoardCommentRes>,
    @SerialName("hasPrevious")
    val hasPrevious: Boolean,
    @SerialName("hasNext")
    val hasNext: Boolean,
    @SerialName("currentPage")
    val currentPage: Int,
    @SerialName("sort")
    val sort: LoadBoardCommentSortRes
)

@Serializable
data class LoadBoardCommentSortRes(
    @SerialName("sorted")
    val sorted: Boolean,
    @SerialName("direction")
    val direction: String,
    @SerialName("orderProperty")
    val orderProperty: String
)
