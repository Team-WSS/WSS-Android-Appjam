package com.teamwss.websoso.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teamwss.websoso.data.remote.response.SearchNovelsResponse
import com.teamwss.websoso.databinding.ItemSearchNovelBinding
import com.teamwss.websoso.util.loadCoverImageRounded6

class SearchViewHolder(private val binding: ItemSearchNovelBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun onBind(searchResult: SearchNovelsResponse.Novel) {
        with(binding) {
            loadCoverImageRounded6(ivSearchNovel, searchResult.novelImg)
            tvSearchNovelTitle.text = searchResult.novelTitle
            tvSearchNovelAuthor.text = searchResult.novelAuthor
            tvSearchNovelGenre.text = searchResult.novelGenre
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