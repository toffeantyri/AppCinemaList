package ru.testwork.appcinemalist.busines

import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import ru.testwork.appcinemalist.APP_CONTEXT
import ru.testwork.appcinemalist.R


interface NYTimesApiService {


    @GET("/svc/movies/v2/reviews/all.json?")
    fun getAllList(
        @Query("api-key") key : String = APP_CONTEXT.getString(R.string.api_key),
    ) : Observable<String> // todo add type model

}