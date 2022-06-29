package ru.testwork.appcinemalist.busines.api

import retrofit2.Retrofit
import javax.inject.Inject


class ApiProvider @Inject constructor(private val nytimesFilms: Retrofit) {

    fun provideNYTimesApi(): NYTimesApiService = nytimesFilms.create(NYTimesApiService::class.java)
}