package kr.hobbly.hobbyweekly.android.data.repository.nonfeature.user.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import kr.hobbly.hobbyweekly.android.data.common.DEFAULT_PAGE_START
import kr.hobbly.hobbyweekly.android.data.remote.network.api.nonfeature.NotificationApi
import kr.hobbly.hobbyweekly.android.domain.model.nonfeature.notification.Notification

class GetNotificationPagingSource(
    private val notificationApi: NotificationApi
) : PagingSource<Int, Notification>() {

    override fun getRefreshKey(state: PagingState<Int, Notification>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Notification> {
        val pageNum = params.key ?: DEFAULT_PAGE_START
        val pageSize = params.loadSize

        return notificationApi.getNotificationList(
            pageNum = pageNum,
            pageSize = pageSize
        ).map { data ->
            val nextPage = if (data.hasNext) pageNum + 1 else null
            val previousPage = if (data.hasPrevious) pageNum - 1 else null

            LoadResult.Page(
                data = data.result.map { it.toDomain() },
                prevKey = previousPage,
                nextKey = nextPage
            )
        }.getOrElse { exception ->
            LoadResult.Error(exception)
        }
    }
}
