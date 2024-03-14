package kr.hobbly.hobbyweekly.android.domain.model.nonfeature.user

data class Profile(
    val id: Long,
    val name: String,
    val nickname: String,
    val email: String,
    val thumbnail: String
) {
    companion object {
        val empty = Profile(
            id = 0,
            name = "",
            nickname = "",
            email = "",
            thumbnail = ""
        )
    }
}
