package com.amk.mygithubclient.mvp.model.repo.retrofit

import com.amk.mygithubclient.mvp.model.api.IDataSource
import com.amk.mygithubclient.mvp.model.cash.IGithubUsersCache
import com.amk.mygithubclient.mvp.model.entity.GithubUser
import com.amk.mygithubclient.mvp.model.network.INetworkStatus
import com.amk.mygithubclient.mvp.model.repo.IGithubUsersRepo
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class RetrofitGithubUsersRepo(
    private val api: IDataSource,
    private val networkStatus: INetworkStatus,
    private val cache: IGithubUsersCache
) : IGithubUsersRepo {

    override fun getUsers(): Single<List<GithubUser>> =
        networkStatus.isOnlineSingle().flatMap { isOnline ->
            if (isOnline) {
                api.getUsers().flatMap {
                    cache.addUsers(it).toSingleDefault(it)
                }
            } else {
                cache.getUsers()
            }
        }.subscribeOn(Schedulers.io())
}