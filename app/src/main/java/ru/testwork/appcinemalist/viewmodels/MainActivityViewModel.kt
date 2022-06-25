package ru.testwork.appcinemalist.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.internal.operators.single.SingleDoOnSuccess
import kotlinx.coroutines.flow.Flow
import ru.testwork.appcinemalist.busines.model.FilmModelItem
import ru.testwork.appcinemalist.repository.FilmListRepository
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(private val repository: FilmListRepository) :
    ViewModel() {

    private val filmsFirstPage: MutableLiveData<Any> by lazy { MutableLiveData<Any>() }
    private val filmsNextPage: MutableLiveData<Any> by lazy { MutableLiveData<Any>() }

    val firstPage: LiveData<Any>
        get() = filmsFirstPage

    val nextPage: LiveData<Any>
        get() = filmsNextPage

    fun requestFirstPage(page: Int, onSuccess: () -> Unit, onFail: () -> Unit) {
        repository.getData(page, filmsFirstPage, { onSuccess() }, { onFail() })
    }

    fun requestNextPage(page: Int, onSuccess: () -> Unit, onFail: () -> Unit) {
        repository.getData(page, filmsNextPage, { onSuccess() }, { onFail() })
    }


}