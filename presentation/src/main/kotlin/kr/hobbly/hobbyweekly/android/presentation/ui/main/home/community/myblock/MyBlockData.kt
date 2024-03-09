package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.community.myblock

import androidx.compose.runtime.Immutable
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.Block

@Immutable
data class MyBlockData(
    val myBlockList: List<Block>
)
