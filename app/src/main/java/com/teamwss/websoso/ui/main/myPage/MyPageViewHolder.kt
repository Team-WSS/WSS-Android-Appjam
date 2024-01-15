package com.teamwss.websoso.ui.main.myPage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.teamwss.websoso.R
import com.teamwss.websoso.databinding.ItemAvatarBinding
import com.teamwss.websoso.ui.main.myPage.model.Avatar

class MyPageViewHolder(private val binding: ItemAvatarBinding, private val adapter: MyPageAdapter) :
    RecyclerView.ViewHolder(binding.root) {

    init {
        setupClickListener()
    }

    private fun setupClickListener(){
        binding.root.setOnClickListener {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val avatar = adapter.avatarItems[position]
                adapter.onItemClickListener.invoke(avatar)
            }
        }
    }

    fun onBind(avatar: Avatar) {
        binding.ivAvatar.load(avatar.avatarImg)
        {
            crossfade(true)
            placeholder(R.drawable.img_cover_test)
            transformations()
        }
    }

    companion object {
        fun create(parent: ViewGroup, adapter: MyPageAdapter): MyPageViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemAvatarBinding.inflate(inflater, parent, false)
            return MyPageViewHolder(binding, adapter)
        }
    }
}