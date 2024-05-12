package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.routine.edit

import androidx.compose.runtime.Immutable
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.Block

@Immutable
data class RoutineEditData(
    val block: Block,
    val isEditMode: Boolean
)
