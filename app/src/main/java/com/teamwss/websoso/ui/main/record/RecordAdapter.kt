package com.teamwss.websoso.ui.main.record

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teamwss.websoso.ui.main.record.model.Memo

class RecordAdapter : RecyclerView.Adapter<RecordViewHolder>() {
    private var memoItems: MutableList<Memo> = mutableListOf()
    lateinit var onItemClickListener: ((Memo) -> Unit)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordViewHolder {
        return RecordViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RecordViewHolder, position: Int) {
        holder.onBind(memoItems[position])

        holder.itemView.setOnClickListener {
            onItemClickListener.invoke(memoItems[position])
        }
    }

    override fun getItemCount() = memoItems.size

    fun updateMemoItems(memos: List<Memo>) {
        this.memoItems = memos.toList().toMutableList()
        notifyDataSetChanged()
    }

    fun onMemoItemClickListener(listener: (Memo) -> Unit) {
        this.onItemClickListener = listener
    }
}