package com.amk.mygithubclient.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.amk.mygithubclient.ApiHolder
import com.amk.mygithubclient.App
import com.amk.mygithubclient.R
import com.amk.mygithubclient.mvp.model.repo.retrofit.RetrofitGithubUsersRepo
import com.amk.mygithubclient.mvp.presenter.UsersPresenter
import com.amk.mygithubclient.mvp.view.UsersView
import com.amk.mygithubclient.ui.BackButtonListener
import com.amk.mygithubclient.ui.adapter.UsersRVAdapter
import com.amk.mygithubclient.ui.image.GlideImageLoader
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_users.*
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

class UsersFragment : MvpAppCompatFragment(), UsersView, BackButtonListener {
    companion object {
        fun newInstance() = UsersFragment()
    }

    val presenter: UsersPresenter by moxyPresenter {
        UsersPresenter(
            AndroidSchedulers.mainThread(),
            RetrofitGithubUsersRepo(ApiHolder().api),
            App.instance.router
        )
    }

    var adapter: UsersRVAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) =
        View.inflate(context, R.layout.fragment_users, null)

    override fun init() {
        rv_users.layoutManager = LinearLayoutManager(context)
        adapter = UsersRVAdapter(presenter.usersListPresenter, GlideImageLoader())
        rv_users.adapter = adapter
    }

    override fun updateList() {
        adapter?.notifyDataSetChanged()
    }

    override fun backPressed() = presenter.backPressed()
}