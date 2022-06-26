package ru.testwork.appcinemalist.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import ru.testwork.appcinemalist.busines.model.FilmModelItem
import ru.testwork.appcinemalist.repository.FilmListRepository
import javax.inject.Inject

@FlowPreview
@ExperimentalCoroutinesApi
@HiltViewModel
class MainActivityViewModel @Inject constructor(private val repository: FilmListRepository) :
    ViewModel() {


    val isErrorEnabled: Flow<Boolean> = repository.isErrorEnabled()

    private var _filmsFlow: Flow<PagingData<FilmModelItem>>

    val filmFlow: Flow<PagingData<FilmModelItem>>
        get() = _filmsFlow


    init {
        _filmsFlow = repository.getPagedFilms().cachedIn(viewModelScope)
        // cacheIn кешируем если подписка будет происходить несколько раз
    }

    fun setEnableErrors(value: Boolean) {
        repository.setErrorEnabled(value)
    }

    fun refresh() {
        //todo ??? refresh
        _filmsFlow = repository.getPagedFilms().cachedIn(viewModelScope)
    }


}