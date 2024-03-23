package kr.hobbly.hobbyweekly.android.domain.model.feature.community


data class Member(
    val id: Long,
    val nickname: String,
    val image: String
) {
    companion object {
        val empty = Member(
            id = -1,
            nickname = "",
            image = ""
        )
    }
}
