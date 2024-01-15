package com.teamwss.websoso.ui.novelDetail

import androidx.recyclerview.widget.RecyclerView
import com.teamwss.websoso.data.remote.response.NovelMemoResponse
import com.teamwss.websoso.databinding.ItemNovelMemoBinding

class NovelMemoViewHolder(private val binding: ItemNovelMemoBinding, clickListener: (position: Int) -> Unit) :
    RecyclerView.ViewHolder(binding.root) {

    init {
        binding.root.setOnClickListener {
            clickListener(adapterPosition)
        }
    }

    fun onBind(novelMemoResponse: NovelMemoResponse) {
        binding.novelMemos = novelMemoResponse
    }
}