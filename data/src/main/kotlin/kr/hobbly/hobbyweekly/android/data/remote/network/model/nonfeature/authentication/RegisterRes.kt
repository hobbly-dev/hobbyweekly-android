package kr.hobbly.hobbyweekly.android.data.remote.network.model.nonfeature.authentication

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kr.hobbly.hobbyweekly.android.data.remote.mapper.DataMapper

@Serializable
data class RegisterRes(
    @SerialName("id")
    val id: Long,
    @SerialName("access_token")
    val accessToken: String,
    @SerialName("refresh_token")
    val refreshToken: String
) : DataMapper<Long> {
    override fun toDomain(): Long {
        return id
    }
}
