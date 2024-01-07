package com.teamwss.websoso.ui.novelDetail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.teamwss.websoso.databinding.ActivityNovelDetailBinding

class NovelDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNovelDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNovelDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRoundCornerToThumbnail()
        setupFragment()
    }

    private fun setupRoundCornerToThumbnail() {
        binding.ivNovelDetailThumbnail.clipToOutline = true
    }

    private fun setupFragment() {
        val fragmentList = listOf(NovelMemoFragment(), NovelInfoFragment())
        val viewPagerAdapter = NovelDetailViewPagerAdapter(this)
        viewPagerAdapter.fragments.addAll(fragmentList)

        binding.vpNovelDetail.adapter = viewPagerAdapter
    }
}