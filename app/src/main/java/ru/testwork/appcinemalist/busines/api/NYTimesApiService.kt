package ru.testwork.appcinemalist.busines.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import ru.testwork.appcinemalist.BuildConfig
import ru.testwork.appcinemalist.busines.model.jsonmodels.NYTimesReviewModel


interface NYTimesApiService {


    // http://api.nytimes.com/svc/movies/v2/reviews/all.json?offset=0&api-key=WLZea1BzGd1A7R9duap3nIJsAfi4JfSo

    //call с 0 по 20 вкл Next Call с 20 что бы без повторений

    @GET("/svc/movies/v2/reviews/all.json?")
    suspend fun getReviewAll(
        @Query("offset") offset: String = "0",
        @Query("api-key") key: String = BuildConfig.API_KEY_DEBUG
    ): Response<NYTimesReviewModel>


}