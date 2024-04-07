package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.community

import androidx.compose.runtime.Immutable
import androidx.paging.compose.LazyPagingItems
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.Block
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.Post

@Immutable
data class CommunityData(
    val myBlockList: List<Block>,
    val popularBlockList: List<Block>,
    val popularPostPaging: LazyPagingItems<Post>
)
