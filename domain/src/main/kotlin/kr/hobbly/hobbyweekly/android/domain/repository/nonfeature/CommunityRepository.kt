package kr.hobbly.hobbyweekly.android.domain.repository.nonfeature

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.Block
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.Board
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.BoardComment
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.BoardPost

interface CommunityRepository {

    suspend fun getBlock(
        id: Long
    ): Result<Block>

    suspend fun getPopularBlockList(): Result<List<Block>>

    suspend fun getRecommendBlockList(): Result<List<Block>>

    suspend fun searchBlockPaging(
        keyword: String
    ): Flow<PagingData<Block>>

    suspend fun getBoardList(
        id: Long
    ): Result<List<Board>>

    suspend fun getMyBlockList(): Result<List<Block>>

    suspend fun addMyBlock(
        id: Long
    ): Result<Unit>

    suspend fun removeMyBlock(
        id: Long
    ): Result<Unit>

    suspend fun writeBoardPost(
        id: Long,
        title: String,
        content: String,
        isAnonymous: Boolean,
        isSecret: Boolean,
        images: List<String>
    ): Result<Long>

    suspend fun writeRoutinePost(
        id: Long,
        title: String,
        content: String,
        isAnonymous: Boolean,
        isSecret: Boolean,
        images: List<String>
    ): Result<Long>

    suspend fun writeNoticePost(
        id: Long,
        title: String,
        content: String,
        isAnonymous: Boolean,
        images: List<String>
    ): Result<Long>

    suspend fun searchBoardPostPaging(
        id: Long,
        keyword: String
    ): Flow<PagingData<BoardPost>>

//    suspend fun searchRoutinePostPaging(
//        id: Long,
//        keyword: String
//    ): Flow<PagingData<RoutinePost>>
//
//    suspend fun searchNoticePostPaging(
//        id: Long,
//        keyword: String
//    ): Flow<PagingData<NoticePost>>

    suspend fun loadBoardPost(
        id: Long
    ): Result<BoardPost>

//    suspend fun loadRoutinePost(
//        id: Long
//    ): Result<RoutinePost>
//
//    suspend fun loadNoticePost(
//        id: Long
//    ): Result<NoticePost>

    suspend fun editBoardPost(
        id: Long,
        title: String,
        content: String,
        isAnonymous: Boolean,
        isSecret: Boolean,
        images: List<String>
    ): Result<Unit>

    suspend fun editRoutinePost(
        id: Long,
        title: String,
        content: String,
        isAnonymous: Boolean,
        isSecret: Boolean,
        images: List<String>
    ): Result<Unit>

    suspend fun editNoticePost(
        id: Long,
        title: String,
        content: String,
        isAnonymous: Boolean,
        images: List<String>
    ): Result<Unit>

    suspend fun removeBoardPost(
        id: Long
    ): Result<Unit>

    suspend fun removeRoutinePost(
        id: Long
    ): Result<Unit>

    suspend fun removeNoticePost(
        id: Long
    ): Result<Unit>

    suspend fun likeBoardPost(
        id: Long
    ): Result<Unit>

    suspend fun likeRoutinePost(
        id: Long
    ): Result<Unit>

    suspend fun likeNoticePost(
        id: Long
    ): Result<Unit>

    suspend fun reportBoardPost(
        id: Long,
        reason: String
    ): Result<Unit>

    suspend fun reportRoutinePost(
        id: Long,
        reason: String
    ): Result<Unit>

    suspend fun reportNoticePost(
        id: Long,
        reason: String
    ): Result<Unit>

    suspend fun loadBoardCommentPaging(
        id: Long,
        keyword: String
    ): Flow<PagingData<BoardComment>>

//    suspend fun loadRoutineCommentPaging(
//        id: Long,
//        keyword: String
//    ): Flow<PagingData<RoutineComment>>
//
//    suspend fun loadNoticeCommentPaging(
//        id: Long,
//        keyword: String
//    ): Flow<PagingData<NoticeComment>>

    suspend fun writeBoardComment(
        id: Long,
        content: String,
        isAnonymous: Boolean
    ): Result<Unit>

    suspend fun writeRoutineComment(
        id: Long,
        content: String,
        isAnonymous: Boolean
    ): Result<Unit>

    suspend fun writeNoticeComment(
        id: Long,
        content: String,
        isAnonymous: Boolean
    ): Result<Unit>

    suspend fun removeComment(
        id: Long
    ): Result<Unit>

    suspend fun reportComment(
        id: Long,
        reason: String
    ): Result<Unit>

    suspend fun writeCommentReply(
        id: Long,
        content: String,
        isAnonymous: Boolean
    ): Result<Unit>

    suspend fun likeComment(
        id: Long
    ): Result<Unit>
}
