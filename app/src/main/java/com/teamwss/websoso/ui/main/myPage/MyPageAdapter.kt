package com.teamwss.websoso.ui.main.myPage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teamwss.websoso.databinding.ItemAvatarBinding
import com.teamwss.websoso.ui.main.myPage.model.Avatar

abstract class MyPageAdapter(
    private val onClick: (id: Long) -> Unit,
) : RecyclerView.Adapter<MyPageViewHolder>() {
    private val avatarItems: MutableList<Avatar> = mutableListOf()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyPageViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemAvatarBinding.inflate(inflater, parent, false)
        return MyPageViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: MyPageViewHolder, position: Int) {
        holder.onBind(avatarItems[position])
    }

    override fun getItemCount() = avatarItems.size

    fun updateAvatarList(newAvatarList: List<Avatar>) {
        avatarItems.addAll(newAvatarList)
        notifyDataSetChanged()
    }
}