package com.amk.mygithubclient.di

import com.amk.mygithubclient.di.modules.*
import com.amk.mygithubclient.mvp.presenter.MainPresenter
import com.amk.mygithubclient.mvp.presenter.RepositoryPresenter
import com.amk.mygithubclient.mvp.presenter.UserPresenter
import com.amk.mygithubclient.mvp.presenter.UsersPresenter
import com.amk.mygithubclient.ui.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        CiceroneModule::class,
        CacheModule::class,
        ApiModule::class,
        RepoModule::class
    ]
)
interface AppComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(mainPresenter: MainPresenter)
    fun inject(usersPresenter: UsersPresenter)
    fun inject(userPresenter: UserPresenter)
    fun inject(repositoryPresenter: RepositoryPresenter)
}

