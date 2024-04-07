package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.mypage.statistics

import androidx.compose.runtime.Immutable
import kr.hobbly.hobbyweekly.android.domain.model.feature.routine.RoutineStatistics

@Immutable
data class MyPageStatisticsData(
    val routineStatisticsList: List<RoutineStatistics>
)
