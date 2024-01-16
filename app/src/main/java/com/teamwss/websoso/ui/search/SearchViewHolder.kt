package com.teamwss.websoso.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teamwss.websoso.data.remote.response.SearchNovelsResponse
import com.teamwss.websoso.databinding.ItemSearchNovelBinding
import com.teamwss.websoso.util.loadCoverImageRounded14
import kotlin.properties.Delegates

class SearchViewHolder(
    private val binding: ItemSearchNovelBinding,
    private val onItemClick: (Long) -> Unit
) :
    RecyclerView.ViewHolder(binding.root) {

    var novelId by Delegates.notNull<Long>()

    init {
        binding.root.setOnClickListener { onItemClick(novelId) }
    }

    fun onBind(searchResult: SearchNovelsResponse.Novel) {
        with(binding) {
            loadCoverImageRounded14(ivSearchNovel, searchResult.novelImg)
            tvSearchNovelTitle.text = searchResult.novelTitle
            tvSearchNovelAuthor.text = searchResult.novelAuthor
            tvSearchNovelGenre.text = searchResult.novelGenre
            novelId = searchResult.novelId
        }
    }

    companion object {
        fun create(parent: ViewGroup, onItemClick: (Long) -> Unit): SearchViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemSearchNovelBinding.inflate(inflater, parent, false)
            return SearchViewHolder(binding, onItemClick)
        }
    }
}