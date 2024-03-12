package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.routine.block

import androidx.compose.runtime.Immutable
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.Block

@Immutable
data class RoutineBlockData(
    val myBlockList: List<Block>
)
