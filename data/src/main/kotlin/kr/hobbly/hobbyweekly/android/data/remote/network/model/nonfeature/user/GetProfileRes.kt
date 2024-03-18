package kr.hobbly.hobbyweekly.android.data.remote.network.model.nonfeature.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kr.hobbly.hobbyweekly.android.data.remote.mapper.DataMapper
import kr.hobbly.hobbyweekly.android.domain.model.nonfeature.user.Profile

@Serializable
data class GetProfileRes(
    @SerialName("id")
    val id: Long,
    @SerialName("nickname")
    val nickname: String = "",
    @SerialName("image")
    val image: String = "",
    @SerialName("isHobbyChecked")
    val isHobbyChecked: Boolean
) : DataMapper<Profile> {
    override fun toDomain(): Profile {
        return Profile(
            id = id,
            nickname = nickname,
            image = image,
            isHobbyChecked = isHobbyChecked
        )
    }
}
