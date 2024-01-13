package com.teamwss.websoso.ui.main.library.adapter

import androidx.recyclerview.widget.RecyclerView
import com.teamwss.websoso.data.model.LibraryUserNovel
import com.teamwss.websoso.databinding.ItemLibraryViewPagerBinding

class LibraryViewPagerViewHolder(
    private val binding: ItemLibraryViewPagerBinding,
) : RecyclerView.ViewHolder(binding.root) {
    private val libraryItemAdapter = LibraryItemAdapter()

    fun bind(novels: List<LibraryUserNovel>) {
        libraryItemAdapter.submitList(novels)
        binding.rvLibraryViewPager.adapter = libraryItemAdapter
    }
}