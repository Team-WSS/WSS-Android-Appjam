package com.teamwss.websoso.ui.main.library.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teamwss.websoso.data.model.LibraryUserNovel
import com.teamwss.websoso.databinding.ItemLibraryViewPagerBinding

class LibraryViewPagerAdapter() :
    RecyclerView.Adapter<LibraryViewPagerViewHolder>() {

    private var userNovels = emptyList<LibraryUserNovel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibraryViewPagerViewHolder {
        val binding =
            ItemLibraryViewPagerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LibraryViewPagerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LibraryViewPagerViewHolder, position: Int) {
        holder.bind(userNovels)
    }

    fun fetchUserNovels(userNovels: List<LibraryUserNovel>) {
        this.userNovels = userNovels
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = 5
}