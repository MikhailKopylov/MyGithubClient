package com.amk.mygithubclient.mvp.model.cash

import com.amk.mygithubclient.mvp.model.entity.GithubUser
import com.amk.mygithubclient.mvp.model.entity.room.RoomGithubUser
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface IGithubUsersCache {
    fun getUsers(): Single<List<GithubUser>>
    fun addUsers(users:List<GithubUser>):Completable
}