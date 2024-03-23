package kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kr.hobbly.hobbyweekly.android.data.remote.mapper.DataMapper
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.Member

@Serializable
data class MemberRes(
    @SerialName("memberId")
    val memberId: Long,
    @SerialName("nickname")
    val nickname: String = "",
    @SerialName("image")
    val image: String = ""
) : DataMapper<Member> {
    override fun toDomain(): Member {
        return Member(
            id = memberId,
            nickname = nickname,
            image = image
        )
    }
}
