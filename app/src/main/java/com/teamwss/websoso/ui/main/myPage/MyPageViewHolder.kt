package com.teamwss.websoso.ui.main.myPage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.teamwss.websoso.databinding.ItemAvatarBinding
import com.teamwss.websoso.ui.main.myPage.model.Avatar

class MyPageViewHolder(private val binding: ItemAvatarBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun onBind(avatar: Avatar) {
        binding.ivAvatar.load(avatar.avatarImg)
    }

    companion object {
        fun create(parent: ViewGroup): MyPageViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemAvatarBinding.inflate(inflater, parent, false)
            return MyPageViewHolder(binding)
        }
    }
}