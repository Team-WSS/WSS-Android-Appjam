package com.teamwss.websoso.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.teamwss.websoso.databinding.ItemSearchNovelBinding
import com.teamwss.websoso.ui.search.model.SearchResult

class SearchViewHolder(private val binding: ItemSearchNovelBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun onBind(searchNovel: SearchResult) {
        with(binding) {
            ivSearchNovel.load(searchNovel.resultNovelImage)
            tvSearchNovelTitle.text = searchNovel.resultNovelTitle
            tvSearchNovelAuthor.text = searchNovel.resultNovelAuthor
            tvSearchNovelGenre.text = searchNovel.resultNovelGenre
        }
    }

    companion object {
        fun create(parent: ViewGroup): SearchViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemSearchNovelBinding.inflate(inflater, parent, false)
            return SearchViewHolder(binding)
        }
    }
}