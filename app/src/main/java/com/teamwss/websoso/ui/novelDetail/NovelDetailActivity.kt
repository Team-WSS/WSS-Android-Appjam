package com.teamwss.websoso.ui.novelDetail

import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.PopupWindow
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.teamwss.websoso.R
import com.teamwss.websoso.databinding.ActivityNovelDetailBinding
import com.teamwss.websoso.ui.memoWrite.MemoWriteActivity
import com.teamwss.websoso.ui.novelDetail.adapter.NovelDetailViewPagerAdapter

class NovelDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNovelDetailBinding
    private val novelDetailAdapter: NovelDetailViewPagerAdapter by lazy {
        NovelDetailViewPagerAdapter(
            this
        )
    }
    private var isUserInteraction = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNovelDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setTranslucentOnStatusBar()
        setupFragment()
        viewPagerPageChangeCallback()
        setItemVisibilityOnToolBar()
        clickAddMemoBtn()
        clickPopupBtn()
    }

    private val Int.intDp: Int get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()

    private fun setTranslucentOnStatusBar() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
    }

    override fun onUserInteraction() {
        super.onUserInteraction()
        isUserInteraction = true
    }

    private fun setupFragment() {
        val tabTitleList = listOf("메모", "정보")
        binding.vpNovelDetail.adapter = NovelDetailViewPagerAdapter(this)

        TabLayoutMediator(binding.tlNovelDetailMemoInfo, binding.vpNovelDetail) { tab, position ->
            tab.text = tabTitleList[position]
        }.attach()

        binding.vpNovelDetail.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
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

    private fun viewPagerPageChangeCallback() {
        binding.vpNovelDetail.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                reloadFragmentView()
            }
        })
    }

    private fun reloadFragmentView() {
        novelDetailAdapter.fragments.forEach { fragment ->
            fragment.view?.post { fragment.view?.requestLayout() }
        }
    }

    private fun setItemVisibilityOnToolBar() {
        binding.ablNovelDetail.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
            val totalScrollRange = appBarLayout.totalScrollRange
            val percentage = (totalScrollRange.toFloat() + verticalOffset) / totalScrollRange
            updateToolbarAppearance(percentage <= TOOLBAR_COLLAPSE_THRESHOLD)
        }
    }

    private fun updateToolbarAppearance(isCollapsed: Boolean) {
        with(binding) {
            val color = if (isCollapsed) R.color.white else R.color.transparent
            tbNovelDetail.setBackgroundColor(
                ContextCompat.getColor(
                    this@NovelDetailActivity,
                    color
                )
            )

            tvNovelDetailTitleOnToolBar.visibility = if (isCollapsed) View.VISIBLE else View.GONE
            ivNovelDetailPopupMenuBtn.visibility = if (isCollapsed) View.GONE else View.VISIBLE
        }
    }

    private fun clickAddMemoBtn() {
        binding.ivNovelDetailAddMemoBtn.setOnClickListener {
            val intent = Intent(this, MemoWriteActivity::class.java)
            startActivity(intent)
        }
    }

    private fun clickPopupBtn() {
        binding.ivNovelDetailPopupMenuBtn.setOnClickListener {
            showNovelDetailPopup()
        }
    }

    private fun showNovelDetailPopup() {
        val spinnerItems =
            listOf(R.string.popup_novel_info_delete, R.string.popup_novel_info_edit_novel)
        binding.ivNovelDetailPopupMenuBtn.setOnClickListener {
            val listView = ListView(this).apply {
                adapter = ArrayAdapter(
                    this@NovelDetailActivity,
                    R.layout.item_custom_popup_drop_down,
                    spinnerItems
                )
                onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
                    when (position) {
                        0 -> showNovelDeleteDialog()
                        1 -> navigateToNovelEdit()
                    }
                }
            }
            val popupWindow =
                PopupWindow(listView, 196, WindowManager.LayoutParams.WRAP_CONTENT, true)
            popupWindow.setBackgroundDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.bg_gray50_radius_12dp
                )
            )
            popupWindow.isOutsideTouchable = true
            popupWindow.isFocusable = true
            val xOffset = (-6).intDp
            val yOffset = 4.intDp
            popupWindow.showAsDropDown(
                binding.ivNovelDetailPopupMenuBtn,
                xOffset,
                yOffset,
                Gravity.END
            )
        }
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
        const val TOOLBAR_COLLAPSE_THRESHOLD = 0.1
    }
}