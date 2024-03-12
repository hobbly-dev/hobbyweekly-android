package kr.hobbly.hobbyweekly.android.domain.model.feature.community


data class Member(
    val id: Long,
    val name: String,
    val thumbnail: String
) {
    companion object {
        val empty = Member(
            id = -1,
            name = "",
            thumbnail = ""
        )
    }
}
