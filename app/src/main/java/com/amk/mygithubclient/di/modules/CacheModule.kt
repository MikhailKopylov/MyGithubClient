package com.amk.mygithubclient.di.modules

import androidx.room.Room
import com.amk.mygithubclient.App
import com.amk.mygithubclient.mvp.model.cash.IGithubUsersCache
import com.amk.mygithubclient.mvp.model.cash.room.RoomGithubUsersCache
import com.amk.mygithubclient.mvp.model.entity.room.Database
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CacheModule {

    @Singleton
    @Provides
    fun database(app: App): Database = Room.databaseBuilder(app, Database::class.java, Database.DB_NAME)
        .build()


    @Singleton
    @Provides
    fun usersCache(database: Database): IGithubUsersCache = RoomGithubUsersCache(database)
}
