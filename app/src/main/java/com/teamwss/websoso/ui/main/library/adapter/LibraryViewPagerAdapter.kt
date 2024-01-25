package com.teamwss.websoso.ui.main.library.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teamwss.websoso.data.model.LibraryUserNovelEntity
import com.teamwss.websoso.databinding.ItemLibraryViewPagerBinding

class LibraryViewPagerAdapter(
    private val userNovelClick: (userNovelId: Long) -> Unit,
    private val onPostClick: () -> Unit
) :
    RecyclerView.Adapter<LibraryViewPagerViewHolder>() {

    private var userNovels :List<LibraryUserNovelEntity> = libraryUserNovelsInitData

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibraryViewPagerViewHolder {
        val binding =
            ItemLibraryViewPagerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LibraryViewPagerViewHolder(binding, userNovelClick, onPostClick)
    }

    override fun onBindViewHolder(holder: LibraryViewPagerViewHolder, position: Int) {
        holder.bind(userNovels)
    }

    fun fetchUserNovels(userNovels: List<LibraryUserNovelEntity>) {
        this.userNovels = userNovels
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = 5

    companion object{
        private val libraryUserNovelsInitData = listOf(
            LibraryUserNovelEntity(
                userNovelId = 1,
                userNovelTitle = "",
                userNovelCover = "",
                userNovelAuthor = "",
                userNovelRating = 0f
            ),
            LibraryUserNovelEntity(
                userNovelId = 1,
                userNovelTitle = "",
                userNovelCover = "",
                userNovelAuthor = "",
                userNovelRating = 0f
            ),
            LibraryUserNovelEntity(
                userNovelId = 1,
                userNovelTitle = "",
                userNovelCover = "",
                userNovelAuthor = "",
                userNovelRating = 0f
            ),
            LibraryUserNovelEntity(
                userNovelId = 1,
                userNovelTitle = "",
                userNovelCover = "",
                userNovelAuthor = "",
                userNovelRating = 0f
            ),
            LibraryUserNovelEntity(
                userNovelId = 1,
                userNovelTitle = "",
                userNovelCover = "",
                userNovelAuthor = "",
                userNovelRating = 0f
            ),
        )
    }
}