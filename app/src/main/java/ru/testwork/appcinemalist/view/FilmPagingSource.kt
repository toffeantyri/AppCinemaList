package ru.testwork.appcinemalist.view

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ru.testwork.appcinemalist.busines.model.FilmModelItem
import java.lang.Exception

typealias FilmPageLoader = suspend (pageIndex: Int, pageSize: Int) -> List<FilmModelItem>
//или сразу getData в repo использовать

class FilmPagingSource(
    private val loader: FilmPageLoader,
    private val pageSize: Int
) : PagingSource<Int, FilmModelItem>() {


    override fun getRefreshKey(state: PagingState<Int, FilmModelItem>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)

    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, FilmModelItem> {
        val pageIndex = params.key ?: 0
        return try {
            val films = loader.invoke(pageIndex, params.loadSize)

            return LoadResult.Page(
                data = films,
                prevKey = if (pageIndex == 0) null else pageIndex - 1,
                nextKey = if (films.size == params.loadSize) pageIndex + (params.loadSize / pageSize) else null
            )
        } catch (e: Exception) {
            LoadResult.Error(
                throwable = e
            )
        }
    }
}

