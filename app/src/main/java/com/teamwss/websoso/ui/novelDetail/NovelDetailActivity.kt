package com.teamwss.websoso.ui.novelDetail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.teamwss.websoso.databinding.ActivityNovelDetailBinding

class NovelDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNovelDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNovelDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupFragment()
    }
    private fun setupFragment() {
        val fragmentList = listOf(NovelMemoFragment(), NovelInfoFragment())
        val fragmentTitleList = listOf("메모", "정보")
        val viewPagerAdapter = NovelDetailViewPagerAdapter(this)
        viewPagerAdapter.fragments.addAll(fragmentList)

        binding.vpNovelDetail.adapter = viewPagerAdapter

        TabLayoutMediator(binding.tlNovelDetailMemoInfo, binding.vpNovelDetail) { tab, position ->
            tab.text = fragmentTitleList[position]
        }.attach()
    }
}