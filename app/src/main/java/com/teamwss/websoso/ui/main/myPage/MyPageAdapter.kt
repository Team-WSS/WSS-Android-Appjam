package com.teamwss.websoso.ui.main.myPage

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teamwss.websoso.ui.main.myPage.model.Avatar

class MyPageAdapter(
    private val onClick: (Long) -> Unit,
    private val representativeAvatarId: Long
) : RecyclerView.Adapter<MyPageViewHolder>() {
    private val avatarItems: MutableList<Avatar> = mutableListOf()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyPageViewHolder {
        return MyPageViewHolder.newInstance(parent, onClick)
    }

    override fun onBindViewHolder(holder: MyPageViewHolder, position: Int) {
        holder.onBind(avatarItems[position], representativeAvatarId)
    }

    override fun getItemCount() = avatarItems.size

    fun updateAvatarList(newAvatarList: List<Avatar>) {
        avatarItems.clear()
        avatarItems.addAll(newAvatarList)
        notifyDataSetChanged()
    }
}