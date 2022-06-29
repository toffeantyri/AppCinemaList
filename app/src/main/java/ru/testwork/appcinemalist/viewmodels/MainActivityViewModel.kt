package ru.testwork.appcinemalist.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.retry
import ru.testwork.appcinemalist.busines.api.ApiProvider
import ru.testwork.appcinemalist.busines.model.FilmModelItem
import ru.testwork.appcinemalist.log
import ru.testwork.appcinemalist.repository.FilmListRepository
import javax.inject.Inject

@FlowPreview
@ExperimentalCoroutinesApi
class MainActivityViewModel @Inject constructor() :
    ViewModel() {

    @Inject
    lateinit var repository: FilmListRepository

    private lateinit var _filmsFlow: Flow<PagingData<FilmModelItem>>

    private var firstInitViewModel: Boolean = true

    val filmFlow: Flow<PagingData<FilmModelItem>>
        get() {
            if (firstInitViewModel) {
                _filmsFlow = repository.getPagedFilms().cachedIn(viewModelScope)
                firstInitViewModel = false
            }
            log("VM : FilmFlow anybody get  $_filmsFlow")
            return _filmsFlow
        }

    fun refresh() {
        _filmsFlow = repository.getPagedFilms().cachedIn(viewModelScope)
    }
}