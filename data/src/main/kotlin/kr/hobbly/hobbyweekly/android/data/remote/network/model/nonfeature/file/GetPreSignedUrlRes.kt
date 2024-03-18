package kr.hobbly.hobbyweekly.android.data.remote.network.model.nonfeature.file

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetPreSignedUrlRes(
    @SerialName("preSignedUrl")
    val preSignedUrl: String,
    @SerialName("uploadFileUrl")
    val uploadFileUrl: String
)
