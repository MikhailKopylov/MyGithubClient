package com.amk.mygithubclient.mvp.model.entity

import android.os.Parcelable
import com.google.gson.annotations.Expose
import kotlinx.android.parcel.Parcelize

@Parcelize
class GithubRepository(
    @Expose val id: String,
    @Expose val name: String? = null,
    @Expose val forksCount: Int? = null,
    @Expose val watchersCount: Int? = null,
    @Expose val language: String? = null,
    ) : Parcelable