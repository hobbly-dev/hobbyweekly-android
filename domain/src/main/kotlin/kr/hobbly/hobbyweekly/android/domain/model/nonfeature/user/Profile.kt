package kr.hobbly.hobbyweekly.android.domain.model.nonfeature.user

data class Profile(
    val id: Long,
    val nickname: String,
    val image: String,
    val isHobbyChecked: Boolean
) {
    companion object {
        val empty = Profile(
            id = 0,
            nickname = "",
            image = "",
            isHobbyChecked = true
        )
    }
}
