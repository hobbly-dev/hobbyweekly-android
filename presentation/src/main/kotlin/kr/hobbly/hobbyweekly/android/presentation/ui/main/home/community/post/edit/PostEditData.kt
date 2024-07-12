package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.community.post.edit

import androidx.compose.runtime.Immutable
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.Block
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.Board

@Immutable
data class PostEditData(
    val block: Block,
    val board: Board,
    val blockId: Long,
    val boardId: Long,
    val postId: Long,
    val routineId: Long,
    val newImageUriList: List<String>
)
