package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.community.post

import androidx.compose.runtime.Immutable

@Immutable
data class PostData(
    val blockId: Long,
    val boardId: Long,
    val postId: Long
)
