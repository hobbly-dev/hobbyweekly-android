package kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BoardRes(
    @SerialName("boardId")
    val boardId: Long,
    @SerialName("title")
    val title: String
)
