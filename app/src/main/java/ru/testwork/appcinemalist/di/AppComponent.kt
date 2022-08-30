package ru.testwork.appcinemalist.di

import dagger.Component
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import ru.testwork.appcinemalist.util.BASE_URL_NYT
import ru.testwork.appcinemalist.busines.api.ApiProvider
import ru.testwork.appcinemalist.repository.FilmListRepository
import ru.testwork.appcinemalist.screens.MainActivity
import ru.testwork.appcinemalist.util.BASE_URL_SARAWAN
import ru.testwork.appcinemalist.viewmodels.MainActivityViewModel
import javax.inject.Inject
import javax.inject.Named

@Component(modules = [AppModule::class])
interface AppComponent {

    fun injectA(activity: MainActivity)

    fun injectV(mainActivityViewModel: MainActivityViewModel)

    val filmListRepo: FilmListRepository

    val apiProvider: ApiProvider

    val retrofit: Retrofit

    val sarawanRetrofit : Retrofit
}

@Module(includes = [NetModule::class])
class AppModule {

    @Provides
    fun provideFilmListRepo(api: ApiProvider): FilmListRepository {
        return FilmListRepository(api = api)
    }

    @Provides
    fun provideApiProvider(retrofit: Retrofit): ApiProvider =
        ApiProvider(retrofit)


}

@Module
object NetModule {

    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL_NYT)
            .addConverterFactory(MoshiConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}
