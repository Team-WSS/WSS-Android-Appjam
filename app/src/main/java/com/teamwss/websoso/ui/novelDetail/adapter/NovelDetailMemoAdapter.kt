package com.teamwss.websoso.ui.novelDetail.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teamwss.websoso.data.remote.response.NovelMemoResponse
import com.teamwss.websoso.databinding.ItemNovelMemoBinding
import com.teamwss.websoso.ui.novelDetail.NovelMemoViewHolder

class NovelDetailMemoAdapter(
    private val clickListener: (Long) -> Unit
) : RecyclerView.Adapter<NovelMemoViewHolder>() {
    private val novelMemos: MutableList<NovelMemoResponse> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NovelMemoViewHolder {
        val binding = ItemNovelMemoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NovelMemoViewHolder(binding, clickListener)
    }

    override fun onBindViewHolder(holder: NovelMemoViewHolder, position: Int) {
        holder.onBind(novelMemos[position])
    }

    override fun getItemCount(): Int = novelMemos.size

    fun updateUserNovelMemo(newData: List<NovelMemoResponse>) {
        novelMemos.clear()
        novelMemos.addAll(newData)
        notifyDataSetChanged()
    }
}