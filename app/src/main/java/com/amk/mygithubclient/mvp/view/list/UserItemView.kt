package com.amk.mygithubclient.mvp.view.list

interface UserItemView : IItemView {
    fun setLogin(text: String)
    fun loadAvatar(url: String)
}