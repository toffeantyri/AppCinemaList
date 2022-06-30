package ru.testwork.appcinemalist

import android.app.Application
import android.content.Context
import ru.testwork.appcinemalist.di.AppComponent
import ru.testwork.appcinemalist.di.AppModule
import ru.testwork.appcinemalist.di.DaggerAppComponent
import ru.testwork.appcinemalist.util.APP_CONTEXT
import ru.testwork.appcinemalist.util.log


class MyApp : Application() {

    lateinit var appComponent: AppComponent


    override fun onCreate() {
        log(this::class.java.simpleName + ": onCreate")
        APP_CONTEXT = this
        super.onCreate()
        appComponent = DaggerAppComponent.builder().appModule(AppModule()).build()
    }
}

fun Context.appComponent(): AppComponent {
    return when (this) {
        is MyApp -> appComponent
        else -> this.applicationContext.appComponent()
    }
}