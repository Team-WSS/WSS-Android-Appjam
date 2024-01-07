package com.teamwss.websoso.ui.main.record

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teamwss.websoso.data.Memo
import com.teamwss.websoso.databinding.ItemMemoBinding

class RecordAdapter(context: Context) : RecyclerView.Adapter<RecordViewHolder>() {
    private val inflater by lazy { LayoutInflater.from(context) }
    private var memoList: List<Memo> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordViewHolder {
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