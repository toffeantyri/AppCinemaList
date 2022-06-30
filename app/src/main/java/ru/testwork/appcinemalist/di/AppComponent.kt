package ru.testwork.appcinemalist.di

import dagger.Component
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.testwork.appcinemalist.util.BASE_URL_NYT
import ru.testwork.appcinemalist.busines.api.ApiProvider
import ru.testwork.appcinemalist.repository.FilmListRepository
import ru.testwork.appcinemalist.screens.MainActivity
import ru.testwork.appcinemalist.viewmodels.MainActivityViewModel

@Component(modules = [AppModule::class])
interface AppComponent {

    fun injectA(activity: MainActivity)

    fun injectV(mainActivityViewModel: MainActivityViewModel)

    val filmListRepo: FilmListRepository

    val apiProvider: ApiProvider

    val retrofit: Retrofit
}

@Module
class AppModule {

    @Provides
    fun provideFilmListRepo(api: ApiProvider): FilmListRepository {
        return FilmListRepository(api = api)
    }

    @Provides
    fun provideApiProvider(retrofit: Retrofit): ApiProvider = ApiProvider(retrofit)


    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL_NYT)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
