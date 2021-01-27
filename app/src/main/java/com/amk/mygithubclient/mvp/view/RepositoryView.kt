package com.amk.mygithubclient.mvp.view

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface RepositoryView : MvpView {
    fun setName(name: String)
    fun setForkCount(forkCount: Int)
    fun setWatchersCount(watcherCount: Int)
    fun setLanguage(language: String)
}