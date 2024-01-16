package com.teamwss.websoso.ui.main.record

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teamwss.websoso.ui.main.record.model.Memo

class RecordAdapter(
    private val onItemClick : (memoId : Long) -> Unit
) : RecyclerView.Adapter<RecordViewHolder>() {
    private var memoItems: MutableList<Memo> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordViewHolder {
        return RecordViewHolder.create(parent,onItemClick)
    }

    override fun onBindViewHolder(holder: RecordViewHolder, position: Int) {
        holder.onBind(memoItems[position])

    }

    override fun getItemCount() = memoItems.size

    fun updateMemoItems(memos: List<Memo>) {
        this.memoItems = memos.toList().toMutableList()
        notifyDataSetChanged()
    }

}