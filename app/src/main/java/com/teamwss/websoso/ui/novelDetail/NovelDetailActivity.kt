package com.teamwss.websoso.ui.novelDetail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.teamwss.websoso.databinding.ActivityNovelDetailBinding
import com.teamwss.websoso.ui.novelDetail.adapter.NovelDetailViewPagerAdapter

class NovelDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNovelDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNovelDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupFragment()
    }

    private fun setupFragment() {
        val tabTitleList = listOf("메모", "정보")
        binding.vpNovelDetail.adapter = NovelDetailViewPagerAdapter(this)

        TabLayoutMediator(binding.tlNovelDetailMemoInfo, binding.vpNovelDetail) { tab, position ->
            tab.text = tabTitleList[position]
        }.attach()
    }
}