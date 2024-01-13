package com.teamwss.websoso.ui.novelDetail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teamwss.websoso.data.remote.response.NovelMemoResponse
import com.teamwss.websoso.databinding.ItemNovelMemoBinding
import com.teamwss.websoso.ui.novelDetail.NovelMemoViewHolder

class NovelDetailMemoAdapter : RecyclerView.Adapter<NovelMemoViewHolder>() {
    private var novelMemoResponse: List<NovelMemoResponse> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NovelMemoViewHolder {
        val binding =
            ItemNovelMemoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NovelMemoViewHolder(binding)
    }

    override fun getItemCount(): Int = novelMemoResponse.size

    override fun onBindViewHolder(holder: NovelMemoViewHolder, position: Int) {
        holder.onBind(novelMemoResponse[position])
    }

    fun updateData(newData: List<NovelMemoResponse>) {
        novelMemoResponse = newData
        notifyDataSetChanged()
    }
}