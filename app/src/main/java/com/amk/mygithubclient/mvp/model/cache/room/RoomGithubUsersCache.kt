package com.amk.mygithubclient.mvp.model.cache.room

import com.amk.mygithubclient.mvp.model.cache.IGithubUsersCache
import com.amk.mygithubclient.mvp.model.entity.GithubUser
import com.amk.mygithubclient.mvp.model.entity.room.Database
import com.amk.mygithubclient.mvp.model.entity.room.RoomGithubUser
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.internal.operators.completable.CompletableError

class RoomGithubUsersCache(
    private val db: Database,
) : IGithubUsersCache {

    override fun getUsers(): Single<List<GithubUser>> = Single.fromCallable {
        db.userDao.getAll().map {
            GithubUser(it.id, it.login, it.avatarUrl, it.reposUrl)
        }
    }

    override fun addUsers(users: List<GithubUser>): Completable =
        try {
            val listRoomGithubUser = users.map {
                RoomGithubUser(
                    it.id,
                    it.login,
                    it.avatarUrl ?: "",
                    it.reposUrl ?: ""
                )
            }
            db.userDao.insert(listRoomGithubUser)
            Completable.complete()
        } catch (e: Exception) {
            CompletableError(e)
        }
}