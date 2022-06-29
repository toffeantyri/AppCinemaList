package ru.testwork.appcinemalist

import android.util.Log
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.scan
import ru.testwork.appcinemalist.busines.model.FilmModelItem
import ru.testwork.appcinemalist.busines.model.jsonmodels.Result


fun log(message: String) {
    Log.d(LOG_TAG, message)
}

fun List<Result>.toListFilmModelItem(): List<FilmModelItem> {
    val arrayList = arrayListOf<FilmModelItem>()
    this.asSequence().forEach {
        arrayList.add(FilmModelItem(it.display_title, it.summary_short, it.multimedia.src ))
    }
    return arrayList
}

@ExperimentalCoroutinesApi
fun <T> Flow<T>.simpleScan(count: Int): Flow<List<T?>> {
    val items = List<T?>(count) { null }
    return this.scan(items) { previous, value -> previous.drop(1) + value }
}