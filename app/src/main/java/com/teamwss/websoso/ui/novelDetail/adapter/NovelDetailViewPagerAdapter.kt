package com.teamwss.websoso.ui.novelDetail.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.teamwss.websoso.ui.novelDetail.fragment.NovelInfoFragment
import com.teamwss.websoso.ui.novelDetail.fragment.NovelMemoFragment

class NovelDetailViewPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = FRAGMENT_COUNT

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> NovelMemoFragment()
            else -> NovelInfoFragment()
        }
    }

    companion object {
        private const val FRAGMENT_COUNT = 2
    }
}