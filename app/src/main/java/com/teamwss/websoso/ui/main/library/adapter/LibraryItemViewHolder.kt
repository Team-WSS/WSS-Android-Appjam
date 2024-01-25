package com.teamwss.websoso.ui.main.library.adapter

import androidx.recyclerview.widget.RecyclerView
import com.teamwss.websoso.data.model.LibraryUserNovelEntity
import com.teamwss.websoso.databinding.ItemLibraryNovelBinding
import kotlin.properties.Delegates

class LibraryItemViewHolder(
    private val binding: ItemLibraryNovelBinding,
    private val userNovelClick: (userNovelId: Long) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    private var userNovelId by Delegates.notNull<Long>()

    init {
        binding.root.setOnClickListener {
            if (adapterPosition != RecyclerView.NO_POSITION) {
                userNovelClick(userNovelId)
            }
        }
    }

    fun bind(userNovel: LibraryUserNovelEntity) {
        binding.libraryNovel = userNovel
        userNovelId = userNovel.userNovelId
    }
}
