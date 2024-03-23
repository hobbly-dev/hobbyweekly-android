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
import kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community.EditNoticePostReq
import kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community.EditRoutinePostReq
import kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community.EditUserPostReq
import kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community.GetBoardListRes
import kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community.GetMyBlockRes
import kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community.GetPopularBlockRes
import kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community.GetPopularPostRes
import kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community.GetRecommendBlockRes
import kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community.LoadNoticeCommentRes
import kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community.LoadRoutineCommentRes
import kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community.LoadUserCommentRes
import kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community.NoticePostRes
import kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community.ReportCommentReq
import kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community.ReportNoticePostReq
import kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community.ReportRoutinePostReq
import kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community.ReportUserPostReq
import kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community.RoutinePostRes
import kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community.SearchBlockRes
import kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community.SearchNoticePostRes
import kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community.SearchRoutinePostRes
import kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community.SearchUserPostRes
import kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community.UserPostRes
import kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community.WriteCommentReplyReq
import kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community.WriteNoticeCommentReq
import kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community.WriteNoticePostReq
import kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community.WriteNoticePostRes
import kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community.WriteRoutineCommentReq
import kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community.WriteRoutinePostReq
import kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community.WriteRoutinePostRes
import kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community.WriteUserCommentReq
import kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community.WriteUserPostReq
import kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community.WriteUserPostRes
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

    suspend fun writeUserPost(
        id: Long,
        title: String,
        content: String,
        isAnonymous: Boolean,
        isSecret: Boolean,
        images: List<String>
    ): Result<WriteUserPostRes> {
        return client.post("$baseUrl/v1/posts/board/$id") {
            setBody(
                WriteUserPostReq(
                    title = title,
                    content = content,
                    isAnonymous = isAnonymous,
                    isSecret = isSecret,
                    images = images
                )
            )
        }.convert(errorMessageMapper::map)
    }

    suspend fun writeRoutinePost(
        id: Long,
        title: String,
        content: String,
        isAnonymous: Boolean,
        isSecret: Boolean,
        images: List<String>
    ): Result<WriteRoutinePostRes> {
        return client.post("$baseUrl/v1/posts/routine/$id") {
            setBody(
                WriteRoutinePostReq(
                    title = title,
                    content = content,
                    isAnonymous = isAnonymous,
                    isSecret = isSecret,
                    images = images
                )
            )
        }.convert(errorMessageMapper::map)
    }

    suspend fun writeNoticePost(
        id: Long,
        title: String,
        content: String,
        isAnonymous: Boolean,
        images: List<String>
    ): Result<WriteNoticePostRes> {
        return client.post("$baseUrl/v1/posts/notice/$id") {
            setBody(
                WriteNoticePostReq(
                    title = title,
                    content = content,
                    isAnonymous = isAnonymous,
                    images = images
                )
            )
        }.convert(errorMessageMapper::map)
    }

    suspend fun searchUserPost(
        id: Long,
        keyword: String,
        page: Int,
        pageSize: Int
    ): Result<SearchUserPostRes> {
        return client.get("$baseUrl/v1/posts/list/board/$id") {
            parameter("keyword", keyword.takeIfNotEmpty())
            parameter("page", page)
            parameter("pageSize", pageSize)
        }.convert(errorMessageMapper::map)
    }

    suspend fun searchRoutinePost(
        id: Long,
        keyword: String,
        page: Int,
        pageSize: Int
    ): Result<SearchRoutinePostRes> {
        return client.get("$baseUrl/v1/posts/list/routine/$id") {
            parameter("keyword", keyword.takeIfNotEmpty())
            parameter("page", page)
            parameter("pageSize", pageSize)
        }.convert(errorMessageMapper::map)
    }

    suspend fun searchNoticePost(
        id: Long,
        keyword: String,
        page: Int,
        pageSize: Int
    ): Result<SearchNoticePostRes> {
        return client.get("$baseUrl/v1/posts/list/notice/$id") {
            parameter("keyword", keyword.takeIfNotEmpty())
            parameter("page", page)
            parameter("pageSize", pageSize)
        }.convert(errorMessageMapper::map)
    }

    suspend fun loadUserPost(
        id: Long
    ): Result<UserPostRes> {
        return client.get("$baseUrl/v1/posts/board/$id")
            .convert(errorMessageMapper::map)
    }

    suspend fun loadRoutinePost(
        id: Long
    ): Result<RoutinePostRes> {
        return client.get("$baseUrl/v1/posts/routine/$id")
            .convert(errorMessageMapper::map)
    }

    suspend fun loadNoticePost(
        id: Long
    ): Result<NoticePostRes> {
        return client.get("$baseUrl/v1/posts/notice/$id")
            .convert(errorMessageMapper::map)
    }

    suspend fun editUserPost(
        id: Long,
        title: String,
        content: String,
        isAnonymous: Boolean,
        isSecret: Boolean,
        images: List<String>
    ): Result<Unit> {
        return client.put("$baseUrl/v1/posts/board/$id") {
            setBody(
                EditUserPostReq(
                    title = title,
                    content = content,
                    isAnonymous = isAnonymous,
                    isSecret = isSecret,
                    images = images
                )
            )
        }.convert(errorMessageMapper::map)
    }

    suspend fun editRoutinePost(
        id: Long,
        title: String,
        content: String,
        isAnonymous: Boolean,
        isSecret: Boolean,
        images: List<String>
    ): Result<Unit> {
        return client.put("$baseUrl/v1/posts/routine/$id") {
            setBody(
                EditRoutinePostReq(
                    title = title,
                    content = content,
                    isAnonymous = isAnonymous,
                    isSecret = isSecret,
                    images = images
                )
            )
        }.convert(errorMessageMapper::map)
    }

    suspend fun editNoticePost(
        id: Long,
        title: String,
        content: String,
        isAnonymous: Boolean,
        images: List<String>
    ): Result<Unit> {
        return client.put("$baseUrl/v1/posts/notice/$id") {
            setBody(
                EditNoticePostReq(
                    title = title,
                    content = content,
                    isAnonymous = isAnonymous,
                    images = images
                )
            )
        }.convert(errorMessageMapper::map)
    }

    suspend fun removeUserPost(
        id: Long
    ): Result<Unit> {
        return client.delete("$baseUrl/v1/posts/board/$id")
            .convert(errorMessageMapper::map)
    }

    suspend fun removeRoutinePost(
        id: Long
    ): Result<Unit> {
        return client.delete("$baseUrl/v1/posts/routine/$id")
            .convert(errorMessageMapper::map)
    }

    suspend fun removeNoticePost(
        id: Long
    ): Result<Unit> {
        return client.delete("$baseUrl/v1/posts/notice/$id")
            .convert(errorMessageMapper::map)
    }

    suspend fun likeUserPost(
        id: Long
    ): Result<Unit> {
        return client.put("$baseUrl/v1/posts/board/$id/like")
            .convert(errorMessageMapper::map)
    }

    suspend fun likeRoutinePost(
        id: Long
    ): Result<Unit> {
        return client.put("$baseUrl/v1/posts/routine/$id/like")
            .convert(errorMessageMapper::map)
    }

    suspend fun likeNoticePost(
        id: Long
    ): Result<Unit> {
        return client.put("$baseUrl/v1/posts/notice/$id/like")
            .convert(errorMessageMapper::map)
    }

    suspend fun reportUserPost(
        id: Long,
        reason: String
    ): Result<Unit> {
        return client.post("$baseUrl/v1/posts/board/$id/report") {
            setBody(
                ReportUserPostReq(
                    reason = reason
                )
            )
        }.convert(errorMessageMapper::map)
    }

    suspend fun reportRoutinePost(
        id: Long,
        reason: String
    ): Result<Unit> {
        return client.post("$baseUrl/v1/posts/routine/$id/report") {
            setBody(
                ReportRoutinePostReq(
                    reason = reason
                )
            )
        }.convert(errorMessageMapper::map)
    }

    suspend fun reportNoticePost(
        id: Long,
        reason: String
    ): Result<Unit> {
        return client.post("$baseUrl/v1/posts/notice/$id/report") {
            setBody(
                ReportNoticePostReq(
                    reason = reason
                )
            )
        }.convert(errorMessageMapper::map)
    }

    suspend fun loadUserComment(
        id: Long,
        keyword: String,
        page: Int,
        pageSize: Int
    ): Result<LoadUserCommentRes> {
        return client.get("$baseUrl/v1/comments/board/$id") {
            parameter("keyword", keyword.takeIfNotEmpty())
            parameter("page", page)
            parameter("pageSize", pageSize)
        }.convert(errorMessageMapper::map)
    }

    suspend fun loadRoutineComment(
        id: Long,
        keyword: String,
        page: Int,
        pageSize: Int
    ): Result<LoadRoutineCommentRes> {
        return client.get("$baseUrl/v1/comments/routine/$id") {
            parameter("keyword", keyword.takeIfNotEmpty())
            parameter("page", page)
            parameter("pageSize", pageSize)
        }.convert(errorMessageMapper::map)
    }

    suspend fun loadNoticeComment(
        id: Long,
        keyword: String,
        page: Int,
        pageSize: Int
    ): Result<LoadNoticeCommentRes> {
        return client.get("$baseUrl/v1/comments/notice/$id") {
            parameter("keyword", keyword.takeIfNotEmpty())
            parameter("page", page)
            parameter("pageSize", pageSize)
        }.convert(errorMessageMapper::map)
    }

    suspend fun writeUserComment(
        id: Long,
        content: String,
        isAnonymous: Boolean
    ): Result<Unit> {
        return client.post("$baseUrl/v1/comments/board/$id") {
            setBody(
                WriteUserCommentReq(
                    content = content,
                    isAnonymous = isAnonymous
                )
            )
        }.convert(errorMessageMapper::map)
    }

    suspend fun writeRoutineComment(
        id: Long,
        content: String,
        isAnonymous: Boolean
    ): Result<Unit> {
        return client.post("$baseUrl/v1/comments/routine/$id") {
            setBody(
                WriteRoutineCommentReq(
                    content = content,
                    isAnonymous = isAnonymous
                )
            )
        }.convert(errorMessageMapper::map)
    }

    suspend fun writeNoticeComment(
        id: Long,
        content: String,
        isAnonymous: Boolean
    ): Result<Unit> {
        return client.post("$baseUrl/v1/comments/notice/$id") {
            setBody(
                WriteNoticeCommentReq(
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
