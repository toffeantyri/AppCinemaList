package ru.testwork.appcinemalist

import android.util.Log
import ru.testwork.appcinemalist.busines.model.FilmModelItem
import ru.testwork.appcinemalist.busines.model.jsonmodels.Result

const val LOG_TAG = "MY_LOG"

fun log(message: String) {
    Log.d(LOG_TAG, message)
}

fun List<Result>.toListFilmModelItem(): List<FilmModelItem> {
    val arrayList = arrayListOf<FilmModelItem>()
    this.asSequence().forEach {
        arrayList.add(FilmModelItem(it.headline, it.display_title, it.link.url ))
    }
    return arrayList
}