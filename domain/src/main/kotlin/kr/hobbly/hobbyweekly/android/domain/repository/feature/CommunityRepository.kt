package kr.hobbly.hobbyweekly.android.domain.repository.feature

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.Block
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.Board
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.Comment
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.Post

interface CommunityRepository {

    suspend fun getBlock(
        id: Long
    ): Result<Block>

    suspend fun getMyBlockList(): Result<List<Block>>

    suspend fun addMyBlock(
        id: Long
    ): Result<Long>

    suspend fun removeMyBlock(
        id: Long
    ): Result<Unit>

    suspend fun searchBlockPaging(
        keyword: String
    ): Flow<PagingData<Block>>

    suspend fun getPopularBlockList(): Result<List<Block>>

    suspend fun getRecommendBlockList(): Result<List<Block>>

    suspend fun getBoardList(
        id: Long
    ): Result<List<Board>>

    suspend fun getPopularPostPaging(): Flow<PagingData<Post>>

    suspend fun getPopularPostFromBlockPaging(
        id: Long
    ): Flow<PagingData<Post>>

    suspend fun searchPostFromBlockPaging(
        id: Long,
        keyword: String
    ): Flow<PagingData<Post>>

    suspend fun getBoard(
        id: Long
    ): Result<Board>

    suspend fun searchPostFromBoardPaging(
        id: Long,
        keyword: String
    ): Flow<PagingData<Post>>

    suspend fun writePost(
        id: Long,
        title: String,
        content: String,
        isAnonymous: Boolean,
        isSecret: Boolean,
        imageList: List<String>
    ): Result<Long>

    suspend fun loadPost(
        id: Long
    ): Result<Post>

    suspend fun editPost(
        id: Long,
        title: String,
        content: String,
        isAnonymous: Boolean,
        isSecret: Boolean,
        imageList: List<String>
    ): Result<Unit>

    suspend fun removePost(
        id: Long
    ): Result<Unit>

    suspend fun likePost(
        id: Long
    ): Result<Unit>

    suspend fun reportPost(
        id: Long,
        reason: String
    ): Result<Unit>

    suspend fun loadCommentPaging(
        id: Long,
    ): Flow<PagingData<Comment>>

    suspend fun writeComment(
        id: Long,
        content: String,
        isAnonymous: Boolean
    ): Result<Long>

    suspend fun removeComment(
        id: Long
    ): Result<Unit>

    suspend fun likeComment(
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
    ): Result<Long>
}
