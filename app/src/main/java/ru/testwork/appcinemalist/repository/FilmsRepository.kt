package ru.testwork.appcinemalist.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.testwork.appcinemalist.busines.model.FilmModelItem


interface FilmsRepository {

    fun isErrorEnabled(): Flow<Boolean>

    fun setErrorEnabled(value: Boolean)

    fun getPagedFilms(
    ): Flow<PagingData<FilmModelItem>>

}