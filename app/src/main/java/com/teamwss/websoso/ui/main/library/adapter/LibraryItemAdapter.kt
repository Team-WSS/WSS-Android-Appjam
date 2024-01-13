package com.teamwss.websoso.ui.main.library.adapter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.teamwss.websoso.data.model.LibraryUserNovel
import com.teamwss.websoso.databinding.ItemLibraryNovelBinding

class LibraryItemAdapter :
    ListAdapter<LibraryUserNovel, LibraryItemViewHolder>(FollowerDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibraryItemViewHolder {
        val binding =
            ItemLibraryNovelBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LibraryItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LibraryItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val FollowerDiffCallback = object :
            DiffUtil.ItemCallback<LibraryUserNovel>() {
            override fun areItemsTheSame(
                oldItem: LibraryUserNovel,
                newItem: LibraryUserNovel
            ): Boolean {
                return oldItem.userNovelId == newItem.userNovelId
            }

            override fun areContentsTheSame(
                oldItem: LibraryUserNovel,
                newItem: LibraryUserNovel
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}