package com.teamwss.websoso.ui.novelDetail

import com.teamwss.websoso.ui.common.view.CustomSnackBar
import android.app.Activity
import android.content.Context
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
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.teamwss.websoso.R
import com.teamwss.websoso.databinding.ActivityNovelDetailBinding
import com.teamwss.websoso.ui.main.MainActivity
import com.teamwss.websoso.ui.memoWrite.MemoWriteActivity
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
    private var userNovelId: Long = 0
    private var boolean: Boolean = false
    private lateinit var userNovelTitle: String
    private lateinit var userNovelAuthor: String
    private lateinit var userNovelImage: String
    private var popupWindow: PopupWindow? = null

    private lateinit var postedMemoLauncher: ActivityResultLauncher<Intent>

    private lateinit var patchedNovelLauncher: ActivityResultLauncher<Intent>

    private val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            navigateToLibrary()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNovelDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.lifecycleOwner = this
        binding.novelDetailViewModel = novelDetailViewModel

        patchedNovelLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    binding.vpNovelDetail.post {
                        binding.vpNovelDetail.setCurrentItem(NOVEL_INFO_FRAGMENT_INDEX, true)
                    }
                }
            }

        this.onBackPressedDispatcher.addCallback(this, callback)

        getAndUpdateUserNovelId()
        setupUI()
        registerForPostMemoLauncher()
        observeUserNovelId()
        observeUserNovelInfoData()
        setupListener()

    }

    private fun getAndUpdateUserNovelId() {
        val errorUserId: Long = 0
        userNovelId = intent.getLongExtra("userNovelId", errorUserId)
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
        val tabTitleItems =
            listOf(getText(R.string.novel_detail_memo), getText(R.string.novel_detail_info))
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

    private fun registerForPostMemoLauncher() {
        postedMemoLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val isAvatarUnlocked =
                        result.data?.getBooleanExtra("isAvatarUnlocked", false) ?: false
                    val memoSavedDrawable =
                        ContextCompat.getDrawable(this, R.drawable.ic_alert_default)

                    if (isAvatarUnlocked) {
                        val avatarUnlockedDrawable =
                            ContextCompat.getDrawable(this, R.drawable.ic_avatar_unlocked)
                        CustomSnackBar.make(binding.root)
                            .setText("새 캐릭터가 열렸어요!")
                            .setIcon(
                                avatarUnlockedDrawable ?: ContextCompat.getDrawable(
                                    this,
                                    R.drawable.ic_avatar_unlocked
                                )!!
                            )
                            .show()
                    } else {
                        CustomSnackBar.make(binding.root)
                            .setText("메모를 저장했어요")
                            .setIcon(
                                memoSavedDrawable ?: ContextCompat.getDrawable(
                                    this,
                                    R.drawable.ic_alert_default
                                )!!
                            )
                            .show()
                    }
                }
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

            tvNovelDetailTitleOnToolBar.visibility =
                if (isCollapsed) View.VISIBLE else View.GONE
            ivNovelDetailPopupMenuBtn.visibility = if (isCollapsed) View.GONE else View.VISIBLE
        }
    }

    private fun observeUserNovelId() {
        novelDetailViewModel.userNovelId.observe(this) {
            userNovelId = novelDetailViewModel.userNovelId.value!!.toLong()
        }
    }

    private fun observeUserNovelInfoData() {
        novelDetailViewModel.userNovelMemoInfoResponse.observe(this) {
            userNovelAuthor =
                novelDetailViewModel.userNovelMemoInfoResponse.value!!.userNovelAuthor
            userNovelTitle =
                novelDetailViewModel.userNovelMemoInfoResponse.value!!.userNovelTitle
            userNovelImage = novelDetailViewModel.userNovelMemoInfoResponse.value!!.userNovelImg
        }
    }

    private fun setupListener() {
        onClickBackButton()
        onClickAddMemoButton()
        onClickPopupButton()
    }

    private fun onClickBackButton() {
        binding.ivNovelDetailNavigateBackBtn.setOnClickListener {
            navigateToLibrary()
        }
    }

    private fun navigateToLibrary() {
        val intent = MainActivity.newIntent(this, R.id.menu_library)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    private fun onClickAddMemoButton() {
        binding.ivNovelDetailAddMemoBtn.setOnClickListener {
            val intent = MemoWriteActivity.newIntentFromDetail(
                this,
                userNovelId,
                userNovelTitle,
                userNovelAuthor,
                userNovelImage
            )
            postedMemoLauncher.launch(intent)
        }
    }

    private fun onClickPopupButton() {
        binding.ivNovelDetailPopupMenuBtn.setOnClickListener {
            showNovelDetailPopup()
        }
    }

    private fun showNovelDetailPopup() {
        val spinnerItems = listOf("작품을 서재에서 삭제", "작품 정보 수정")
        val listView = createListView(spinnerItems)
        popupWindow = createPopupWindow(listView)

        val xOffset = POPUP_MARGIN_END.intDp
        val yOffset = POPUP_MARGIN_TOP.intDp
        popupWindow?.showAsDropDown(
            binding.ivNovelDetailPopupMenuBtn,
            xOffset,
            yOffset,
            Gravity.END
        )
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
        return PopupWindow(
            listView,
            POPUP_WIDTH.intDp,
            WindowManager.LayoutParams.WRAP_CONTENT,
            true
        ).apply {
            isTouchable = true
            isOutsideTouchable = true
            isFocusable = true
            setBackgroundDrawable(
                ContextCompat.getDrawable(
                    this@NovelDetailActivity,
                    R.drawable.bg_gray50_radius_12dp
                )
            )
        }
    }

    private fun handlePopupItemClick(position: Int) {
        when (position) {
            NOVEL_MEMO_FRAGMENT_INDEX -> showNovelDeleteDialog()
            NOVEL_INFO_FRAGMENT_INDEX -> navigateToNovelEdit()
        }
    }

    private fun showNovelDeleteDialog() {
        val dialog = DialogNovelDelete(clickNovelDelete = {
            finish()
        })
        dialog.show((supportFragmentManager), "DeleteNovelDialog")
    }

    private fun navigateToNovelEdit() {
        val intent = PostNovelActivity.newIntent(
            this,
            novelDetailViewModel.userNovelMemoInfoResponse.value?.novelId ?: 0
        )
        patchedNovelLauncher.launch(intent)
    }

    override fun onResume() {
        super.onResume()
        novelDetailViewModel.getUserNovelMemoInfo(userNovelId)
    }

    override fun onPause() {
        super.onPause()
        if (popupWindow?.isShowing == true) {
            popupWindow?.dismiss()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (popupWindow?.isShowing == true) {
            popupWindow?.dismiss()
        }
    }

    private val Int.intDp: Int get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()

    companion object {
        const val INDEX_OF_FRAGMENT_NOVEL_INFO = 1
        const val TOOLBAR_COLLAPSE_THRESHOLD = 0.7
        const val POPUP_WIDTH = 198
        const val POPUP_MARGIN_END = -6
        const val POPUP_MARGIN_TOP = 4
        const val NOVEL_MEMO_FRAGMENT_INDEX = 0
        const val NOVEL_INFO_FRAGMENT_INDEX = 1

        fun createIntent(context: Context, userNovelId: Long): Intent {
            return Intent(context, NovelDetailActivity::class.java).apply {
                putExtra("userNovelId", userNovelId)
            }
        }
    }
}