package com.amk.mygithubclient.mvp.model.repo

import com.amk.mygithubclient.mvp.model.entity.GithubRepository
import com.amk.mygithubclient.mvp.model.entity.GithubUser
import io.reactivex.rxjava3.core.Single

interface IGithubRepositoriesRepo {
    fun getRepositories(
        user: GithubUser,
    ): Single<List<GithubRepository>>
}