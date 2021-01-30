package com.amk.mygithubclient.mvp.model.repo.retrofit

import com.amk.mygithubclient.mvp.model.api.IDataSource
import com.amk.mygithubclient.mvp.model.entity.GithubUser
import com.amk.mygithubclient.mvp.model.entity.room.Database
import com.amk.mygithubclient.mvp.model.entity.room.RoomGithubUser
import com.amk.mygithubclient.mvp.model.network.INetworkStatus
import com.amk.mygithubclient.mvp.model.repo.IGithubUsersRepo
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class RetrofitGithubUsersRepo(
    private val api: IDataSource,
    private val networkStatus: INetworkStatus,
    private val db: Database
) : IGithubUsersRepo {
    override fun getUsers(): Single<List<GithubUser>> =
        networkStatus.isOnlineSingle().flatMap { isOnline ->
            if (isOnline) {
                api.getUsers()
                    .flatMap { listGithubUsers ->
                        val listRoomGithubUser = listGithubUsers.map {
                            RoomGithubUser(
                                it.id,
                                it.login,
                                it.avatarUrl ?: "",
                                it.reposUrl ?: ""
                            )
                        }
                        db.userDao.insert(listRoomGithubUser)
                        Single.fromCallable { listGithubUsers }
                    }
            } else {
                Single.fromCallable {
                    db.userDao.getAll().map {
                        GithubUser(it.id, it.login, it.avatarUrl, it.reposUrl)
                    }
                }
            }
        }.subscribeOn(Schedulers.io())
}