package kr.hobbly.hobbyweekly.android.data.remote.network.model.nonfeature.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kr.hobbly.hobbyweekly.android.data.remote.mapper.DataMapper
import kr.hobbly.hobbyweekly.android.domain.model.nonfeature.user.Term

@Serializable
data class GetTermListRes(
    @SerialName("terms")
    val terms: List<GetTermItemRes>
) : DataMapper<List<Term>> {
    override fun toDomain(): List<Term> {
        return terms.map { it.toDomain() }
    }
}

@Serializable
data class GetTermItemRes(
    @SerialName("id")
    val id: Long,
    @SerialName("name")
    val name: String,
    @SerialName("isRequired")
    val isRequired: Boolean,
    @SerialName("url")
    val url: String
) : DataMapper<Term> {
    override fun toDomain(): Term {
        return Term(
            id = id,
            name = name,
            isRequired = isRequired,
            url = url
        )
    }
}
