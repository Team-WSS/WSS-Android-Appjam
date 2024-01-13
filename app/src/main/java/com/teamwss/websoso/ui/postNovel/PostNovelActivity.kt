package com.teamwss.websoso.ui.postNovel

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.teamwss.websoso.R
import com.teamwss.websoso.databinding.ActivityPostNovelBinding
import com.teamwss.websoso.ui.postNovel.postNovelDialog.DatePickerDialog
import com.teamwss.websoso.ui.postNovel.postNovelDialog.ExitPopupDialog
import com.teamwss.websoso.ui.postNovel.postNovelDialog.PostSuccessDialog
import com.teamwss.websoso.ui.postNovel.postNovelViewModel.PostNovelViewModel
import java.time.LocalDate
import kotlin.math.pow

class PostNovelActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPostNovelBinding
    private val postNovelViewModel by viewModels<PostNovelViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostNovelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.lifecycleOwner = this
        binding.postNovelViewModel = postNovelViewModel

        postNovelViewModel.updateIsDialogShown(false)

        setTranslucentOnStatusBar()
        setupAppBar()
        setupDateToggle()

        setupExitPopupDialog()
        setupDatePickerDialog()
        setupPostSuccessDialog()

        initUserNovelInfo()
        setupReadStatusUI()
        observeRatingBar()
        setupUrlButton()
    }

    private fun setTranslucentOnStatusBar() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
    }

    private fun setupAppBar() {
        binding.svPost.viewTreeObserver.addOnScrollChangedListener {
            val scrollY = binding.svPost.scrollY
            val maxHeight = binding.ivPostCoverBackground.height - binding.viewPostAppBar.height

            val scrollRatio = ((scrollY.toFloat() + 15) / maxHeight).coerceAtMost(1f).pow(3 / 2)
            val colorAlpha = (scrollRatio * 255).toInt()

            binding.viewPostAppBar.setBackgroundColor(getColor(R.color.white).changeAlpha(colorAlpha))
            binding.tvPostTitle.setTextColor(getColor(R.color.black).changeAlpha(colorAlpha))

            if (scrollY == 0) {
                binding.viewPostAppBar.visibility = View.INVISIBLE
            } else {
                binding.viewPostAppBar.visibility = View.VISIBLE
            }
        }
    }

    private fun Int.changeAlpha(newAlpha: Int): Int {
        return Color.argb(newAlpha, Color.red(this), Color.green(this), Color.blue(this))
    }

    private fun setupDateToggle() {
        binding.scPostDateSwitch.setOnCheckedChangeListener { _, isChecked ->
            binding.llPostReadDate.visibility = if (isChecked) View.VISIBLE else View.GONE
        }
    }

    private fun setupExitPopupDialog() {
        binding.ivPostExitPopup.setOnClickListener {
            postNovelViewModel.updateIsDialogShown(true)

            val dialogFragment = ExitPopupDialog()
            dialogFragment.show(supportFragmentManager, "ExitPopupDialog")
        }
    }

    private fun setupDatePickerDialog() {
        binding.llPostReadDate.setOnClickListener {
            postNovelViewModel.updateIsDialogShown(true)

            val dialogFragment = DatePickerDialog()
            dialogFragment.show(supportFragmentManager, "DatePickerDialog")
        }
    }

    private fun setupPostSuccessDialog() {
        binding.llPostButton.setOnClickListener {
            postNovelViewModel.updateIsDialogShown(true)

            val dialogFragment = PostSuccessDialog()
            dialogFragment.show(supportFragmentManager, "PostSuccessDialog")
        }
    }

    private fun initUserNovelInfo() {
        postNovelViewModel.getUserNovelInfo(1)
        observeDummyData()
    }

    private fun observeDummyData() {
        val readStatus = postNovelViewModel.editNovelInfo.value?.userNovelReadStatus ?: "FINISH"
        postNovelViewModel.updateReadStatus(readStatus)

        val readStartDate =
            postNovelViewModel.editNovelInfo.value?.readStartDate ?: LocalDate.now().toString()
        val readEndDate =
            postNovelViewModel.editNovelInfo.value?.readEndDate ?: LocalDate.now().toString()
        postNovelViewModel.updateReadDate(readStartDate, readEndDate)

        val novelRating = postNovelViewModel.editNovelInfo.value?.userNovelRating ?: 0f
        postNovelViewModel.updateRating(novelRating)

        val platforms = postNovelViewModel.editNovelInfo.value?.platformList ?: listOf()
        postNovelViewModel.setPlatforms(platforms)
    }

    private fun setupReadStatusUI() {
        postNovelViewModel.readStatus.observe(this@PostNovelActivity) {
            handleReadStatus(it)
        }
    }

    private fun handleReadStatus(readStatus: String) {
        when (readStatus) {
            PostNovelViewModel.ReadStatus.FINISH.status -> updateUIForStatusFinish()
            PostNovelViewModel.ReadStatus.READING.status -> updateUIForStatusReading()
            PostNovelViewModel.ReadStatus.DROP.status -> updateUIForStatusDrop()
            PostNovelViewModel.ReadStatus.WISH.status -> updateUIForStatusWish()
        }
    }

    private fun updateUIForStatusFinish() {
        postNovelViewModel.updateIsDateVisible(isStartDateVisible = true, isEndDateVisible = true)
        postNovelViewModel.updateIsNumberPickerStartSelected(true)
        binding.tvPostReadDateTitle.text = getString(R.string.post_read_status_finish)
    }

    private fun updateUIForStatusReading() {
        postNovelViewModel.updateIsDateVisible(isStartDateVisible = true, isEndDateVisible = false)
        postNovelViewModel.updateIsNumberPickerStartSelected(true)
        binding.tvPostReadDateTitle.text = getString(R.string.post_read_status_reading)
    }

    private fun updateUIForStatusDrop() {
        postNovelViewModel.updateIsDateVisible(isStartDateVisible = false, isEndDateVisible = true)
        postNovelViewModel.updateIsNumberPickerStartSelected(false)
        binding.tvPostReadDateTitle.text = getString(R.string.post_read_status_drop)
    }

    private fun updateUIForStatusWish() {
        binding.clPostReadDate.visibility = View.GONE
    }

    private fun observeRatingBar() {
        binding.rbPostRating.setOnRatingBarChangeListener { _, rating, _ ->
            postNovelViewModel.updateRating(rating)
        }
    }

    private fun setupUrlButton() {
        postNovelViewModel.platforms.observe(this) {
            binding.llPostNovelLinkNaver.setOnClickListener { openUrl(postNovelViewModel.naverUrl.value.toString()) }
            binding.llPostNovelLinkKakao.setOnClickListener { openUrl(postNovelViewModel.kakaoUrl.value.toString()) }
        }
    }

    private fun openUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, PostNovelActivity::class.java)
        }
    }
}