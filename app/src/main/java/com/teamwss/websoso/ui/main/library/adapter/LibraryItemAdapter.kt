package com.teamwss.websoso.ui.main.library.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.teamwss.websoso.data.model.LibraryUserNovelEntity
import com.teamwss.websoso.databinding.ItemLibraryNovelBinding

class LibraryItemAdapter(
    private val onItemClick: (Long) -> Unit
) :
    ListAdapter<LibraryUserNovelEntity, LibraryItemViewHolder>(FollowerDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibraryItemViewHolder {
        val binding =
            ItemLibraryNovelBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LibraryItemViewHolder(binding,onItemClick)
    }

    override fun onBindViewHolder(holder: LibraryItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val FollowerDiffCallback = object :
            DiffUtil.ItemCallback<LibraryUserNovelEntity>() {
            override fun areItemsTheSame(
                oldItem: LibraryUserNovelEntity,
                newItem: LibraryUserNovelEntity
            ): Boolean {
                return oldItem.userNovelId == newItem.userNovelId
            }

            override fun areContentsTheSame(
                oldItem: LibraryUserNovelEntity,
                newItem: LibraryUserNovelEntity
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}