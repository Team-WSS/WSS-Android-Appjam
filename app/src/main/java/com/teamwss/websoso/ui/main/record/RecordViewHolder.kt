package com.teamwss.websoso.ui.main.record

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teamwss.websoso.databinding.ItemMemoBinding
import com.teamwss.websoso.ui.main.record.model.Memo
import kotlin.properties.Delegates

class RecordViewHolder(
    private val binding: ItemMemoBinding,
    private val onItemClick: (memoId : Long) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    private var memoId by Delegates.notNull<Long>()

    init {
        binding.root.setOnClickListener {
            if (adapterPosition != RecyclerView.NO_POSITION) {
                onItemClick(memoId)
            }
        }
    }

    fun onBind(memo: Memo) {
        memoId = memo.memoId
        with(binding) {
            tvMemoDate.text = memo.memoDate
            tvMemoTitle.text = memo.novelTitle
            tvMemoContent.text = memo.memoContent
        }
    }

    companion object {
        fun create(parent: ViewGroup, onItemClick: (memoId : Long) -> Unit): RecordViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemMemoBinding.inflate(inflater, parent, false)
            return RecordViewHolder(binding, onItemClick)
        }
    }
}
