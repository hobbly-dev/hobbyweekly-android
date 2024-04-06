package kr.hobbly.hobbyweekly.android.data.repository.feature.community

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kr.hobbly.hobbyweekly.android.data.common.DEFAULT_PAGING_SIZE
import kr.hobbly.hobbyweekly.android.data.remote.network.api.feature.CommunityApi
import kr.hobbly.hobbyweekly.android.data.remote.network.util.toDomain
import kr.hobbly.hobbyweekly.android.data.repository.feature.community.paging.GetPopularPostPagingSource
import kr.hobbly.hobbyweekly.android.data.repository.feature.community.paging.SearchBlockPagingSource
import kr.hobbly.hobbyweekly.android.data.repository.feature.community.paging.SearchCommentPagingSource
import kr.hobbly.hobbyweekly.android.data.repository.feature.community.paging.SearchPostFromBlockPagingSource
import kr.hobbly.hobbyweekly.android.data.repository.feature.community.paging.SearchPostFromBoardPagingSource
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.Block
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.Board
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.Comment
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.Post
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

    override suspend fun getMyBlockList(): Result<List<Block>> {
        return communityApi.getMyBlockList().toDomain()
    }

    override suspend fun addMyBlock(
        id: Long
    ): Result<Long> {
        return communityApi.addMyBlock(
            id = id
        ).toDomain()
    }

    override suspend fun removeMyBlock(
        id: Long
    ): Result<Unit> {
        return communityApi.removeMyBlock(
            id = id
        )
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

    override suspend fun getPopularBlockList(): Result<List<Block>> {
        return communityApi.getPopularBlockList().toDomain()
    }

    override suspend fun getRecommendBlockList(): Result<List<Block>> {
        return communityApi.getRecommendBlockList().toDomain()
    }

    override suspend fun getBoardList(
        id: Long
    ): Result<List<Board>> {
        return communityApi.getBoardList(
            id = id
        ).toDomain()
    }

    override suspend fun getPopularPostPaging(
        id: Long
    ): Flow<PagingData<Post>> {
        return Pager(
            config = PagingConfig(
                pageSize = DEFAULT_PAGING_SIZE,
                enablePlaceholders = true
            ),
            pagingSourceFactory = {
                GetPopularPostPagingSource(
                    communityApi = communityApi,
                    id = id
                )
            },
        ).flow
    }

    override suspend fun searchPostFromBlockPaging(
        id: Long,
        keyword: String
    ): Flow<PagingData<Post>> {
        return Pager(
            config = PagingConfig(
                pageSize = DEFAULT_PAGING_SIZE,
                enablePlaceholders = true
            ),
            pagingSourceFactory = {
                SearchPostFromBlockPagingSource(
                    communityApi = communityApi,
                    id = id,
                    keyword = keyword
                )
            },
        ).flow
    }

    override suspend fun getBoard(
        id: Long
    ): Result<Board> {
        return communityApi.getBoard(
            id = id
        ).toDomain()
    }

    override suspend fun searchPostFromBoardPaging(
        id: Long,
        keyword: String
    ): Flow<PagingData<Post>> {
        return Pager(
            config = PagingConfig(
                pageSize = DEFAULT_PAGING_SIZE,
                enablePlaceholders = true
            ),
            pagingSourceFactory = {
                SearchPostFromBoardPagingSource(
                    communityApi = communityApi,
                    id = id,
                    keyword = keyword
                )
            },
        ).flow
    }

    override suspend fun writePost(
        id: Long,
        title: String,
        content: String,
        isAnonymous: Boolean,
        isSecret: Boolean,
        imageList: List<String>
    ): Result<Long> {
        return communityApi.writePost(
            id = id,
            title = title,
            content = content,
            isAnonymous = isAnonymous,
            isSecret = isSecret,
            imageList = imageList
        ).toDomain()
    }

    override suspend fun loadPost(
        id: Long
    ): Result<Post> {
        return communityApi.loadPost(
            id = id
        ).toDomain()
    }

    override suspend fun editPost(
        id: Long,
        title: String,
        content: String,
        isAnonymous: Boolean,
        isSecret: Boolean,
        imageList: List<String>
    ): Result<Unit> {
        return communityApi.editPost(
            id = id,
            title = title,
            content = content,
            isAnonymous = isAnonymous,
            isSecret = isSecret,
            imageList = imageList
        )
    }

    override suspend fun removePost(
        id: Long
    ): Result<Unit> {
        return communityApi.removePost(
            id = id
        )
    }

    override suspend fun likePost(
        id: Long
    ): Result<Unit> {
        return communityApi.likePost(
            id = id
        )
    }

    override suspend fun reportPost(
        id: Long,
        reason: String
    ): Result<Unit> {
        return communityApi.reportPost(
            id = id,
            reason = reason
        )
    }

    override suspend fun loadCommentPaging(
        id: Long
    ): Flow<PagingData<Comment>> {
        return Pager(
            config = PagingConfig(
                pageSize = DEFAULT_PAGING_SIZE,
                enablePlaceholders = true
            ),
            pagingSourceFactory = {
                SearchCommentPagingSource(
                    communityApi = communityApi,
                    id = id
                )
            },
        ).flow
    }

    override suspend fun writeComment(
        id: Long,
        content: String,
        isAnonymous: Boolean
    ): Result<Long> {
        return communityApi.writeComment(
            id = id,
            content = content,
            isAnonymous = isAnonymous
        ).toDomain()
    }

    override suspend fun removeComment(
        id: Long
    ): Result<Unit> {
        return communityApi.removeComment(
            id = id
        )
    }

    override suspend fun likeComment(
        id: Long
    ): Result<Unit> {
        return communityApi.likeComment(
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
    ): Result<Long> {
        return communityApi.writeCommentReply(
            id = id,
            content = content,
            isAnonymous = isAnonymous
        ).toDomain()
    }
}
