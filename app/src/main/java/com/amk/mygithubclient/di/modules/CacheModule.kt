package com.amk.mygithubclient.di.modules

import androidx.room.Room
import com.amk.mygithubclient.App
import com.amk.mygithubclient.mvp.model.cache.IGithubRepositoriesCache
import com.amk.mygithubclient.mvp.model.cache.IGithubUsersCache
import com.amk.mygithubclient.mvp.model.cache.room.RoomGithubRepositoriesCache
import com.amk.mygithubclient.mvp.model.cache.room.RoomGithubUsersCache
import com.amk.mygithubclient.mvp.model.entity.room.Database
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CacheModule {

    @Singleton
    @Provides
    fun database(app: App): Database =
        Room.databaseBuilder(app, Database::class.java, Database.DB_NAME)
            .build()


    @Singleton
    @Provides
    fun usersCache(database: Database): IGithubUsersCache = RoomGithubUsersCache(database)

    @Singleton
    @Provides
    fun repoCache(database: Database): IGithubRepositoriesCache =
        RoomGithubRepositoriesCache(database)
}


