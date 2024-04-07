package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.mypage

import androidx.compose.runtime.Immutable
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.Block
import kr.hobbly.hobbyweekly.android.domain.model.feature.routine.RoutineStatistics
import kr.hobbly.hobbyweekly.android.domain.model.nonfeature.user.Profile

@Immutable
data class MyPageData(
    val profile: Profile,
    val myBlockList: List<Block>,
    val routineStatisticsList: List<RoutineStatistics>
)
