package com.teamwss.websoso.ui.main.home.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.teamwss.websoso.data.model.HomeNovel

class HomeAdapter: ListAdapter<HomeNovel, HomeViewHolder>(HomeDiffCallback) {

    companion object {
        private val HomeDiffCallback = object :
            DiffUtil.ItemCallback<HomeNovel>() {
            override fun areItemsTheSame(
                oldItem: HomeNovel,
                newItem: HomeNovel
            ): Boolean {
                return oldItem.novelId == newItem.novelId
            }

            override fun areContentsTheSame(
                oldItem: HomeNovel,
                newItem: HomeNovel
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val item = getItem(position)
        holder.onBind(item)
    }
}