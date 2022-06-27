package ru.testwork.appcinemalist.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import ru.testwork.appcinemalist.busines.api.ApiProvider
import ru.testwork.appcinemalist.busines.model.FilmModelItem
import ru.testwork.appcinemalist.log
import ru.testwork.appcinemalist.repository.FilmListRepository
import javax.inject.Inject

@FlowPreview
@ExperimentalCoroutinesApi
class MainActivityViewModel constructor(private val repository: FilmListRepository = FilmListRepository(ApiProvider())) :
    ViewModel() {


    private var _filmsFlow: Flow<PagingData<FilmModelItem>>

    val filmFlow: Flow<PagingData<FilmModelItem>>
        get() = _filmsFlow


    init {
        log("${this::class.java.simpleName} init")
        _filmsFlow = repository.getPagedFilms().cachedIn(viewModelScope)
        // cacheIn кешируем если подписка будет происходить несколько раз
    }


    fun refresh() {
        //todo ??? refresh
        _filmsFlow = repository.getPagedFilms().cachedIn(viewModelScope)
    }


}