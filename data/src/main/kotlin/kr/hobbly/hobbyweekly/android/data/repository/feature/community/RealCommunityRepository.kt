package kr.hobbly.hobbyweekly.android.data.repository.feature.community

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kr.hobbly.hobbyweekly.android.data.common.DEFAULT_PAGING_SIZE
import kr.hobbly.hobbyweekly.android.data.remote.network.api.feature.CommunityApi
import kr.hobbly.hobbyweekly.android.data.remote.network.util.toDomain
import kr.hobbly.hobbyweekly.android.data.repository.feature.community.paging.SearchBlockPagingSource
import kr.hobbly.hobbyweekly.android.data.repository.feature.community.paging.SearchBoardCommentPagingSource
import kr.hobbly.hobbyweekly.android.data.repository.feature.community.paging.SearchBoardPostPagingSource
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.Block
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.Board
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.BoardComment
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.BoardPost
import kr.hobbly.hobbyweekly.android.domain.repository.nonfeature.CommunityRepository

class RealCommunityRepository @Inject constructor(
    private val communityApi: CommunityApi
) : CommunityRepository {
    override suspend fun getBlock(
        id: Long
    ): Result<Block> {
        return communityApi.getBlock(
            id = id
        ).toDomain()
    }

    override suspend fun getPopularBlockList(): Result<List<Block>> {
        return communityApi.getPopularBlockList().toDomain()
    }

    override suspend fun getRecommendBlockList(): Result<List<Block>> {
        return communityApi.getRecommendBlockList().toDomain()
    }

    override suspend fun searchBlockPaging(
        keyword: String
    ): Flow<PagingData<Block>> {
        return Pager(
            config = PagingConfig(
                pageSize = DEFAULT_PAGING_SIZE,
                enablePlaceholders = true
            ),
            pagingSourceFactory = {
                SearchBlockPagingSource(
                    communityApi = communityApi,
                    keyword = keyword
                )
            },
        ).flow
    }

    override suspend fun getBoardList(
        id: Long
    ): Result<List<Board>> {
        return communityApi.getBoardList(
            id = id
        ).toDomain()
    }

    override suspend fun getMyBlockList(): Result<List<Block>> {
        return communityApi.getMyBlockList().toDomain()
    }

    override suspend fun addMyBlock(
        id: Long
    ): Result<Unit> {
        return communityApi.addMyBlock(
            id = id
        )
    }

    override suspend fun removeMyBlock(
        id: Long
    ): Result<Unit> {
        return communityApi.removeMyBlock(
            id = id
        )
    }

    override suspend fun writeBoardPost(
        id: Long,
        title: String,
        content: String,
        isAnonymous: Boolean,
        isSecret: Boolean,
        imageList: List<String>
    ): Result<Long> {
        return communityApi.writeBoardPost(
            id = id,
            title = title,
            content = content,
            isAnonymous = isAnonymous,
            isSecret = isSecret,
            imageList = imageList
        ).toDomain()
    }

    override suspend fun writeRoutinePost(
        id: Long,
        title: String,
        content: String,
        isAnonymous: Boolean,
        isSecret: Boolean,
        imageList: List<String>
    ): Result<Long> {
        return communityApi.writeRoutinePost(
            id = id,
            title = title,
            content = content,
            isAnonymous = isAnonymous,
            isSecret = isSecret,
            imageList = imageList
        ).toDomain()
    }

    override suspend fun writeNoticePost(
        id: Long,
        title: String,
        content: String,
        isAnonymous: Boolean,
        imageList: List<String>
    ): Result<Long> {
        return communityApi.writeNoticePost(
            id = id,
            title = title,
            content = content,
            isAnonymous = isAnonymous,
            imageList = imageList
        ).toDomain()
    }

    override suspend fun searchBoardPostPaging(
        id: Long,
        keyword: String
    ): Flow<PagingData<BoardPost>> {
        return Pager(
            config = PagingConfig(
                pageSize = DEFAULT_PAGING_SIZE,
                enablePlaceholders = true
            ),
            pagingSourceFactory = {
                SearchBoardPostPagingSource(
                    communityApi = communityApi,
                    id = id,
                    keyword = keyword
                )
            },
        ).flow
    }

    override suspend fun loadBoardPost(
        id: Long
    ): Result<BoardPost> {
        return communityApi.loadBoardPost(
            id = id
        ).toDomain()
    }

    override suspend fun editBoardPost(
        id: Long,
        title: String,
        content: String,
        isAnonymous: Boolean,
        isSecret: Boolean,
        imageList: List<String>
    ): Result<Unit> {
        return communityApi.editBoardPost(
            id = id,
            title = title,
            content = content,
            isAnonymous = isAnonymous,
            isSecret = isSecret,
            imageList = imageList
        )
    }

    override suspend fun editRoutinePost(
        id: Long,
        title: String,
        content: String,
        isAnonymous: Boolean,
        isSecret: Boolean,
        imageList: List<String>
    ): Result<Unit> {
        return communityApi.editRoutinePost(
            id = id,
            title = title,
            content = content,
            isAnonymous = isAnonymous,
            isSecret = isSecret,
            imageList = imageList
        )
    }

    override suspend fun editNoticePost(
        id: Long,
        title: String,
        content: String,
        isAnonymous: Boolean,
        imageList: List<String>
    ): Result<Unit> {
        return communityApi.editNoticePost(
            id = id,
            title = title,
            content = content,
            isAnonymous = isAnonymous,
            imageList = imageList
        )
    }

    override suspend fun removeBoardPost(
        id: Long
    ): Result<Unit> {
        return communityApi.removeBoardPost(
            id = id
        )
    }

    override suspend fun removeRoutinePost(
        id: Long
    ): Result<Unit> {
        return communityApi.removeRoutinePost(
            id = id
        )
    }

    override suspend fun removeNoticePost(
        id: Long
    ): Result<Unit> {
        return communityApi.removeNoticePost(
            id = id
        )
    }

    override suspend fun likeBoardPost(
        id: Long
    ): Result<Unit> {
        return communityApi.likeBoardPost(
            id = id
        )
    }

    override suspend fun likeRoutinePost(
        id: Long
    ): Result<Unit> {
        return communityApi.likeRoutinePost(
            id = id
        )
    }

    override suspend fun likeNoticePost(
        id: Long
    ): Result<Unit> {
        return communityApi.likeNoticePost(
            id = id
        )
    }

    override suspend fun reportBoardPost(
        id: Long,
        reason: String
    ): Result<Unit> {
        return communityApi.reportBoardPost(
            id = id,
            reason = reason
        )
    }

    override suspend fun reportRoutinePost(
        id: Long,
        reason: String
    ): Result<Unit> {
        return communityApi.reportRoutinePost(
            id = id,
            reason = reason
        )
    }

    override suspend fun reportNoticePost(
        id: Long,
        reason: String
    ): Result<Unit> {
        return communityApi.reportNoticePost(
            id = id,
            reason = reason
        )
    }

    override suspend fun loadBoardCommentPaging(
        id: Long,
        keyword: String
    ): Flow<PagingData<BoardComment>> {
        return Pager(
            config = PagingConfig(
                pageSize = DEFAULT_PAGING_SIZE,
                enablePlaceholders = true
            ),
            pagingSourceFactory = {
                SearchBoardCommentPagingSource(
                    communityApi = communityApi,
                    id = id,
                    keyword = keyword
                )
            },
        ).flow
    }

    override suspend fun writeBoardComment(
        id: Long,
        content: String,
        isAnonymous: Boolean
    ): Result<Unit> {
        return communityApi.writeBoardComment(
            id = id,
            content = content,
            isAnonymous = isAnonymous
        )
    }

    override suspend fun writeRoutineComment(
        id: Long,
        content: String,
        isAnonymous: Boolean
    ): Result<Unit> {
        return communityApi.writeRoutineComment(
            id = id,
            content = content,
            isAnonymous = isAnonymous
        )
    }

    override suspend fun writeNoticeComment(
        id: Long,
        content: String,
        isAnonymous: Boolean
    ): Result<Unit> {
        return communityApi.writeNoticeComment(
            id = id,
            content = content,
            isAnonymous = isAnonymous
        )
    }

    override suspend fun removeComment(
        id: Long
    ): Result<Unit> {
        return communityApi.removeComment(
            id = id
        )
    }

    override suspend fun reportComment(
        id: Long,
        reason: String
    ): Result<Unit> {
        return communityApi.reportComment(
            id = id,
            reason = reason
        )
    }

    override suspend fun writeCommentReply(
        id: Long,
        content: String,
        isAnonymous: Boolean
    ): Result<Unit> {
        return communityApi.writeCommentReply(
            id = id,
            content = content,
            isAnonymous = isAnonymous
        )
    }

    override suspend fun likeComment(
        id: Long
    ): Result<Unit> {
        return communityApi.likeComment(
            id = id
        )
    }

}
