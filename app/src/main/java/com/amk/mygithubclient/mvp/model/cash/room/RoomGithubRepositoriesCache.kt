package com.amk.mygithubclient.mvp.model.cash.room

import com.amk.mygithubclient.mvp.model.cash.IGithubRepositoriesCache
import com.amk.mygithubclient.mvp.model.entity.GithubRepository
import com.amk.mygithubclient.mvp.model.entity.GithubUser
import com.amk.mygithubclient.mvp.model.entity.room.Database
import com.amk.mygithubclient.mvp.model.entity.room.RoomGithubRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.internal.operators.completable.CompletableError

class RoomGithubRepositoriesCache(private val db: Database) : IGithubRepositoriesCache {
    override fun addRepository(
        user: GithubUser,
        repositories: List<GithubRepository>
    ): Completable =
        try {
            repositories.map {
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
            Completable.complete()
        } catch (e: Exception) {
            CompletableError(e)
        }

    override fun getRepositories(user: GithubUser): Single<List<GithubRepository>> =
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