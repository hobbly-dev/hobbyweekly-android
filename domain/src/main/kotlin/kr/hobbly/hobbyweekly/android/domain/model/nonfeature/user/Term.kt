package kr.hobbly.hobbyweekly.android.domain.model.nonfeature.user

data class Term(
    val id: Long,
    val name: String,
    val isRequired: Boolean,
    val url: String
)
