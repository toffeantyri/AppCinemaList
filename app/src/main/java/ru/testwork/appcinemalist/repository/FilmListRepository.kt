package ru.testwork.appcinemalist.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import ru.testwork.appcinemalist.busines.api.ApiProvider
import ru.testwork.appcinemalist.busines.model.FilmModelItem
import ru.testwork.appcinemalist.log
import ru.testwork.appcinemalist.toListFilmModelItem
import java.lang.IllegalStateException
import javax.inject.Inject

const val PAGE_SIZE = 20

class FilmListRepository @Inject constructor(private val api: ApiProvider) {


   fun getPagedFilms(
    ): Flow<PagingData<FilmModelItem>> {
        val loader: FilmPageLoader = { pageIndex: Int, pageSize: Int ->
            getData(pageIndex)
        }
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false,
                prefetchDistance = 5,
                initialLoadSize = PAGE_SIZE,
            ),
            pagingSourceFactory = { FilmPagingSource(loader, PAGE_SIZE) }
        ).flow
    }


    private suspend fun getData(
        pageIndex: Int
    ): List<FilmModelItem> = withContext(Dispatchers.IO) {

        val offset = (pageIndex * PAGE_SIZE).toString()

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
}


