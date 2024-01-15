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
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.teamwss.websoso.R
import com.teamwss.websoso.databinding.ActivityNovelDetailBinding
import com.teamwss.websoso.ui.memoPlain.MemoPlainActivity
import com.teamwss.websoso.ui.novelDetail.adapter.NovelDetailViewPagerAdapter
import com.teamwss.websoso.ui.novelDetail.fragment.DialogNovelDelete
import com.teamwss.websoso.ui.postNovel.PostNovelActivity

class NovelDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNovelDetailBinding
    private val novelDetailAdapter: NovelDetailViewPagerAdapter by lazy {
        NovelDetailViewPagerAdapter(
            this
        )
    }
    private val novelDetailViewModel: NovelDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNovelDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.lifecycleOwner = this
        binding.novelDetailViewModel = novelDetailViewModel

        setupUI()
        setupListener()

        var userNovelId: Long = 55
        novelDetailViewModel.getUserNovelId(userNovelId)
        novelDetailViewModel.getUserNovelMemoInfo(userNovelId)
    }

    private fun setupUI() {
        setTranslucentOnStatusBar()
        setupFragment()
        viewPagerRegisterPageCallback()
        setItemVisibilityOnToolBar()
    }

    private fun setTranslucentOnStatusBar() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
    }

    private fun setupFragment() {
        val tabTitleItems = listOf(getText(R.string.novel_detail_memo), getText(R.string.novel_detail_info))
        binding.vpNovelDetail.adapter = novelDetailAdapter

        TabLayoutMediator(binding.tlNovelDetailMemoInfo, binding.vpNovelDetail) { tab, position ->
            tab.text = tabTitleItems[position]
        }.attach()
    }

    private fun viewPagerRegisterPageCallback() {
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

    private fun setupListener() {
        onClickAddMemoButton()
        onClickPopupButton()
    }

    private fun onClickAddMemoButton() {
        binding.ivNovelDetailAddMemoBtn.setOnClickListener {
            val intent = Intent(this, MemoPlainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun onClickPopupButton() {
        binding.ivNovelDetailPopupMenuBtn.setOnClickListener {
            showNovelDetailPopup()
        }
    }

    private fun showNovelDetailPopup() {
        val spinnerItems = listOf("작품을 서재에서 삭제", "작품 수정")
        val listView = createListView(spinnerItems)
        val popupWindow = createPopupWindow(listView)

        val xOffset = POPUP_MARGIN_END.intDp
        val yOffset = POPUP_MARGIN_TOP.intDp
        popupWindow.showAsDropDown(binding.ivNovelDetailPopupMenuBtn, xOffset, yOffset, Gravity.END)
    }

    private fun createListView(items: List<String>): ListView {
        return ListView(this).apply {
            adapter = ArrayAdapter(
                this@NovelDetailActivity,
                R.layout.item_custom_popup_drop_down,
                items
            )
            onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
                handlePopupItemClick(position)
            }
        }
    }

    private fun createPopupWindow(listView: ListView): PopupWindow {
        return PopupWindow(listView, POPUP_WIDTH.intDp, WindowManager.LayoutParams.WRAP_CONTENT, true).apply {
            isTouchable = true
            isOutsideTouchable = true
            isFocusable = true
            setBackgroundDrawable(ContextCompat.getDrawable(this@NovelDetailActivity, R.drawable.bg_gray50_radius_12dp))
        }
    }

    private fun handlePopupItemClick(position: Int) {
        when (position) {
            0 -> showNovelDeleteDialog()
            1 -> navigateToNovelEdit()
        }
    }

    private fun showNovelDeleteDialog() {
        val dialog = DialogNovelDelete(clickNovelDelete = {
            // 서재 프레그먼트로 이동해야 함
            finish()
        })
        dialog.show((supportFragmentManager), "DeleteNovelDialog")
    }

    private fun navigateToNovelEdit() {
        val intent = PostNovelActivity.newIntent(this, 0)
        startActivity(intent)
        finish()
    }

    private val Int.intDp: Int get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()

    companion object {
        const val INDEX_OF_FRAGMENT_NOVEL_INFO = 1
        const val TOOLBAR_COLLAPSE_THRESHOLD = 0.1
        const val POPUP_WIDTH = 198
        const val POPUP_MARGIN_END = -6
        const val POPUP_MARGIN_TOP = 4
    }
}