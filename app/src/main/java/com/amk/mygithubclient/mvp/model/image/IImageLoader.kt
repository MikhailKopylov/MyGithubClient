package com.amk.mygithubclient.mvp.model.image

interface IImageLoader<T> {
    fun loadInto(url: String, container: T)
}