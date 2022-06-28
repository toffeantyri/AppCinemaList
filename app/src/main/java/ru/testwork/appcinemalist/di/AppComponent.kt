package ru.testwork.appcinemalist.di

import androidx.lifecycle.ViewModel
import dagger.Component
import dagger.Module
import dagger.Provides
import ru.testwork.appcinemalist.busines.api.ApiProvider
import ru.testwork.appcinemalist.repository.FilmListRepository
import ru.testwork.appcinemalist.screens.MainActivity

@Component(modules = [AppModule::class])
interface AppComponent {

    fun inject(activity : MainActivity)

    val filmListRepo: FilmListRepository

}

@Module
object AppModule {

    @Provides
    fun provideFilmListRepo(api: ApiProvider): FilmListRepository {
        return FilmListRepository(api = api)
    }

    @Provides
    fun provideApiProvider(): ApiProvider = ApiProvider()


}