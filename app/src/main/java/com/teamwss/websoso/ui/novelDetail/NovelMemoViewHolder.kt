package com.teamwss.websoso.ui.novelDetail

import androidx.recyclerview.widget.RecyclerView
import com.teamwss.websoso.data.remote.response.NovelMemoResponse
import com.teamwss.websoso.databinding.ItemNovelMemoBinding

class NovelMemoViewHolder(
    private val binding: ItemNovelMemoBinding,
    private val clickListener: (Long) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(memo: NovelMemoResponse) {

        binding.novelMemos = memo
        binding.root.setOnClickListener {
            clickListener(memo.memoId)
        }
    }
}
