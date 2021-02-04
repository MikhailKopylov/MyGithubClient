package com.amk.mygithubclient.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.amk.mygithubclient.mvp.model.api.ApiHolder
import com.amk.mygithubclient.App
import com.amk.mygithubclient.R
import com.amk.mygithubclient.mvp.model.cash.room.RoomGithubRepositoriesCache
import com.amk.mygithubclient.mvp.model.contextImplementation.AndroidNetworkStatus
import com.amk.mygithubclient.mvp.model.entity.GithubUser
import com.amk.mygithubclient.mvp.model.entity.room.Database
import com.amk.mygithubclient.mvp.model.repo.retrofit.RetrofitRepositoriesRepo
import com.amk.mygithubclient.mvp.presenter.UserPresenter
import com.amk.mygithubclient.mvp.view.UserView
import com.amk.mygithubclient.ui.BackButtonListener
import com.amk.mygithubclient.ui.adapter.RepositoriesRVAdapter
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_user.*
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class UserFragment : MvpAppCompatFragment(), UserView, BackButtonListener {

    @Inject lateinit var database: Database
    @Inject lateinit var router: Router


    val presenter: UserPresenter by moxyPresenter {
        val user = arguments?.getParcelable<GithubUser>(USER_ARG) as GithubUser
        UserPresenter(
            user,
            AndroidSchedulers.mainThread(),
            RetrofitRepositoriesRepo(
                ApiHolder().api,
                AndroidNetworkStatus(context!!),
                RoomGithubRepositoriesCache(database)
            ),
            router,
            )

    }

    private var adapter: RepositoriesRVAdapter? = null


    companion object {
        private const val USER_ARG = "userArg"

        fun newInstance(user: GithubUser) = UserFragment().apply {
            arguments = Bundle().apply {
                putParcelable(USER_ARG, user)
            }
            App.instance.appComponent.inject(this)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        View.inflate(context, R.layout.fragment_user, null)

    override fun init() {
        rv_repositories.layoutManager = LinearLayoutManager(context)
        adapter = RepositoriesRVAdapter(presenter.repositoriesListPresenter)
        rv_repositories.adapter = adapter
    }

    override fun updateList() {
        adapter?.notifyDataSetChanged()
    }

    override fun backPressed() = presenter.backPressed()


}