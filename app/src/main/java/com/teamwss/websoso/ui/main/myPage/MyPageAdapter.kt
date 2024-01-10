package com.teamwss.websoso.ui.main.myPage

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teamwss.websoso.ui.main.myPage.model.Avatar

class MyPageAdapter : RecyclerView.Adapter<MyPageViewHolder>() {
    private var avatarItems: List<Avatar> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyPageViewHolder {
        return MyPageViewHolder.create(parent)
    }

    override fun getItemCount() = avatarItems.size

    fun setAvatarList(avatarList: List<Avatar>) {
        this.avatarItems = avatarList.toList()
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: MyPageViewHolder, position: Int) {
        holder.onBind(avatarItems[position])
    }

}