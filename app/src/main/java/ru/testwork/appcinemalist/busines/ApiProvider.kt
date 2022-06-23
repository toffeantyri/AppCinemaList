package ru.testwork.appcinemalist.busines

import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class ApiProvider {

    private val nytimesFilms by lazy { initApi() }

    private fun initApi() = Retrofit.Builder()
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("http://api.nytimes.com")
        .build()

    fun provideNYTimesApi() = nytimesFilms.create(NYTimesApiService::class.java)
}