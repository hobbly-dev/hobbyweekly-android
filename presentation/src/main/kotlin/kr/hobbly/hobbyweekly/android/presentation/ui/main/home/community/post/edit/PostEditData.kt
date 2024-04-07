package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.community.post.edit

import androidx.compose.runtime.Immutable

@Immutable
data class PostEditData(
    val blockId: Long,
    val boardId: Long,
    val postId: Long,
    val routineId: Long
)
