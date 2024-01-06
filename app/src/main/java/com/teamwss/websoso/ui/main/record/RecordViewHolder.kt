package com.teamwss.websoso.ui.main.record

import androidx.recyclerview.widget.RecyclerView
import com.teamwss.websoso.data.Memo
import com.teamwss.websoso.databinding.ItemMemoBinding

class RecordViewHolder(private val binding: ItemMemoBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun onBind(memo: Memo) {
        binding.tvMemoDate.text = memo.novelDate
        binding.tvMemoTitle.text = memo.novelTitle
        binding.tvMemoContent.text = memo.novelContent
    }

}