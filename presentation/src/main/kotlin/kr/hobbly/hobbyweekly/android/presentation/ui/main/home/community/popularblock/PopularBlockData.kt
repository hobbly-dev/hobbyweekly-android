package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.community.popularblock

import androidx.compose.runtime.Immutable
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.Block

@Immutable
data class PopularBlockData(
    val popularBlockList: List<Block>
)
