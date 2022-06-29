package ru.testwork.appcinemalist.di

import androidx.lifecycle.ViewModel
import dagger.Component
import dagger.Module
import dagger.Provides
import ru.testwork.appcinemalist.busines.api.ApiProvider
import ru.testwork.appcinemalist.repository.FilmListRepository
import ru.testwork.appcinemalist.screens.MainActivity
import ru.testwork.appcinemalist.viewmodels.MainActivityViewModel

@Component(modules = [AppModule::class])
interface AppComponent {

    fun injectA(activity : MainActivity)

    fun injectV(mainActivityViewModel : MainActivityViewModel)

    val filmListRepo: FilmListRepository

    val apiProvider : ApiProvider
}

@Module
class AppModule {

    @Provides
    fun provideFilmListRepo(api: ApiProvider): FilmListRepository {
        return FilmListRepository(api = api)
    }

    @Provides
    fun provideApiProvider(): ApiProvider = ApiProvider()


}