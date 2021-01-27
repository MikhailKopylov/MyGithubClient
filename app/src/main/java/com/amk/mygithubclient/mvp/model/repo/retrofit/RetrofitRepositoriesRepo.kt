package com.amk.mygithubclient.mvp.model.repo.retrofit

import com.amk.mygithubclient.mvp.model.api.IDataSource
import com.amk.mygithubclient.mvp.model.entity.GithubRepository
import com.amk.mygithubclient.mvp.model.entity.GithubUser
import com.amk.mygithubclient.mvp.model.repo.IGithubRepositoriesRepo
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class RetrofitRepositoriesRepo(private val api: IDataSource) : IGithubRepositoriesRepo {

    override fun getRepositories(user: GithubUser): Single<List<GithubRepository>> =
        user.reposUrl?.let {
            api.getRepositories(it).subscribeOn(Schedulers.io())
        } ?: Single.error(RuntimeException("No such repositories "))
}