package com.amk.mygithubclient.navigation

import com.amk.mygithubclient.mvp.model.entity.GithubRepository
import com.amk.mygithubclient.mvp.model.entity.GithubUser
import com.amk.mygithubclient.ui.fragments.RepositoryFragment
import com.amk.mygithubclient.ui.fragments.UserFragment
import com.amk.mygithubclient.ui.fragments.UsersFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class Screens {
    class UsersScreen : SupportAppScreen() {
        override fun getFragment() = UsersFragment.newInstance()
    }

    class UserScreen(private val user: GithubUser) : SupportAppScreen() {
        override fun getFragment() = UserFragment.newInstance(user)
    }

    class RepositoryScreen(private val repository: GithubRepository) : SupportAppScreen() {
        override fun getFragment() = RepositoryFragment.getInstance(repository)
    }
}