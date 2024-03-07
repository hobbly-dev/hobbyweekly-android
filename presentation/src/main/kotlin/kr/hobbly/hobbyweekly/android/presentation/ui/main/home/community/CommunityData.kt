package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.community

import androidx.compose.runtime.Immutable
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.Community
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.Post

@Immutable
data class CommunityData(
    val myCommunity: List<Community>,
    val popularCommunity: List<Community>,
    val popularPost: List<Post>
)
