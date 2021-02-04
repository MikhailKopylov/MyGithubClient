package com.amk.mygithubclient.di

import com.amk.mygithubclient.di.modules.*
import com.amk.mygithubclient.mvp.presenter.MainPresenter
import com.amk.mygithubclient.mvp.presenter.UsersPresenter
import com.amk.mygithubclient.ui.MainActivity
import com.amk.mygithubclient.ui.fragments.RepositoryFragment
import com.amk.mygithubclient.ui.fragments.UserFragment
import com.amk.mygithubclient.ui.fragments.UsersFragment
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

    //При выполнении практического задания это должно отсюда уйти
    fun inject(userFragment: UserFragment)
    fun inject(repositoryFragment: RepositoryFragment)
    fun inject(usersFragment: UsersFragment)
}

