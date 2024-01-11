package com.teamwss.websoso.ui.novelDetail

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.teamwss.websoso.R
import com.teamwss.websoso.databinding.ActivityNovelDetailBinding
import com.teamwss.websoso.ui.memoWrite.MemoWriteActivity
import com.teamwss.websoso.ui.novelDetail.adapter.NovelDetailViewPagerAdapter

class NovelDetailActivity : AppCompatActivity(), PopupMenu.OnMenuItemClickListener {
    private lateinit var binding: ActivityNovelDetailBinding
    private val novelDetailAdapter: NovelDetailViewPagerAdapter by lazy {
        NovelDetailViewPagerAdapter(
            this
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNovelDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setTitleVisibilityOnToolBar()
        setTranslucentOnStatusBar()
        setupFragment()
        clickAddMemoBtn()
        clickPopupBtn()

        binding.vpNovelDetail.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                novelDetailAdapter.fragments.forEach { fragment ->
                    fragment.view?.post { fragment.view?.requestLayout() }
                }
            }
        })
    }

    private fun setTitleVisibilityOnToolBar() {
        with(binding) {
            ablNovelDetail.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
                tvNovelDetailTitleOnToolBar.visibility = View.VISIBLE
                ivNovelDetailPopupMenuBtn.visibility = View.GONE
            }
        }
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

        binding.vpNovelDetail.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                if (position == INDEX_OF_FRAGMENT_NOVEL_INFO) {
                    binding.ivNovelDetailAddMemoBtn.visibility = View.GONE
                } else {
                    binding.ivNovelDetailAddMemoBtn.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun clickAddMemoBtn() {
        binding.ivNovelDetailAddMemoBtn.setOnClickListener {
            val intent = Intent(this, MemoWriteActivity::class.java)
            startActivity(intent)
        }
    }

    private fun clickPopupBtn() {
        binding.ivNovelDetailPopupMenuBtn.setOnClickListener {
            showNovelDetailPopup(binding.ivNovelDetailPopupMenuBtn)
        }
    }

    private fun showNovelDetailPopup(view: View) {
        val popup = PopupMenu(this, view, Gravity.END)
        popup.menuInflater.inflate(R.menu.menu_novel_info_popup, popup.menu)
        popup.setOnMenuItemClickListener(this)
        popup.show()
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.btnNovelInfoPopupDeleteNovel ->
                showNovelDeleteDialog()

            R.id.btnNovelInfoPopupEditNovel ->
                navigateToNovelEdit()
        }
        return false
    }

    private fun showNovelDeleteDialog() {
        val dialog = DialogNovelDelete(clickNovelDelete = {
            // 서재 프레그먼트로 이동해야 함
            finish()
        })
        dialog.show((supportFragmentManager), "hi")
    }

    private fun navigateToNovelEdit() {
        // 이동해
    }

    companion object {
        const val INDEX_OF_FRAGMENT_NOVEL_INFO = 1

    }
}