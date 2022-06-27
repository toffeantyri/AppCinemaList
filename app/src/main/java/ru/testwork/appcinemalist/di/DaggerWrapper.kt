package ru.testwork.appcinemalist.di

object DaggerWrapper {
    lateinit var appComponent: AppComponent

    fun initAppComponent(){
        appComponent = DaggerAppComponent.builder().build()
    }
}