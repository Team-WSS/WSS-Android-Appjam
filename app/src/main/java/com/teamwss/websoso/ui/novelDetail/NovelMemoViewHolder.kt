package com.teamwss.websoso.ui.novelDetail

import androidx.recyclerview.widget.RecyclerView
import com.teamwss.websoso.data.remote.response.NovelMemoResponse
import com.teamwss.websoso.databinding.ItemNovelMemoBinding

class NovelMemoViewHolder(private val binding: ItemNovelMemoBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun onBind(novelMemoResponse: NovelMemoResponse) {
        binding.novelMemo = novelMemoResponse
    }
}