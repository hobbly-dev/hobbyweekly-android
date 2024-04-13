package kr.hobbly.hobbyweekly.android.data.remote.network.api.feature

import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import javax.inject.Inject
import kr.hobbly.hobbyweekly.android.common.util.takeIfNotEmpty
import kr.hobbly.hobbyweekly.android.data.remote.network.di.AuthHttpClient
import kr.hobbly.hobbyweekly.android.data.remote.network.environment.BaseUrlProvider
import kr.hobbly.hobbyweekly.android.data.remote.network.environment.ErrorMessageMapper
import kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community.AddMyBlockRes
import kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community.BlockRes
import kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community.EditBoardPostReq
import kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community.GetBoardListRes
import kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community.GetBoardRes
import kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community.GetMyBlockRes
import kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community.GetPopularBlockRes
import kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community.GetPopularPostRes
import kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community.GetRecommendBlockRes
import kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community.LoadBoardCommentRes
import kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community.PostRes
import kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community.ReportCommentReq
import kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community.ReportPostReq
import kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community.SearchBlockRes
import kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community.SearchPostFromBlockRes
import kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community.SearchPostFromBoardRes
import kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community.WriteCommentReplyReq
import kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community.WriteCommentReplyRes
import kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community.WriteCommentReq
import kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community.WriteCommentRes
import kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community.WritePostReq
import kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community.WritePostRes
import kr.hobbly.hobbyweekly.android.data.remote.network.util.convert

class CommunityApi @Inject constructor(
    @AuthHttpClient private val client: HttpClient,
    private val baseUrlProvider: BaseUrlProvider,
    private val errorMessageMapper: ErrorMessageMapper
) {
    private val baseUrl: String
        get() = baseUrlProvider.get()

    suspend fun getBlock(
        id: Long
    ): Result<BlockRes> {
        return client.get("$baseUrl/v1/blocks/$id")
            .convert(errorMessageMapper::map)
    }

    suspend fun getMyBlockList(): Result<GetMyBlockRes> {
        return client.get("$baseUrl/v1/blocks/me")
            .convert(errorMessageMapper::map)
    }

    suspend fun addMyBlock(
        id: Long
    ): Result<AddMyBlockRes> {
        return client.post("$baseUrl/v1/blocks/$id/me")
            .convert(errorMessageMapper::map)
    }

    suspend fun removeMyBlock(
        id: Long
    ): Result<Unit> {
        return client.delete("$baseUrl/v1/blocks/$id/me")
            .convert(errorMessageMapper::map)
    }

    suspend fun searchBlockList(
        keyword: String,
        pageNum: Int,
        pageSize: Int
    ): Result<SearchBlockRes> {
        return client.get("$baseUrl/v1/blocks") {
            parameter("keyword", keyword.takeIfNotEmpty())
            parameter("pageNum", pageNum)
            parameter("pageSize", pageSize)
        }.convert(errorMessageMapper::map)
    }

    suspend fun getPopularBlockList(): Result<GetPopularBlockRes> {
        return client.get("$baseUrl/v1/blocks/popular")
            .convert(errorMessageMapper::map)
    }

    suspend fun getRecommendBlockList(): Result<GetRecommendBlockRes> {
        return client.get("$baseUrl/v1/blocks/recommended")
            .convert(errorMessageMapper::map)
    }

    suspend fun getBoardList(
        id: Long
    ): Result<GetBoardListRes> {
        return client.get("$baseUrl/v1/blocks/$id/boards")
            .convert(errorMessageMapper::map)
    }

    suspend fun getPopularPostList(
        pageNum: Int,
        pageSize: Int
    ): Result<GetPopularPostRes> {
        return client.get("$baseUrl/v1/posts/popular") {
            parameter("pageNum", pageNum)
            parameter("pageSize", pageSize)
        }.convert(errorMessageMapper::map)
    }

    suspend fun getPopularPostListFromBlock(
        id: Long,
        pageNum: Int,
        pageSize: Int
    ): Result<GetPopularPostRes> {
        return client.get("$baseUrl/v1/blocks/$id/posts/popular") {
            parameter("pageNum", pageNum)
            parameter("pageSize", pageSize)
        }.convert(errorMessageMapper::map)
    }

    suspend fun searchPostFromBlock(
        id: Long,
        keyword: String,
        pageNum: Int,
        pageSize: Int
    ): Result<SearchPostFromBlockRes> {
        return client.get("$baseUrl/v1/blocks/$id/posts") {
            parameter("keyword", keyword.takeIfNotEmpty())
            parameter("pageNum", pageNum)
            parameter("pageSize", pageSize)
        }.convert(errorMessageMapper::map)
    }

    suspend fun getBoard(
        id: Long
    ): Result<GetBoardRes> {
        return client.get("$baseUrl/v1/boards/$id")
            .convert(errorMessageMapper::map)
    }

    suspend fun searchPostFromBoard(
        id: Long,
        keyword: String,
        pageNum: Int,
        pageSize: Int
    ): Result<SearchPostFromBoardRes> {
        return client.get("$baseUrl/v1/boards/$id/posts") {
            parameter("keyword", keyword.takeIfNotEmpty())
            parameter("pageNum", pageNum)
            parameter("pageSize", pageSize)
        }.convert(errorMessageMapper::map)
    }

    suspend fun writePost(
        id: Long,
        title: String,
        content: String,
        isAnonymous: Boolean,
        isSecret: Boolean,
        imageList: List<String>
    ): Result<WritePostRes> {
        return client.post("$baseUrl/v1/boards/$id/posts") {
            setBody(
                WritePostReq(
                    title = title,
                    content = content,
                    isAnonymous = isAnonymous,
                    isSecret = isSecret,
                    images = imageList
                )
            )
        }.convert(errorMessageMapper::map)
    }

    suspend fun loadPost(
        id: Long
    ): Result<PostRes> {
        return client.get("$baseUrl/v1/posts/$id")
            .convert(errorMessageMapper::map)
    }

    suspend fun editPost(
        id: Long,
        title: String,
        content: String,
        isAnonymous: Boolean,
        isSecret: Boolean,
        imageList: List<String>
    ): Result<Unit> {
        return client.patch("$baseUrl/v1/posts/$id") {
            setBody(
                EditBoardPostReq(
                    title = title,
                    content = content,
                    isAnonymous = isAnonymous,
                    isSecret = isSecret,
                    images = imageList
                )
            )
        }.convert(errorMessageMapper::map)
    }

    suspend fun removePost(
        id: Long
    ): Result<Unit> {
        return client.delete("$baseUrl/v1/posts/$id")
            .convert(errorMessageMapper::map)
    }

    suspend fun likePost(
        id: Long
    ): Result<Unit> {
        return client.post("$baseUrl/v1/posts/$id/like")
            .convert(errorMessageMapper::map)
    }

    suspend fun reportPost(
        id: Long,
        reason: String
    ): Result<Unit> {
        return client.post("$baseUrl/v1/posts/$id/report") {
            setBody(
                ReportPostReq(
                    reason = reason
                )
            )
        }.convert(errorMessageMapper::map)
    }

    suspend fun loadCommentList(
        id: Long,
        pageNum: Int,
        pageSize: Int
    ): Result<LoadBoardCommentRes> {
        return client.get("$baseUrl/v1/posts/$id/comments") {
            parameter("pageNum", pageNum)
            parameter("pageSize", pageSize)
        }.convert(errorMessageMapper::map)
    }

    suspend fun writeComment(
        id: Long,
        content: String,
        isAnonymous: Boolean
    ): Result<WriteCommentRes> {
        return client.post("$baseUrl/v1/posts/$id/comment") {
            setBody(
                WriteCommentReq(
                    content = content,
                    isAnonymous = isAnonymous
                )
            )
        }.convert(errorMessageMapper::map)
    }

    suspend fun removeComment(
        id: Long
    ): Result<Unit> {
        return client.delete("$baseUrl/v1/comments/$id")
            .convert(errorMessageMapper::map)
    }

    suspend fun likeComment(
        id: Long
    ): Result<Unit> {
        return client.post("$baseUrl/v1/comments/$id/like")
            .convert(errorMessageMapper::map)
    }

    suspend fun reportComment(
        id: Long,
        reason: String
    ): Result<Unit> {
        return client.post("$baseUrl/v1/comments/$id/report") {
            setBody(
                ReportCommentReq(
                    reason = reason
                )
            )
        }.convert(errorMessageMapper::map)
    }

    suspend fun writeCommentReply(
        id: Long,
        content: String,
        isAnonymous: Boolean
    ): Result<WriteCommentReplyRes> {
        return client.post("$baseUrl/v1/comments/$id/reply") {
            setBody(
                WriteCommentReplyReq(
                    content = content,
                    isAnonymous = isAnonymous
                )
            )
        }.convert(errorMessageMapper::map)
    }
}
