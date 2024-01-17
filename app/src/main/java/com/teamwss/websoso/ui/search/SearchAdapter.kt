package com.teamwss.websoso.ui.search

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teamwss.websoso.data.remote.response.SearchNovelsResponse

class SearchAdapter(private val onItemClick: (Long) -> Unit) :
    RecyclerView.Adapter<SearchViewHolder>() {
    private var novelItems: List<SearchNovelsResponse.Novel> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder.create(parent, onItemClick)
    }

    override fun getItemCount() = novelItems.size

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.onBind(novelItems[position])
    }

    fun setResultNovelList(novelList: List<SearchNovelsResponse.Novel>) {
        this.novelItems = novelList.toList()
        notifyDataSetChanged()
    }
}