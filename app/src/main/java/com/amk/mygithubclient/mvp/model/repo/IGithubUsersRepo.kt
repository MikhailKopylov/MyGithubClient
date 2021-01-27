package com.amk.mygithubclient.mvp.model.repo

import com.amk.mygithubclient.mvp.model.entity.GithubUser
import io.reactivex.rxjava3.core.Single

interface IGithubUsersRepo {
    fun getUsers(): Single<List<GithubUser>>
}