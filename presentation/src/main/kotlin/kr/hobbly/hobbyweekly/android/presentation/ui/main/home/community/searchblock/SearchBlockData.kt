package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.community.searchblock

import androidx.compose.runtime.Immutable
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.Block

@Immutable
data class SearchBlockData(
    val suggestBlockList: List<Block>,
    val searchBlockList: List<Block>
)
