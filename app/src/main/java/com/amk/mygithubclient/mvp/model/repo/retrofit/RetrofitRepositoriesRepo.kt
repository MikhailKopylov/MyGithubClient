package com.amk.mygithubclient.mvp.model.repo.retrofit

import com.amk.mygithubclient.mvp.model.api.IDataSource
import com.amk.mygithubclient.mvp.model.entity.GithubRepository
import com.amk.mygithubclient.mvp.model.entity.GithubUser
import com.amk.mygithubclient.mvp.model.entity.room.Database
import com.amk.mygithubclient.mvp.model.entity.room.RoomGithubRepository
import com.amk.mygithubclient.mvp.model.network.INetworkStatus
import com.amk.mygithubclient.mvp.model.repo.IGithubRepositoriesRepo
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class RetrofitRepositoriesRepo(
    private val api: IDataSource,
    private val networkStatus: INetworkStatus,
    private val db: Database
) : IGithubRepositoriesRepo {

    override fun getRepositories(user: GithubUser): Single<List<GithubRepository>> =
        user.reposUrl?.let { reposUtl ->
            networkStatus.isOnlineSingle().flatMap { isOnline ->
                if (isOnline) {
                    api.getRepositories(reposUtl).flatMap { listRepository ->
                        listRepository.map {
                            db.repositoryDao.insert(
                                RoomGithubRepository(
                                    it.id,
                                    it.name ?: "",
                                    it.forksCount ?: 0,
                                    user.id,
                                    it.watchersCount ?: 0,
                                    it.language ?: ""
                                )
                            )
                        }
                        Single.fromCallable { listRepository }
                    }
                } else {
                    Single.fromCallable {
                        db.repositoryDao.findForUser(user.id).map {
                            GithubRepository(
                                it.id,
                                it.name,
                                it.forksCount,
                                it.watchersCount,
                                it.language
                            )
                        }
                    }
                }
            }.subscribeOn(Schedulers.io())
        } ?: Single.error(RuntimeException("No such repositories "))
}