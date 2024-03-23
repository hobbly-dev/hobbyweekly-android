package kr.hobbly.hobbyweekly.android.data.repository.feature.community.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import kr.hobbly.hobbyweekly.android.data.common.DEFAULT_PAGE_START
import kr.hobbly.hobbyweekly.android.data.remote.network.api.feature.CommunityApi
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.Block

class SearchBlockPagingSource(
    private val communityApi: CommunityApi,
    private val keyword: String
) : PagingSource<Int, Block>() {

    override fun getRefreshKey(state: PagingState<Int, Block>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Block> {
        val page = params.key ?: DEFAULT_PAGE_START
        val pageSize = params.loadSize

        return communityApi.searchBlockList(
            keyword = keyword,
            page = page,
            pageSize = pageSize
        ).map { data ->
            val nextPage = if (data.hasNext) page + 1 else null
            val previousPage = if (data.hasPrevious) page - 1 else null

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
