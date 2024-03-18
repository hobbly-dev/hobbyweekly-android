package kr.hobbly.hobbyweekly.android.data.remote.network.model.nonfeature.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kr.hobbly.hobbyweekly.android.data.remote.mapper.DataMapper
import kr.hobbly.hobbyweekly.android.domain.model.nonfeature.user.AgreedTerm

@Serializable
data class GetTermListAgreeStateListRes(
    @SerialName("terms")
    val terms: List<GetTermListAgreeStateItemRes>
) : DataMapper<List<AgreedTerm>> {
    override fun toDomain(): List<AgreedTerm> {
        return terms.map { it.toDomain() }
    }
}

@Serializable
data class GetTermListAgreeStateItemRes(
    @SerialName("termId")
    val termId: Long,
    @SerialName("name")
    val name: String,
    @SerialName("isAgreed")
    val isAgreed: Boolean
) : DataMapper<AgreedTerm> {
    override fun toDomain(): AgreedTerm {
        return AgreedTerm(
            id = termId,
            name = name,
            isAgreed = isAgreed
        )
    }
}
