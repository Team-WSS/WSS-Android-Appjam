package com.teamwss.websoso.ui.main.myPage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teamwss.websoso.databinding.ItemAvatarBinding
import com.teamwss.websoso.ui.main.myPage.model.Avatar

class MyPageAdapter : RecyclerView.Adapter<MyPageViewHolder>() {
    var avatarItems: List<Avatar> = emptyList()
    var onItemClickListener: ((Avatar) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyPageViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemAvatarBinding.inflate(inflater, parent, false)
        return MyPageViewHolder(binding, adapter = MyPageAdapter())
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