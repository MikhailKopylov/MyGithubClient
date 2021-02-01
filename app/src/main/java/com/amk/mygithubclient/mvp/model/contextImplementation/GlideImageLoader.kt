package com.amk.mygithubclient.mvp.model.contextImplementation

import android.widget.ImageView
import com.amk.mygithubclient.mvp.model.image.IImageLoader
import com.bumptech.glide.Glide

class GlideImageLoader : IImageLoader<ImageView> {
    override fun loadInto(url: String, container: ImageView) {
        Glide.with(container.context)
            .load(url)
            .into(container)

    }
}