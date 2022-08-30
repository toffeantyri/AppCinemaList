package ru.testwork.appcinemalist.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import ru.testwork.appcinemalist.util.MAX_PAGE_SIZE
import ru.testwork.appcinemalist.busines.api.ApiProvider
import ru.testwork.appcinemalist.busines.model.FilmModelItem
import ru.testwork.appcinemalist.busines.model.FilterProduct
import ru.testwork.appcinemalist.util.log
import ru.testwork.appcinemalist.util.toListFilmModelItem
import ru.testwork.appcinemalist.view.FilmPageLoader
import ru.testwork.appcinemalist.view.FilmPagingSource
import javax.inject.Inject


class FilmListRepository @Inject constructor(private val api: ApiProvider) {


    fun getPagedFilms(
    ): Flow<PagingData<FilmModelItem>> {
        val loader: FilmPageLoader = { pageIndex: Int, pageSize: Int ->
            getData(pageIndex)
        }
        return Pager(
            config = PagingConfig(
                pageSize = MAX_PAGE_SIZE,
                enablePlaceholders = false,
                prefetchDistance = 5,
                initialLoadSize = MAX_PAGE_SIZE,
            ),
            pagingSourceFactory = { FilmPagingSource(loader, MAX_PAGE_SIZE) }
        ).flow
    }


    private suspend fun getData(
        pageIndex: Int
    ): List<FilmModelItem> = withContext(Dispatchers.IO) {

        val offset = (pageIndex * MAX_PAGE_SIZE).toString()

        val response = async {
            api.provideNYTimesApi().getReviewAll(offset = offset)
        }.await()
        log("REPO response Successful : ${response.isSuccessful}")
        if (response.isSuccessful && response.body() != null) {
            val model = response.body()!!
            val list = model.results ?: listOf()

            return@withContext list.toListFilmModelItem()
        } else {
            return@withContext emptyList()
        }
    }

    private var page = 1
    suspend fun getSimilarProducts(): ProductResult {
        val request = withContext(Dispatchers.IO) {
            val filter = FilterProduct(
                page = page,
                similarProducts = 66967
            )
            api.provideNYTimesApi().getProducts("https://dev.sarawan.ru/api/products/", filter.toMap())
        }
        page++
        return request
    }
}


