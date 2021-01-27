package com.amk.mygithubclient.mvp.presenter.list

import com.amk.mygithubclient.mvp.view.list.IItemView

interface IListPresenter<V : IItemView> {
    var itemClickListener: ((V) -> Unit)?
    fun bindView(view: V)
    fun getCount(): Int
}