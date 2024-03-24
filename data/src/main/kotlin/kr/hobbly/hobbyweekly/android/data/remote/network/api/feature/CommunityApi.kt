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
import kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community.BoardPostRes
import kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community.EditBoardPostReq
import kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community.EditNoticePostReq
import kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community.EditRoutinePostReq
import kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community.GetBoardListRes
import kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community.GetMyBlockRes
import kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community.GetPopularBlockRes
import kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community.GetRecommendBlockRes
import kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community.LoadBoardCommentRes
import kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community.LoadNoticeCommentRes
import kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community.LoadRoutineCommentRes
import kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community.NoticePostRes
import kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community.ReportBoardPostReq
import kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community.ReportCommentReq
import kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community.ReportNoticePostReq
import kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community.ReportRoutinePostReq
import kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community.RoutinePostRes
import kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community.SearchBlockRes
import kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community.SearchBoardPostRes
import kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community.SearchNoticePostRes
import kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community.SearchRoutinePostRes
import kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community.WriteBoardCommentReq
import kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community.WriteBoardPostReq
import kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community.WriteBoardPostRes
import kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community.WriteCommentReplyReq
import kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community.WriteNoticeCommentReq
import kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community.WriteNoticePostReq
import kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community.WriteNoticePostRes
import kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community.WriteRoutineCommentReq
import kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community.WriteRoutinePostReq
import kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community.WriteRoutinePostRes
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

    suspend fun getPopularBlockList(): Result<GetPopularBlockRes> {
        return client.get("$baseUrl/v1/blocks/popular")
            .convert(errorMessageMapper::map)
    }

    suspend fun getRecommendBlockList(): Result<GetRecommendBlockRes> {
        return client.get("$baseUrl/v1/blocks/recommend")
            .convert(errorMessageMapper::map)
    }

    suspend fun searchBlockList(
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

    suspend fun getMyBlockList(): Result<GetMyBlockRes> {
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

    // TODO : Common Post Model
//    suspend fun getPopularPost(
//        id: Long
//    ): Result<GetPopularPostRes> {
//        TODO()
//    }

    // TODO : 통합 검색 결과 불러오기 ( 일반 + 루틴 )
//    suspend fun searchPost(
//        id: Long,
//        keyword: String,
//        page: Int,
//        pageSize: Int
//    ): Result<GetPopularPostRes> {
//        TODO()
//    }

    suspend fun writeBoardPost(
        id: Long,
        title: String,
        content: String,
        isAnonymous: Boolean,
        isSecret: Boolean,
        imageList: List<String>
    ): Result<WriteBoardPostRes> {
        return client.post("$baseUrl/v1/posts/board/$id") {
            setBody(
                WriteBoardPostReq(
                    title = title,
                    content = content,
                    isAnonymous = isAnonymous,
                    isSecret = isSecret,
                    images = imageList
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
        imageList: List<String>
    ): Result<WriteRoutinePostRes> {
        return client.post("$baseUrl/v1/posts/routine/$id") {
            setBody(
                WriteRoutinePostReq(
                    title = title,
                    content = content,
                    isAnonymous = isAnonymous,
                    isSecret = isSecret,
                    images = imageList
                )
            )
        }.convert(errorMessageMapper::map)
    }

    suspend fun writeNoticePost(
        id: Long,
        title: String,
        content: String,
        isAnonymous: Boolean,
        imageList: List<String>
    ): Result<WriteNoticePostRes> {
        return client.post("$baseUrl/v1/posts/notice/$id") {
            setBody(
                WriteNoticePostReq(
                    title = title,
                    content = content,
                    isAnonymous = isAnonymous,
                    images = imageList
                )
            )
        }.convert(errorMessageMapper::map)
    }

    suspend fun searchBoardPostList(
        id: Long,
        keyword: String,
        page: Int,
        pageSize: Int
    ): Result<SearchBoardPostRes> {
        return client.get("$baseUrl/v1/posts/list/board/$id") {
            parameter("keyword", keyword.takeIfNotEmpty())
            parameter("page", page)
            parameter("pageSize", pageSize)
        }.convert(errorMessageMapper::map)
    }

    suspend fun searchRoutinePostList(
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

    suspend fun searchNoticePostList(
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

    suspend fun loadBoardPost(
        id: Long
    ): Result<BoardPostRes> {
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

    suspend fun editBoardPost(
        id: Long,
        title: String,
        content: String,
        isAnonymous: Boolean,
        isSecret: Boolean,
        imageList: List<String>
    ): Result<Unit> {
        return client.put("$baseUrl/v1/posts/board/$id") {
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

    suspend fun editRoutinePost(
        id: Long,
        title: String,
        content: String,
        isAnonymous: Boolean,
        isSecret: Boolean,
        imageList: List<String>
    ): Result<Unit> {
        return client.put("$baseUrl/v1/posts/routine/$id") {
            setBody(
                EditRoutinePostReq(
                    title = title,
                    content = content,
                    isAnonymous = isAnonymous,
                    isSecret = isSecret,
                    images = imageList
                )
            )
        }.convert(errorMessageMapper::map)
    }

    suspend fun editNoticePost(
        id: Long,
        title: String,
        content: String,
        isAnonymous: Boolean,
        imageList: List<String>
    ): Result<Unit> {
        return client.put("$baseUrl/v1/posts/notice/$id") {
            setBody(
                EditNoticePostReq(
                    title = title,
                    content = content,
                    isAnonymous = isAnonymous,
                    images = imageList
                )
            )
        }.convert(errorMessageMapper::map)
    }

    suspend fun removeBoardPost(
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

    suspend fun likeBoardPost(
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

    suspend fun reportBoardPost(
        id: Long,
        reason: String
    ): Result<Unit> {
        return client.post("$baseUrl/v1/posts/board/$id/report") {
            setBody(
                ReportBoardPostReq(
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

    suspend fun loadBoardCommentList(
        id: Long,
        page: Int,
        pageSize: Int
    ): Result<LoadBoardCommentRes> {
        return client.get("$baseUrl/v1/comments/board/$id") {
            parameter("page", page)
            parameter("pageSize", pageSize)
        }.convert(errorMessageMapper::map)
    }

    suspend fun loadRoutineCommentList(
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

    suspend fun loadNoticeCommentList(
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

    suspend fun writeBoardComment(
        id: Long,
        content: String,
        isAnonymous: Boolean
    ): Result<Unit> {
        return client.post("$baseUrl/v1/comments/board/$id") {
            setBody(
                WriteBoardCommentReq(
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
