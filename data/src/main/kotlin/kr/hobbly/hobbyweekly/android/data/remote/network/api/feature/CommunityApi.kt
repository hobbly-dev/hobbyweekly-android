package kr.hobbly.hobbyweekly.android.data.remote.network.api.feature

import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import javax.inject.Inject
import kr.hobbly.hobbyweekly.android.common.util.takeIfNotEmpty
import kr.hobbly.hobbyweekly.android.data.remote.network.di.AuthHttpClient
import kr.hobbly.hobbyweekly.android.data.remote.network.environment.BaseUrlProvider
import kr.hobbly.hobbyweekly.android.data.remote.network.environment.ErrorMessageMapper
import kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community.BlockRes
import kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community.EditPostReq
import kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community.GetBoardListRes
import kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community.GetMyBlockRes
import kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community.GetPopularBlockRes
import kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community.GetPopularPostRes
import kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community.GetRecommendBlockRes
import kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community.LoadCommentRes
import kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community.PostRes
import kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community.ReportCommentReq
import kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community.ReportPostReq
import kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community.SearchBlockRes
import kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community.SearchPostRes
import kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community.WriteCommentReplyReq
import kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community.WriteCommentReq
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

    suspend fun getUserBlock(
        id: Long
    ): Result<BlockRes> {
        return client.get("$baseUrl/v1/blocks/$id")
            .convert(errorMessageMapper::map)
    }

    suspend fun getPopularBlock(): Result<GetPopularBlockRes> {
        return client.get("$baseUrl/v1/blocks/popular")
            .convert(errorMessageMapper::map)
    }

    suspend fun getRecommendBlock(): Result<GetRecommendBlockRes> {
        return client.get("$baseUrl/v1/blocks/recommend")
            .convert(errorMessageMapper::map)
    }

    suspend fun searchBlock(
        keyword: String,
        page: Int,
        pageSize: Int
    ): Result<SearchBlockRes> {
        return client.get("$baseUrl/v1/blocks/search") {
            parameter("keyword", keyword.takeIfNotEmpty())
            parameter("page", page)
            parameter("pageSize", pageSize)
        }.convert(errorMessageMapper::map)
    }

    suspend fun getBoardList(
        id: Long
    ): Result<GetBoardListRes> {
        return client.get("$baseUrl/v1/blocks/$id/boards")
            .convert(errorMessageMapper::map)
    }

    suspend fun getMyBlock(): Result<GetMyBlockRes> {
        return client.get("$baseUrl/v1/blocks/subscribe")
            .convert(errorMessageMapper::map)
    }

    suspend fun addMyBlock(
        id: Long
    ): Result<Unit> {
        return client.post("$baseUrl/v1/blocks/$id/subscribe")
            .convert(errorMessageMapper::map)
    }

    suspend fun removeMyBlock(
        id: Long
    ): Result<Unit> {
        return client.delete("$baseUrl/v1/blocks/$id/subscribe")
            .convert(errorMessageMapper::map)
    }

    suspend fun getPopularPost(
        id: Long
    ): Result<GetPopularPostRes> {
        return client.get("$baseUrl/v1/blocks/$id/popular")
            .convert(errorMessageMapper::map)
    }

    // TODO : 통합 검색 결과 불러오기 ( 일반 + 루틴 )

    suspend fun writePost(
        id: Long,
        title: String,
        content: String,
        isAnonymous: Boolean,
        isSecret: Boolean,
        images: List<String>
    ): Result<WritePostRes> {
        return client.post("$baseUrl/v1/posts/board/$id") {
            setBody(
                WritePostReq(
                    title = title,
                    content = content,
                    isAnonymous = isAnonymous,
                    isSecret = isSecret,
                    images = images
                )
            )
        }.convert(errorMessageMapper::map)
    }

    suspend fun searchPost(
        id: Long,
        keyword: String,
        page: Int,
        pageSize: Int
    ): Result<SearchPostRes> {
        return client.get("$baseUrl/v1/posts/list/board/$id") {
            parameter("keyword", keyword.takeIfNotEmpty())
            parameter("page", page)
            parameter("pageSize", pageSize)
        }.convert(errorMessageMapper::map)
    }

    suspend fun loadPost(
        id: Long
    ): Result<PostRes> {
        return client.get("$baseUrl/v1/posts/board/$id")
            .convert(errorMessageMapper::map)
    }

    suspend fun editPost(
        id: Long,
        title: String,
        content: String,
        isAnonymous: Boolean,
        isSecret: Boolean,
        images: List<String>
    ): Result<Unit> {
        return client.put("$baseUrl/v1/posts/board/$id") {
            setBody(
                EditPostReq(
                    title = title,
                    content = content,
                    isAnonymous = isAnonymous,
                    isSecret = isSecret,
                    images = images
                )
            )
        }
            .convert(errorMessageMapper::map)
    }

    suspend fun removePost(
        id: Long
    ): Result<Unit> {
        return client.delete("$baseUrl/v1/posts/board/$id")
            .convert(errorMessageMapper::map)
    }

    suspend fun likePost(
        id: Long
    ): Result<Unit> {
        return client.put("$baseUrl/v1/posts/board/$id/like")
            .convert(errorMessageMapper::map)
    }

    suspend fun reportPost(
        id: Long,
        reason: String
    ): Result<Unit> {
        return client.post("$baseUrl/v1/posts/board/$id/report") {
            setBody(
                ReportPostReq(
                    reason = reason
                )
            )
        }.convert(errorMessageMapper::map)
    }

    suspend fun loadComment(
        id: Long,
        keyword: String,
        page: Int,
        pageSize: Int
    ): Result<LoadCommentRes> {
        return client.get("$baseUrl/v1/comments/board/$id") {
            parameter("keyword", keyword.takeIfNotEmpty())
            parameter("page", page)
            parameter("pageSize", pageSize)
        }.convert(errorMessageMapper::map)
    }

    suspend fun writeComment(
        id: Long,
        content: String,
        isAnonymous: Boolean
    ): Result<Unit> {
        return client.post("$baseUrl/v1/comments/board/$id") {
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
    ): Result<Unit> {
        return client.post("$baseUrl/v1/comments/$id/reply") {
            setBody(
                WriteCommentReplyReq(
                    content = content,
                    isAnonymous = isAnonymous
                )
            )
        }.convert(errorMessageMapper::map)
    }

    suspend fun likeComment(
        id: Long
    ): Result<Unit> {
        return client.post("$baseUrl/v1/comments/$id/like")
            .convert(errorMessageMapper::map)
    }
}
