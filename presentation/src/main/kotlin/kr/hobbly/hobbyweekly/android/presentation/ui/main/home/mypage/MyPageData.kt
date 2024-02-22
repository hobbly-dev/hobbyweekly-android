package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.mypage

import androidx.compose.runtime.Immutable
import kr.hobbly.hobbyweekly.android.domain.model.nonfeature.user.Profile

@Immutable
data class MyPageData(
    val profile: Profile
)
