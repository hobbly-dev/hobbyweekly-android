package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.community

import androidx.compose.runtime.Immutable
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.Block
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.Post

@Immutable
data class CommunityData(
    val myBlock: List<Block>,
    val popularBlock: List<Block>,
    val popularPost: List<Post>
)
