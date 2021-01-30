package com.amk.mygithubclient.mvp.model.repo.retrofit

import com.amk.mygithubclient.mvp.model.api.IDataSource
import com.amk.mygithubclient.mvp.model.cash.IGithubRepositoriesCache
import com.amk.mygithubclient.mvp.model.entity.GithubRepository
import com.amk.mygithubclient.mvp.model.entity.GithubUser
import com.amk.mygithubclient.mvp.model.network.INetworkStatus
import com.amk.mygithubclient.mvp.model.repo.IGithubRepositoriesRepo
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class RetrofitRepositoriesRepo(
    private val api: IDataSource,
    private val networkStatus: INetworkStatus,

) : IGithubRepositoriesRepo {

    override fun getRepositories(user: GithubUser, cashe:IGithubRepositoriesCache): Single<List<GithubRepository>> =
        user.reposUrl?.let { reposUtl ->
            networkStatus.isOnlineSingle().flatMap { isOnline ->
                if (isOnline) {
                    api.getRepositories(reposUtl).flatMap {
                        cashe.addRepository(user, it).toSingleDefault(it)
                    }
                } else {
                    cashe.getRepositories(user)
                }
            }.subscribeOn(Schedulers.io())
        } ?: Single.error(RuntimeException("No such repositories "))
}