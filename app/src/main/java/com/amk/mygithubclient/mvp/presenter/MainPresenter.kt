package com.amk.mygithubclient.mvp.presenter

import com.amk.mygithubclient.mvp.view.MainView
import com.amk.mygithubclient.navigation.Screens
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router

class MainPresenter(val router: Router) : MvpPresenter<MainView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        router.replaceScreen(Screens.UsersScreen())
    }

    fun backClicked() {
        router.exit()
    }

}