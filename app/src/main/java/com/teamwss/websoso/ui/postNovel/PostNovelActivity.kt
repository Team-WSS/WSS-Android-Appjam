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
import com.teamwss.websoso.ui.common.model.ReadStatus
import com.teamwss.websoso.ui.main.MainActivity
import com.teamwss.websoso.ui.novelDetail.NovelDetailActivity
import com.teamwss.websoso.ui.postNovel.dialog.DatePickerDialog
import com.teamwss.websoso.ui.postNovel.dialog.PostSuccessDialog
import kotlin.math.pow

class PostNovelActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPostNovelBinding
    private val postNovelViewModel by viewModels<PostNovelViewModel>()

    private var datePickerDialog: DatePickerDialog? = null
    private var postSuccessDialog: PostSuccessDialog? = null

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
        setupSaveStatusObserver()
        setupIsServerError()

        setupExitButton()
        setupDatePickerDialog()

        initUserNovelInfo()
        setupReadStatusUI()
        setupRatingBar()
        setupUrlButton()
        setupSaveButton()
    }

    private fun setTranslucentOnStatusBar() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
    }

    private fun setupAppBar() {
        val scrollThreshold = 10
        val alphaThreshold = 5
        val maxScrollRatio = 1f
        val alphaMultiplier = 255
        val powerFactor = 3 / 2

        binding.svPost.viewTreeObserver.addOnScrollChangedListener {
            val scrollY = binding.svPost.scrollY
            val maxHeight = binding.ivPostCoverBackground.height - binding.viewPostAppBar.height

            val scrollRatio =
                ((scrollY.toFloat() + alphaThreshold) / maxHeight).coerceAtMost(maxScrollRatio)
                    .pow(powerFactor)
            val colorAlpha = (scrollRatio * alphaMultiplier).toInt()

            binding.viewPostAppBar.setBackgroundColor(getColor(R.color.white).changeAlpha(colorAlpha))
            binding.tvPostTitle.setTextColor(getColor(R.color.black).changeAlpha(colorAlpha))

            if (scrollY < scrollThreshold) {
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
        }
    }

    private fun setupSaveStatusObserver() {
        postNovelViewModel.isSaveError.observe(this@PostNovelActivity) {
            val isSaveError = postNovelViewModel.isSaveError.value ?: true
            val isNovelAlreadyPosted: Boolean =
                postNovelViewModel.isNovelAlreadyPosted.value ?: true
            val isTitleNotEmpty = !binding.tvPostNovelTitle.text.isNullOrEmpty()

            when {
                !isSaveError && !isNovelAlreadyPosted && isTitleNotEmpty -> {
                    showPostSuccessDialog()
                }

                !isSaveError && isNovelAlreadyPosted && isTitleNotEmpty -> {
                    navigateToNovelDetailFromSuccessDialog()
                    finish()
                }
            }
        }
    }

    private fun navigateToNovelDetailFromSuccessDialog() {
        navigateToHome()
        navigateToNovelDetail()
        finishAffinity()
    }

    private fun navigateToHomeFromSuccessDialog() {
        navigateToHome()
        finishAffinity()
    }

    private fun navigateToNovelDetail(){
        val newUserNovelId = postNovelViewModel.newUserNovelId.value ?: 0
        val intent = NovelDetailActivity.createIntent(this, newUserNovelId)
        startActivity(intent)
    }

    private fun navigateToHome() {
        val intent = MainActivity.newIntent(this)
        startActivity(intent)
    }

    private fun checkIsDateNull(): NovelPostRequest {
        val rating = binding.rbPostRating.rating
        val readStatus = postNovelViewModel.readStatus.value ?: ReadStatus.FINISH.toString()

        if (!binding.scPostDateSwitch.isChecked || readStatus == ReadStatus.WISH.toString()) {
            return NovelPostRequest(rating, readStatus, null, null)
        }

        val startDate = if (readStatus != ReadStatus.DROP.toString())
            binding.tvPostReadDateStart.text.toString() else null
        val endDate = if (readStatus != ReadStatus.READING.toString())
            binding.tvPostReadDateEnd.text.toString() else null
        return NovelPostRequest(rating, readStatus, startDate, endDate)
    }

    private fun saveNovelInfo() {
        val errorId: Long = 0
        val id = postNovelViewModel.novelInfo.value?.id ?: errorId
        val request = checkIsDateNull()

        if (postNovelViewModel.isNovelAlreadyPosted.value == false) {
            postNovelViewModel.saveNovelInfo(id, request)
        } else {
            postNovelViewModel.saveUserNovelInfo(id, request)
        }
    }

    private fun setupExitButton() {
        binding.ivPostExitButton.setOnClickListener {
            finish()
        }
    }

    private fun setupDatePickerDialog() {
        binding.llPostReadDate.setOnClickListener {
            if (datePickerDialog == null || !datePickerDialog!!.isAdded) {
                postNovelViewModel.updateIsDialogShown(true)

                datePickerDialog = DatePickerDialog()
                datePickerDialog!!.show(supportFragmentManager, "DatePickerDialog")
            }
        }
    }

    private fun showPostSuccessDialog() {
        if (postSuccessDialog == null || !postSuccessDialog!!.isAdded) {
            postNovelViewModel.updateIsDialogShown(true)

            postSuccessDialog =
                PostSuccessDialog(
                    ::navigateToNovelDetailFromSuccessDialog,
                    ::navigateToHomeFromSuccessDialog
                )
            postSuccessDialog!!.show(supportFragmentManager, "PostSuccessDialog")
        }
    }

    private fun initUserNovelInfo() {
        val novelId = intent.getLongExtra(NOVEL_ID, 0)
        postNovelViewModel.fetchUserNovelInfo(novelId)
        postNovelViewModel.isNovelAlreadyPosted.observe(this@PostNovelActivity) {
            if (!it && postNovelViewModel.novelInfo.value == null) {
                postNovelViewModel.fetchDefaultNovelInfo(novelId)
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
        postNovelViewModel.naverUrl.observe(this) { naverUrl ->
            bindUrlClickListener(binding.llPostNovelLinkNaver, naverUrl)
        }

        postNovelViewModel.kakaoUrl.observe(this) { kakaoUrl ->
            bindUrlClickListener(binding.llPostNovelLinkKakao, kakaoUrl)
        }
    }

    private fun bindUrlClickListener(view: View, url: String) {
        view.setOnClickListener { openUrl(url) }
        view.visibility = if (url.isNotEmpty()) View.VISIBLE else View.GONE
    }

    private fun openUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    private fun setupIsServerError() {
        val showErrorSnackbar: (Boolean) -> Unit = {
            if (it) {
                Snackbar.make(
                    binding.root,
                    getString(R.string.post_server_error),
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
        postNovelViewModel.isFetchError.observe(this, showErrorSnackbar)
        postNovelViewModel.isSaveError.observe(this, showErrorSnackbar)
    }

    companion object {
        const val NOVEL_ID = "NOVEL_ID"
        fun newIntent(context: Context, novelId: Long): Intent {
            return Intent(context, PostNovelActivity::class.java).apply {
                putExtra(NOVEL_ID, novelId)
            }
        }
    }
}