package com.teamwss.websoso.ui.main.home.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.teamwss.websoso.data.model.SosoPickNovelEntity

class HomeAdapter : ListAdapter<SosoPickNovelEntity, HomeViewHolder>(HomeDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val item = getItem(position)
        holder.onBind(item)
    }

    companion object {
        private val HomeDiffCallback = object :
            DiffUtil.ItemCallback<SosoPickNovelEntity>() {
            override fun areItemsTheSame(
                oldItem: SosoPickNovelEntity,
                newItem: SosoPickNovelEntity
            ): Boolean {
                return oldItem.novelImg == newItem.novelImg
            }

            override fun areContentsTheSame(
                oldItem: SosoPickNovelEntity,
                newItem: SosoPickNovelEntity
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}