package com.amk.mygithubclient.di.modules

import com.amk.mygithubclient.App
import dagger.Module
import dagger.Provides

@Module
class AppModule(val app: App) {

    @Provides
    fun app(): App {
        return app
    }

}
