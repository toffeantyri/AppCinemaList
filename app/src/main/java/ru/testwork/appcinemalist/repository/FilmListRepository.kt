package ru.testwork.appcinemalist.repository

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import ru.testwork.appcinemalist.busines.api.ApiProvider
import ru.testwork.appcinemalist.busines.model.jsonmodels.Result
import ru.testwork.appcinemalist.log
import javax.inject.Inject

class FilmListRepository @Inject constructor(private val api: ApiProvider) :
    BaseRepository<List<Result>>() {


    fun getData(
        page: Int,
        receiver: MutableLiveData<Any>,
        onSuccess: () -> Unit,
        onFail: () -> Unit
    ) {

        //todo check internet if no -> fail

        val exceptionHandler = CoroutineExceptionHandler { _, exception ->
            log("REPO exceptionHandlerCoroutine  : " + exception.message.toString())
            CoroutineScope(Dispatchers.Main).launch {
                onFail()
            }
        }

        CoroutineScope(Dispatchers.IO).launch(exceptionHandler) {
            val response = async {
                api.provideNYTimesApi().getReviewAll(offset = page.toString())
            }.await()
            log("REPO response Successful : ${response.isSuccessful}")
            if (response.isSuccessful && response.body() != null) {
                val model = response.body()!!
                val list = model.results ?: listOf()
                withContext(Dispatchers.Main) {
                    dataEmitter.value = list
                    onSuccess()
                }
            } else {
                withContext(Dispatchers.Main) {
                    onFail()
                }
            }
        }
    }
}