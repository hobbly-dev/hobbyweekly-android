package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.community.board.search

import androidx.compose.runtime.Immutable
import androidx.paging.compose.LazyPagingItems
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.Post

@Immutable
data class BoardSearchData(
    val searchPostPaging: LazyPagingItems<Post>
)
