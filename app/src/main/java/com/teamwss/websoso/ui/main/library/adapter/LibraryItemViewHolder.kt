package com.teamwss.websoso.ui.main.library.adapter

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.teamwss.websoso.data.model.LibraryUserNovel
import com.teamwss.websoso.databinding.ItemLibraryNovelBinding
import kotlin.properties.Delegates


class LibraryItemViewHolder(
    private val binding: ItemLibraryNovelBinding
) : RecyclerView.ViewHolder(binding.root) {

    private var userNovelId by Delegates.notNull<Long>()

    init {
        binding.root.setOnClickListener {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
            }
        }
    }

    fun bind(userNovel: LibraryUserNovel) {
        binding.libraryNovel = userNovel
        binding.ivLibraryNovelCover.load(userNovel.userNovelCover)
        userNovelId = userNovel.userNovelId
    }
}
