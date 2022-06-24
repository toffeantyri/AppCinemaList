package ru.testwork.appcinemalist.repository

import kotlinx.coroutines.*
import ru.testwork.appcinemalist.busines.ApiProvider
import ru.testwork.appcinemalist.busines.model.NYTimesReviewModel
import ru.testwork.appcinemalist.log

class FilmListRepository(api : ApiProvider) : BaseRepository<NYTimesReviewModel>() {

    val apiProvider = api

    fun getData(page : Int, onSuccess : () -> Unit, onFail: () -> Unit){

        //todo check internet if no -> fail

        val exceptionHandler = CoroutineExceptionHandler { _, exception ->
            log("REPO exceptionHandlerCoroutine  : " + exception.message.toString())
            CoroutineScope(Dispatchers.Main).launch {
                onFail()
            }
        }

        CoroutineScope(Dispatchers.IO).launch(exceptionHandler) {



        }
    }
}