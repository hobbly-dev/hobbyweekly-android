package kr.hobbly.hobbyweekly.android.data.remote.network.model.nonfeature.file

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kr.hobbly.hobbyweekly.android.data.remote.mapper.DataMapper
import kr.hobbly.hobbyweekly.android.domain.model.nonfeature.file.PreSignedUrl

@Serializable
data class GetPreSignedUrlListRes(
    @SerialName("presignedUrls")
    val presignedUrls: List<String>
) : DataMapper<List<PreSignedUrl>> {
    override fun toDomain(): List<PreSignedUrl> {
        return presignedUrls.map {
            PreSignedUrl(
                preSignedUrl = it,
                uploadFileUrl = it.substringBefore('?')
            )
        }
    }
}
