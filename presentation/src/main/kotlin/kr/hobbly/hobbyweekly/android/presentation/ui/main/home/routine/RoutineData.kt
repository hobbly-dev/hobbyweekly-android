package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.routine

import androidx.compose.runtime.Immutable
import kr.hobbly.hobbyweekly.android.domain.model.feature.routine.Routine

@Immutable
data class RoutineData(
    val currentRoutineList: List<Routine>,
    val latestRoutineList: List<Routine>
)
