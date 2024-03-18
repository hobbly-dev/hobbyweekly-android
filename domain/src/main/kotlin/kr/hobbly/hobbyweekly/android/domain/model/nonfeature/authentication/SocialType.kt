package kr.hobbly.hobbyweekly.android.domain.model.nonfeature.authentication

sealed class SocialType(
    val value: String
) {
    data object Kakao : SocialType(value = "KAKAO")
}
