package com.teamwss.websoso.ui.main.library.adapter

import androidx.recyclerview.widget.RecyclerView
import com.teamwss.websoso.data.model.LibraryUserNovelEntity
import com.teamwss.websoso.databinding.ItemLibraryViewPagerBinding

class LibraryViewPagerViewHolder(
    private val binding: ItemLibraryViewPagerBinding,
) : RecyclerView.ViewHolder(binding.root) {
    private val libraryItemAdapter = LibraryItemAdapter()

    fun bind(novels: List<LibraryUserNovelEntity>) {
        libraryItemAdapter.submitList(novels)
        binding.rvLibraryViewPager.adapter = libraryItemAdapter
    }
}