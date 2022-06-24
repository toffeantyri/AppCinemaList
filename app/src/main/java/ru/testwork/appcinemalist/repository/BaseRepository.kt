package ru.testwork.appcinemalist.repository

import androidx.lifecycle.MutableLiveData

abstract class BaseRepository<T>() {

    val dataEmitter: MutableLiveData<T> by lazy { MutableLiveData() }


}