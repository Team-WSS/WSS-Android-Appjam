package com.teamwss.websoso.ui.novelDetail.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.teamwss.websoso.ui.novelDetail.fragment.NovelInfoFragment
import com.teamwss.websoso.ui.novelDetail.fragment.NovelMemoFragment

class NovelDetailViewPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {
    val fragments = listOf(NovelMemoFragment(), NovelInfoFragment())

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> fragments.first()
            else -> fragments.last()
        }
    }
}