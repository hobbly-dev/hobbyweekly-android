package kr.hobbly.hobbyweekly.android.presentation.model.routine

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

data class RoutineStatisticsModel(
    val dayOfWeek: Int,
    val color: Color,
    val x: Dp,
    val y: Dp
)
