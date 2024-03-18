package kr.hobbly.hobbyweekly.android.data.remote.network.model.nonfeature.file

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kr.hobbly.hobbyweekly.android.data.remote.mapper.DataMapper
import kr.hobbly.hobbyweekly.android.domain.model.nonfeature.file.PreSignedUrl

@Serializable
data class GetPreSignedUrlListRes(
    @SerialName("urlList")
    val urlList: List<GetPreSignedUrlItemRes>
) : DataMapper<List<PreSignedUrl>> {
    override fun toDomain(): List<PreSignedUrl> {
        return urlList.map { it.toDomain() }
    }
}

@Serializable
data class GetPreSignedUrlItemRes(
    @SerialName("preSignedUrl")
    val preSignedUrl: String,
    @SerialName("uploadFileUrl")
    val uploadFileUrl: String
) : DataMapper<PreSignedUrl> {
    override fun toDomain(): PreSignedUrl {
        return PreSignedUrl(
            preSignedUrl = preSignedUrl,
            uploadFileUrl = uploadFileUrl
        )
    }
}
