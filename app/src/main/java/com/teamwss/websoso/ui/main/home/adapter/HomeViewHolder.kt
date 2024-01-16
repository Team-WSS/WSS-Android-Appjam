package com.teamwss.websoso.ui.main.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.teamwss.websoso.data.model.SosoPickNovelEntity
import com.teamwss.websoso.databinding.ItemHomeBinding

class HomeViewHolder(
    private val binding: ItemHomeBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(sosoPickNovel: SosoPickNovelEntity) {
        binding.sosoPickNovel = sosoPickNovel

        with(binding) {
            ivHomeItemCover.load(sosoPickNovel.novelImg)
        }
    }

    companion object {
        private const val REGISTERED_COUNT_FORMAT = "%d명이 등록"
        fun newInstance(parent: ViewGroup): HomeViewHolder {
            val binding =
                ItemHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return HomeViewHolder(binding)
        }
    }
}