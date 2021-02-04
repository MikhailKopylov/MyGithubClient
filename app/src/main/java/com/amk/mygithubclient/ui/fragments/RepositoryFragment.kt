package com.amk.mygithubclient.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.amk.mygithubclient.App
import com.amk.mygithubclient.R
import com.amk.mygithubclient.mvp.model.entity.GithubRepository
import com.amk.mygithubclient.mvp.presenter.RepositoryPresenter
import com.amk.mygithubclient.mvp.view.RepositoryView
import com.amk.mygithubclient.ui.BackButtonListener
import kotlinx.android.synthetic.main.fragment_repository.*
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class RepositoryFragment : MvpAppCompatFragment(), RepositoryView, BackButtonListener {

    @Inject
    lateinit var router: Router

    private val presenter by moxyPresenter {
        val repository = arguments?.get(REPOSITORY_ARG) as GithubRepository
        RepositoryPresenter(
            repository,
            router
        )

    }

    companion object {
        private const val REPOSITORY_ARG = "repositoryArg"

        fun getInstance(repository: GithubRepository) = RepositoryFragment().apply {
            arguments = Bundle().apply {
                putParcelable(REPOSITORY_ARG, repository)
            }
            App.instance.appComponent.inject(this)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = View.inflate(context, R.layout.fragment_repository, null)

    override fun setName(name: String) {
        tv_name.text = name
    }

    @SuppressLint("SetTextI18n")
    override fun setForkCount(forkCount: Int) {
        tv_fork_count.text = "Fork count = $forkCount"
    }

    @SuppressLint("SetTextI18n")
    override fun setWatchersCount(watcherCount: Int) {
        tv_watchers_count.text = "Watchers count = $watcherCount"
    }

    @SuppressLint("SetTextI18n")
    override fun setLanguage(language: String) {
        tv_language.text = "Language = $language"
    }

    override fun backPressed() = presenter.backPressed()

}