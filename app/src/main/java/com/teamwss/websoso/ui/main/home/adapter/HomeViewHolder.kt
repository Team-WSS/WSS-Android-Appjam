package com.teamwss.websoso.ui.main.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teamwss.websoso.data.model.SosoPickNovelEntity
import com.teamwss.websoso.databinding.ItemHomeBinding
import kotlin.properties.Delegates

class HomeViewHolder(
    private val binding: ItemHomeBinding,
    private val onClick: (Long) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    private var novelId by Delegates.notNull<Long>()

    init {
        binding.root.setOnClickListener {
            if (adapterPosition != RecyclerView.NO_POSITION) {
                onClick(novelId)
            }
        }
    }

    fun onBind(sosoPickNovel: SosoPickNovelEntity) {
        novelId = sosoPickNovel.novelId
        binding.sosoPickNovel = sosoPickNovel
    }

    companion object {
        private const val REGISTERED_COUNT_FORMAT = "%d명이 등록"
        fun newInstance(parent: ViewGroup, onClick: (Long) -> Unit): HomeViewHolder {
            val binding =
                ItemHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return HomeViewHolder(binding, onClick)
        }
    }
}