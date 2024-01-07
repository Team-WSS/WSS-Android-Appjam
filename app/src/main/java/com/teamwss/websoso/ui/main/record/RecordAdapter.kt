package com.teamwss.websoso.ui.main.record

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teamwss.websoso.data.Memo
import com.teamwss.websoso.databinding.ItemMemoBinding

class RecordAdapter : RecyclerView.Adapter<RecordViewHolder>() {
    private var memoList: List<Memo> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMemoBinding.inflate(inflater, parent, false)
        return RecordViewHolder(binding)
    }

    override fun getItemCount() = memoList.size

    fun setFriendList(memoList: List<Memo>) {
        this.memoList = memoList.toList()
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RecordViewHolder, position: Int) {
        holder.onBind(memoList[position])
    }
}