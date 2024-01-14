package com.teamwss.websoso.ui.main.library.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.teamwss.websoso.data.model.LibraryUserNovelEntity
import com.teamwss.websoso.databinding.ItemLibraryViewPagerBinding

class LibraryViewPagerViewHolder(
    private val binding: ItemLibraryViewPagerBinding,
    private val onItemClick: (Long) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    private val libraryItemAdapter = LibraryItemAdapter(onItemClick)

    fun bind(novels: List<LibraryUserNovelEntity>) {
        binding.clLibraryEmpty.apply {
            visibility = if (novels.isEmpty()) View.VISIBLE else View.GONE
        }

        libraryItemAdapter.submitList(novels)
        binding.rvLibraryViewPager.adapter = libraryItemAdapter
    }
}