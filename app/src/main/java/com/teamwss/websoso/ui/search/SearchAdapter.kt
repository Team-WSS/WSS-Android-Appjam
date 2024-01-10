package com.teamwss.websoso.ui.search

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teamwss.websoso.ui.search.model.SearchResult

class SearchAdapter : RecyclerView.Adapter<SearchViewHolder>() {
    private var novelItems: List<SearchResult> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder.create(parent)
    }

    override fun getItemCount() = novelItems.size

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.onBind(novelItems[position])
    }

    fun setResultNovelList(novelList: List<SearchResult>) {
        this.novelItems = novelList.toList()
        notifyDataSetChanged()
    }
}