package ru.testwork.appcinemalist.repository

import kotlinx.coroutines.*
import ru.testwork.appcinemalist.busines.ApiProvider
import ru.testwork.appcinemalist.busines.model.Result
import ru.testwork.appcinemalist.log

class FilmListRepository(api: ApiProvider) : BaseRepository<List<Result>>() {

    private val apiProvider = api // todo DI

    fun getData(page: Int, onSuccess: () -> Unit, onFail: () -> Unit) {

        //todo check internet if no -> fail

        val exceptionHandler = CoroutineExceptionHandler { _, exception ->
            log("REPO exceptionHandlerCoroutine  : " + exception.message.toString())
            CoroutineScope(Dispatchers.Main).launch {
                onFail()
            }
        }

        CoroutineScope(Dispatchers.IO).launch(exceptionHandler) {
            val response = async {
                apiProvider.provideNYTimesApi().getReviewAll()
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