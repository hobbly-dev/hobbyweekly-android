package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.community.board.search

import androidx.compose.runtime.Immutable
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.Post

@Immutable
data class BoardSearchData(
    val searchPostList: List<Post>
)
