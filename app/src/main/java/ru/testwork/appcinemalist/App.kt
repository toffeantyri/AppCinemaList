package ru.testwork.appcinemalist

import android.app.Application

lateinit var APP_CONTEXT : Application

class App : Application() {

    companion object {

    }


    override fun onCreate() {
        log(this::class.java.simpleName + ": onCreate")
        APP_CONTEXT = this
        super.onCreate()

    }

}