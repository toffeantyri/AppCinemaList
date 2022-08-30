package ru.testwork.appcinemalist.busines.api

import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Named


class ApiProvider @Inject constructor(
    private val nytimesFilms: Retrofit,
) {

    fun provideNYTimesApi(): NYTimesApiService = nytimesFilms.create(NYTimesApiService::class.java)

}