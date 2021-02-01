package com.amk.mygithubclient.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amk.mygithubclient.R
import com.amk.mygithubclient.mvp.presenter.list.IRepositoryListPresenter
import com.amk.mygithubclient.mvp.view.list.RepositoryItemView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_repository.view.*

class RepositoriesRVAdapter(val presenter: IRepositoryListPresenter) :
    RecyclerView.Adapter<RepositoriesRVAdapter.ViewHolder>() {

    inner class ViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView),
        LayoutContainer, RepositoryItemView {
        override fun setName(text: String) = with(containerView) {
            tv_name.text = text
        }

        override var pos = -1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_repository, parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.pos = position
        holder.containerView.setOnClickListener { presenter.itemClickListener?.invoke(holder) }
        presenter.bindView(holder)
    }

    override fun getItemCount() = presenter.getCount()
}