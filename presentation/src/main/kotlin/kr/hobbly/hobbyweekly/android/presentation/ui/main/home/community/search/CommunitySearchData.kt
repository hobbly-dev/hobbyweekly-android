package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.community.search

import androidx.compose.runtime.Immutable
import androidx.paging.compose.LazyPagingItems
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.Block

@Immutable
data class CommunitySearchData(
    val suggestBlockList: List<Block>,
    val searchBlockList: LazyPagingItems<Block>
)
