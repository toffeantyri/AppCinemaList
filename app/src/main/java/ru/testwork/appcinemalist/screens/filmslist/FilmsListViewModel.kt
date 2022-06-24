package ru.testwork.appcinemalist.screens.filmslist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import ru.testwork.appcinemalist.busines.ApiProvider
import ru.testwork.appcinemalist.repository.FilmListRepository

class FilmsListViewModel(app : Application) : AndroidViewModel(app) {

    val repo = FilmListRepository(ApiProvider()) // todo di




}