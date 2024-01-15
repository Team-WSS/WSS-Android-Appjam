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
import com.google.android.material.snackbar.Snackbar
import com.teamwss.websoso.R
import com.teamwss.websoso.data.remote.request.NovelPostRequest
import com.teamwss.websoso.databinding.ActivityPostNovelBinding
import com.teamwss.websoso.ui.postNovel.postNovelDialog.DatePickerDialog
import com.teamwss.websoso.ui.postNovel.postNovelDialog.ExitPopupDialog
import com.teamwss.websoso.ui.postNovel.postNovelDialog.PostSuccessDialog
import com.teamwss.websoso.ui.postNovel.postNovelModel.ReadStatus
import com.teamwss.websoso.ui.postNovel.postNovelViewModel.PostNovelViewModel
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
        setupSaveButton()

        setupExitPopupDialog()
        setupDatePickerDialog()

        initUserNovelInfo()
        setupReadStatusUI()
        setupRatingBar()
        setupUrlButton()
        setupServerError()
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

    private fun setupSaveButton() {
        binding.fbPostButton.setOnClickListener {
            saveNovelInfo()
            postNovelViewModel.isServerError.observe(this) {
                if (!it) {
                    showPostSuccessDialog()
                }
            }
        }
    }

    private fun checkIsDateNull(): NovelPostRequest {
        return if (!binding.scPostDateSwitch.isChecked) {
            (NovelPostRequest(
                binding.rbPostRating.rating,
                postNovelViewModel.readStatus.value ?: ReadStatus.FINISH.toString(),
                null,
                null
            ))
        } else {
            (NovelPostRequest(
                binding.rbPostRating.rating,
                postNovelViewModel.readStatus.value ?: ReadStatus.FINISH.toString(),
                binding.tvPostReadDateStart.text.toString(),
                binding.tvPostReadDateEnd.text.toString()
            ))
        }
    }

    private fun saveNovelInfo() {
        val id = postNovelViewModel.novelInfo.value?.id ?: 0
        val request = checkIsDateNull()

        if (postNovelViewModel.isNovelAlreadyPosted.value == false) {
            postNovelViewModel.postNovelInfo(id, request)
        } else {
            postNovelViewModel.patchNovelInfo(id, request)
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

    private fun showPostSuccessDialog() {
        postNovelViewModel.updateIsDialogShown(true)

        val dialogFragment = PostSuccessDialog()
        dialogFragment.show(supportFragmentManager, "PostSuccessDialog")
    }

    private fun initUserNovelInfo() {
        val testNovelInfoId: Long = 1
        postNovelViewModel.fetchUserNovelInfo(testNovelInfoId)
        postNovelViewModel.isNovelAlreadyPosted.observe(this@PostNovelActivity) {
            if (!it) {
                postNovelViewModel.fetchDefaultNovelInfo(testNovelInfoId)
            }
        }
    }

    private fun setupReadStatusUI() {
        postNovelViewModel.readStatus.observe(this@PostNovelActivity) {
            handleReadStatus(it)
        }
    }

    private fun handleReadStatus(readStatus: String) {
        when (readStatus) {
            ReadStatus.FINISH.toString() -> updateUIForStatusFinish()
            ReadStatus.READING.toString() -> updateUIForStatusReading()
            ReadStatus.DROP.toString() -> updateUIForStatusDrop()
            ReadStatus.WISH.toString() -> updateUIForStatusWish()
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

    private fun setupRatingBar() {
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

    private fun setupServerError() {
        postNovelViewModel.isServerError.observe(this) {
            if (it) {
                Snackbar.make(
                    binding.root,
                    getString(R.string.post_server_error),
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
    }
}