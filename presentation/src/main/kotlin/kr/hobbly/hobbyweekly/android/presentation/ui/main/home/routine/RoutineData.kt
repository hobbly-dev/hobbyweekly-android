package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.routine

import androidx.compose.runtime.Immutable
import kr.hobbly.hobbyweekly.android.domain.model.nonfeature.user.Profile

@Immutable
data class RoutineData(
    val profile: Profile
)
