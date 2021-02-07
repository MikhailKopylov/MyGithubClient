package com.amk.mygithubclient.mvp.presenter

import com.amk.mygithubclient.mvp.model.entity.GithubRepository
import com.amk.mygithubclient.mvp.view.RepositoryView
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class RepositoryPresenter(
    private val repository: GithubRepository,
) : MvpPresenter<RepositoryView>() {

    @Inject
    lateinit var router: Router

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        init()
    }

    private fun init() {
        repository.name?.let { viewState.setName(it) }
        repository.forksCount?.let { viewState.setForkCount(it) }
        repository.watchersCount?.let { viewState.setWatchersCount(it) }
        repository.language?.let { viewState.setLanguage(it) }
    }

    fun backPressed(): Boolean {
        router.exit()
        return true
    }
}