package com.teamwss.websoso.ui.main.myPage

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teamwss.websoso.ui.main.myPage.model.Avatar

class MyPageAdapter : RecyclerView.Adapter<MyPageViewHolder>() {
    internal var avatarItems: List<Avatar> = emptyList()
    internal var onItemClickListener: ((Avatar) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyPageViewHolder {
        return MyPageViewHolder.create(parent, adapter = MyPageAdapter())
    }

    override fun onBindViewHolder(holder: MyPageViewHolder, position: Int) {
        holder.onBind(avatarItems[position])

        holder.itemView.setOnClickListener {
            onItemClickListener?.invoke(avatarItems[position])
        }
    }

    override fun getItemCount() = avatarItems.size

    fun setAvatarList(avatarList: List<Avatar>) {
        this.avatarItems = avatarList.toList()
        notifyDataSetChanged()
    }

    fun onAvatarItemClickListener(listener: (Avatar) -> Unit) {
        this.onItemClickListener = listener
    }
}