package com.teamwss.websoso.ui.main.home.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.teamwss.websoso.data.model.HomeNovelEntity

class HomeAdapter : ListAdapter<HomeNovelEntity, HomeViewHolder>(HomeDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val item = getItem(position)
        holder.onBind(item)
    }

    companion object {
        private val HomeDiffCallback = object :
            DiffUtil.ItemCallback<HomeNovelEntity>() {
            override fun areItemsTheSame(
                oldItem: HomeNovelEntity,
                newItem: HomeNovelEntity
            ): Boolean {
                return oldItem.novelId == newItem.novelId
            }

            override fun areContentsTheSame(
                oldItem: HomeNovelEntity,
                newItem: HomeNovelEntity
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}