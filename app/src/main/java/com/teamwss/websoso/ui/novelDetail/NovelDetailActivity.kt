package com.teamwss.websoso.ui.novelDetail

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.teamwss.websoso.R
import com.teamwss.websoso.databinding.ActivityNovelDetailBinding
import com.teamwss.websoso.ui.novelDetail.adapter.NovelDetailViewPagerAdapter

class NovelDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNovelDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNovelDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setTranslucentOnStatusBar()
        setupFragment()
        clickPopupBtn()
    }

    private fun setTranslucentOnStatusBar() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
    }

    private fun setupFragment() {
        val tabTitleList = listOf("메모", "정보")
        binding.vpNovelDetail.adapter = NovelDetailViewPagerAdapter(this)

        TabLayoutMediator(binding.tlNovelDetailMemoInfo, binding.vpNovelDetail) { tab, position ->
            tab.text = tabTitleList[position]
        }.attach()
    }

    private fun clickPopupBtn() {
        binding.btnNovelDetailPopupMenu.setOnClickListener {
            showNovelDetailPopup(binding.btnNovelDetailPopupMenu)
        }
    }

    private fun showNovelDetailPopup(view: View) {
        val popup = PopupMenu(this, view, Gravity.END)
        popup.menuInflater.inflate(R.menu.menu_novel_info_popup, popup.menu)
        popup.show()
    }
}