package com.teamwss.websoso.ui.main.record

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teamwss.websoso.ui.main.record.model.Memo

class RecordAdapter : RecyclerView.Adapter<RecordViewHolder>() {
    private var memoItems: List<Memo> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordViewHolder {
        return RecordViewHolder.create(parent)
    }

    override fun getItemCount() = memoItems.size

    fun setFriendList(memoList: List<Memo>) {
        this.memoItems = memoList.toList()
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RecordViewHolder, position: Int) {
        holder.onBind(memoItems[position])
    }
}