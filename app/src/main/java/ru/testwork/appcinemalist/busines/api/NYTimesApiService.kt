package ru.testwork.appcinemalist.busines.api

import io.reactivex.rxjava3.core.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import ru.testwork.appcinemalist.APP_CONTEXT
import ru.testwork.appcinemalist.R
import ru.testwork.appcinemalist.busines.model.NYTimesReviewModel


interface NYTimesApiService {


    // http://api.nytimes.com/svc/movies/v2/reviews/all.json?offset=0&api-key=WLZea1BzGd1A7R9duap3nIJsAfi4JfSo

    //call с 0 по 20 вкл Next Call с 20 что бы без повторений
    val MAX_PAGE_SIZE: Int
        get() = 20

    private val startPage: String
        get() = "0"

    @GET("/svc/movies/v2/reviews/all.json?")
    fun getReviewAll(
        @Query("offset") offset: String = startPage,
        @Query("api-key") key: String = APP_CONTEXT.getString(R.string.api_key)
    ): Response<NYTimesReviewModel>

}