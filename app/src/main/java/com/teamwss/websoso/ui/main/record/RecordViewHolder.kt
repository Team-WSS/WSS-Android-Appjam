package com.teamwss.websoso.ui.main.record

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teamwss.websoso.data.Memo
import com.teamwss.websoso.databinding.ItemMemoBinding

class RecordViewHolder(private val binding: ItemMemoBinding) :
    RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun create(parent: ViewGroup): RecordViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemMemoBinding.inflate(inflater, parent, false)
            return RecordViewHolder(binding)
        }
    }

    fun onBind(memo: Memo) {
        with(binding) {
            tvMemoDate.text = memo.novelDate
            tvMemoTitle.text = memo.novelTitle
            tvMemoContent.text = memo.novelContent
        }
    }
}