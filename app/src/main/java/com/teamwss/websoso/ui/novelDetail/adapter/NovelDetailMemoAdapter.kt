package com.teamwss.websoso.ui.novelDetail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teamwss.websoso.data.remote.response.NovelMemo
import com.teamwss.websoso.databinding.ItemNovelMemoBinding
import com.teamwss.websoso.ui.novelDetail.NovelMemoViewHolder

class NovelDetailMemoAdapter : RecyclerView.Adapter<NovelMemoViewHolder>() {
    private var novelMemo: List<NovelMemo> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NovelMemoViewHolder {
        val binding =
            ItemNovelMemoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NovelMemoViewHolder(binding)
    }

    override fun getItemCount(): Int = novelMemo.size

    override fun onBindViewHolder(holder: NovelMemoViewHolder, position: Int) {
        holder.onBind(novelMemo[position])
    }

    fun updateData(newData: List<NovelMemo>) {
        novelMemo = newData
        notifyDataSetChanged()
    }
}