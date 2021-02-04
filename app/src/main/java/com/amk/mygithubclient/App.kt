package com.amk.mygithubclient

import android.app.Application
import com.amk.mygithubclient.di.AppComponent
import com.amk.mygithubclient.di.DaggerAppComponent
import com.amk.mygithubclient.di.modules.AppModule

class App : Application() {
    companion object {
        lateinit var instance: App
    }

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        instance = this

        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }
}
