package ru.testwork.appcinemalist.view

import androidx.paging.PagingSource
import androidx.paging.PagingState
import retrofit2.HttpException
import ru.testwork.appcinemalist.busines.api.NYTimesApiService
import ru.testwork.appcinemalist.busines.model.jsonmodels.NYTimesReviewModel
import ru.testwork.appcinemalist.busines.model.jsonmodels.Result

class FilmPagerSource(
    private val service: NYTimesApiService,
    private val query: String
) : PagingSource<Int, Result>() {


    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        if (query.isEmpty()) {
            return LoadResult.Page(emptyList(), prevKey = null, nextKey = null)
        }
        val page: Int = params.key ?: 1 //todo 0 or 1?
        val pageSize = 20 // todo default

        val response = service.getReviewAll(offset = page.toString())

        if (response.isSuccessful && response.body() != null) {
            val list: List<Result> = (response.body() as NYTimesReviewModel).results
            val nextKey = if (list.size < pageSize) null else page + 1
            val prevKey = if (page == 1) null else page - 1
            return LoadResult.Page(list, prevKey = prevKey, nextKey = nextKey)
        } else {
            return LoadResult.Error(HttpException(response))
        }
    }
}

