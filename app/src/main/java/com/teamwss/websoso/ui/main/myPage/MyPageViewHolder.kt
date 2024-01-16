package com.teamwss.websoso.ui.main.myPage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.teamwss.websoso.R
import com.teamwss.websoso.data.remote.response.MyPageUserInfoResponse
import com.teamwss.websoso.databinding.ItemAvatarBinding
import com.teamwss.websoso.ui.main.myPage.model.Avatar

class MyPageViewHolder(
    private val binding: ItemAvatarBinding,
    onClick: (id: Long) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.root.setOnClickListener {
            onClick((adapterPosition + 1L))
        }
    }

    fun onBind(avatar: Avatar) {
        binding.ivAvatar.load(avatar.avatarImg)
        {
            crossfade(true)
            placeholder(R.drawable.img_cover_test)
            transformations()
        }

//        if (avatar.avatarId == representativeAvatarId) {
//            binding.ivAvatar.setBackgroundResource(R.drawable.bg_stroke_primary100_20)
//        } else {
//            binding.ivAvatar.setBackgroundResource(0)
//        }
    }

    companion object {
        fun create(parent: ViewGroup, onClick: (Long) -> Unit): MyPageViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemAvatarBinding.inflate(inflater, parent, false)
            return MyPageViewHolder(binding, onClick)
        }
    }
}