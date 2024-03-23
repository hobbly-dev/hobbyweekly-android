package kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NoticePostRes(
    @SerialName("postId")
    val postId: Long,
    @SerialName("member")
    val member: MemberRes,
    @SerialName("title")
    val title: String,
    @SerialName("content")
    val content: String,
    @SerialName("images")
    val images: List<String>,
    @SerialName("likeCount")
    val likeCount: Int,
    @SerialName("commentCount")
    val commentCount: Int,
    @SerialName("isAnonymous")
    val isAnonymous: Boolean
)
