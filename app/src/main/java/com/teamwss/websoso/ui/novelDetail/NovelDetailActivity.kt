package com.teamwss.websoso.ui.novelDetail

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.PopupMenu
import android.widget.PopupWindow
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.teamwss.websoso.R
import com.teamwss.websoso.databinding.ActivityNovelDetailBinding
import com.teamwss.websoso.ui.memoWrite.MemoWriteActivity
import com.teamwss.websoso.ui.novelDetail.adapter.CustomPopupSpinnerAdapter
import com.teamwss.websoso.ui.novelDetail.adapter.NovelDetailViewPagerAdapter

class NovelDetailActivity : AppCompatActivity(), PopupMenu.OnMenuItemClickListener {
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
//        clickPopupBtn()
        val spinnerItems = listOf("작품을 서재에서 삭제", "작품 정보 수정")
        binding.ivNovelDetailPopupMenuBtn.setOnClickListener {
            // 리스트 뷰를 준비합니다.
            val listView = ListView(this).apply {
                adapter = ArrayAdapter(
                    this@NovelDetailActivity,
                    R.layout.item_custom_popup_drop_down, // 사용자 지정 드롭다운 항목 레이아웃
                    spinnerItems
                )
                // 항목 클릭 리스너를 설정합니다.
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
            popupWindow.showAsDropDown(
                binding.ivNovelDetailPopupMenuBtn,
                0,
                -binding.ivNovelDetailPopupMenuBtn.height
            )
        }

    }

    private fun setupSpinner() {
        val spinnerItems = listOf("작품을 서재에서 삭제", "작품 정보 수정")
        val adapter = CustomPopupSpinnerAdapter(
            this,
            R.layout.item_custom_popup_drop_down,
            spinnerItems
        )
        binding.spNovelDetail.adapter = adapter
        binding.spNovelDetail.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                // 사용자가 실제로 스피너를 클릭해서 아이템을 선택한 경우에만 이벤트 처리
                if (isUserInteraction) {
                    when (position) {
                        0 -> showNovelDeleteDialog()
                        1 -> navigateToNovelEdit()
                        // 기타 케이스...
                    }
                }
                isUserInteraction = false
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // 아무것도 선택되지 않았을 때 처리
            }
        }
    }


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
        const val TOOLBAR_COLLAPSE_THRESHOLD = 0.1
    }
}