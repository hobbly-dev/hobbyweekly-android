package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.community.board.routine

import androidx.compose.runtime.Immutable
import kr.hobbly.hobbyweekly.android.domain.model.feature.routine.Routine

@Immutable
data class BoardRoutineData(
    val blockId: Long,
    val routineList: List<Routine>
)
