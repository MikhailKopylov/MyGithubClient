package com.amk.mygithubclient.mvp.model.cache

import com.amk.mygithubclient.mvp.model.entity.GithubRepository
import com.amk.mygithubclient.mvp.model.entity.GithubUser
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface IGithubRepositoriesCache {

    fun addRepository(user: GithubUser, repositories: List<GithubRepository>): Completable
    fun getRepositories(user: GithubUser): Single<List<GithubRepository>>

}