package com.teamwss.websoso.ui.main.record

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teamwss.websoso.databinding.ItemMemoBinding
import com.teamwss.websoso.ui.main.record.model.Memo

class RecordViewHolder(private val binding: ItemMemoBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun onBind(memo: Memo) {
        with(binding) {
            tvMemoDate.text = memo.novelDate
            tvMemoTitle.text = memo.novelTitle
            tvMemoContent.text = memo.novelContent
        }
    }

    companion object {
        fun create(parent: ViewGroup): RecordViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemMemoBinding.inflate(inflater, parent, false)
            return RecordViewHolder(binding)
        }
    }
}