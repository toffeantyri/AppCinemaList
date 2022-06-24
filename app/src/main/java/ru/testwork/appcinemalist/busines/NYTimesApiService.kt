package ru.testwork.appcinemalist.busines

import io.reactivex.rxjava3.core.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import ru.testwork.appcinemalist.APP_CONTEXT
import ru.testwork.appcinemalist.R


interface NYTimesApiService {


    // http://api.nytimes.com/svc/movies/v2/reviews/all.json?api-key=WLZea1BzGd1A7R9duap3nIJsAfi4JfSo

    @GET("/svc/movies/v2/reviews/all.json?")
    fun getReviewAll(
        @Query("api-key") key : String = APP_CONTEXT.getString(R.string.api_key),
    ) : Response<String> // todo add type model

}