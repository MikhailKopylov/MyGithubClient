package com.amk.mygithubclient.ui

import android.os.Bundle
import com.amk.mygithubclient.App
import com.amk.mygithubclient.R
import com.amk.mygithubclient.mvp.presenter.MainPresenter
import com.amk.mygithubclient.mvp.view.MainView
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter
import ru.geekbrains.githubclient.rxjavatest.BackPressure
import ru.terrakok.cicerone.android.support.SupportAppNavigator

class MainActivity : MvpAppCompatActivity(), MainView {
    val navigatorHolder = App.instance.navigatorHolder
    val navigator = SupportAppNavigator(this, supportFragmentManager, R.id.container)

    private val presenter by moxyPresenter {
        MainPresenter(App.instance.router)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Creation().exec()
        //Operators().exec()
        //Sources().exec()
//        BackPressure().exec()
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.removeNavigator()
    }

    override fun onBackPressed() {
        supportFragmentManager.fragments.forEach {
            if (it is BackButtonListener && it.backPressed()) {
                return
            }
        }

        presenter.backClicked()
    }

}