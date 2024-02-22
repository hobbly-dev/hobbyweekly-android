package kr.hobbly.hobbyweekly.android.data.remote.network.model.nonfeature.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kr.hobbly.hobbyweekly.android.data.remote.mapper.DataMapper
import kr.hobbly.hobbyweekly.android.domain.model.nonfeature.user.Profile

@Serializable
data class ProfileRes(
    @SerialName("id")
    val id: Long,
    @SerialName("name")
    val name: String,
    @SerialName("nickname")
    val nickname: String,
    @SerialName("email")
    val email: String
) : DataMapper<Profile> {
    override fun toDomain(): Profile {
        return Profile(
            id = id,
            name = name,
            nickname = nickname,
            email = email
        )
    }
}
