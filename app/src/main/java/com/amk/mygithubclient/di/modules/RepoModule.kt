package com.amk.mygithubclient.di.modules

import com.amk.mygithubclient.mvp.model.api.IDataSource
import com.amk.mygithubclient.mvp.model.cache.IGithubRepositoriesCache
import com.amk.mygithubclient.mvp.model.cache.IGithubUsersCache
import com.amk.mygithubclient.mvp.model.network.INetworkStatus
import com.amk.mygithubclient.mvp.model.repo.IGithubRepositoriesRepo
import com.amk.mygithubclient.mvp.model.repo.IGithubUsersRepo
import com.amk.mygithubclient.mvp.model.repo.retrofit.RetrofitGithubUsersRepo
import com.amk.mygithubclient.mvp.model.repo.retrofit.RetrofitRepositoriesRepo
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepoModule {

    @Singleton
    @Provides
    fun usersRepo(
        api: IDataSource,
        networkStatus: INetworkStatus,
        cache: IGithubUsersCache
    ): IGithubUsersRepo = RetrofitGithubUsersRepo(api, networkStatus, cache)

    @Singleton
    @Provides
    fun repositoriesRepo(
        api: IDataSource,
        networkStatus: INetworkStatus,
        cache: IGithubRepositoriesCache
    ): IGithubRepositoriesRepo = RetrofitRepositoriesRepo(api, networkStatus, cache)
}