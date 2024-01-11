package com.teamwss.websoso.ui.postNovel

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.teamwss.websoso.R
import com.teamwss.websoso.databinding.ActivityPostNovelBinding
import com.teamwss.websoso.ui.postNovel.postNovelDialog.DatePickerDialog
import com.teamwss.websoso.ui.postNovel.postNovelDialog.PostNavigateLeftDialog
import com.teamwss.websoso.ui.postNovel.postNovelDialog.PostSuccessDialog
import com.teamwss.websoso.ui.postNovel.postNovelViewModel.PostNovelViewModel
import java.time.LocalDate
import kotlin.math.pow

class PostNovelActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPostNovelBinding
    private lateinit var postNovelViewModel: PostNovelViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostNovelBinding.inflate(layoutInflater)
        setContentView(binding.root)
        postNovelViewModel = ViewModelProvider(this)[PostNovelViewModel::class.java]

        binding.lifecycleOwner = this
        binding.postNovelViewModel = postNovelViewModel

        postNovelViewModel.updateIsDialogShown(false)

        setupAppBar()
        setupDateToggle()

        setupNavigateLeftDialog()
        setupDatePickerDialog()
        setupPostSuccessDialog()

        initUserNovelInfo()
        setupReadStatusUI()
        observeRatingBar()
    }

    private fun setupAppBar() {
        binding.svPost.viewTreeObserver.addOnScrollChangedListener {
            val scrollY = binding.svPost.scrollY
            val maxHeight = binding.ivPostCoverBackground.height - binding.alPostAppBar.height

            val scrollRatio = ((scrollY.toFloat() + 15) / maxHeight).coerceAtMost(1f).pow(3 / 2)
            val colorAlpha = (scrollRatio * 255).toInt()

            binding.alPostAppBar.setBackgroundColor(getColor(R.color.white).changeAlpha(colorAlpha))
            binding.tvPostTitle.setTextColor(getColor(R.color.black).changeAlpha(colorAlpha))
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

    private fun setupNavigateLeftDialog() {
        binding.ivPostNavigateLeft.setOnClickListener {
            postNovelViewModel.updateIsDialogShown(true)

            val dialogFragment = PostNavigateLeftDialog()
            dialogFragment.show(supportFragmentManager, "PostNavigateLeftDialog")
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
        postNovelViewModel.getUserNovelInfo()
        observeDummyData()
    }

    private fun observeDummyData() {
        postNovelViewModel.dummyData.observe(this@PostNovelActivity) { dummyData ->
            val readStatus = dummyData?.userNovelReadStatus ?: getString(R.string.api_read_status_finish)
            postNovelViewModel.updateReadStatus(readStatus)

            val readStartDate = dummyData?.readStartDate ?: LocalDate.now().toString()
            val readEndDate = dummyData?.readEndDate ?: LocalDate.now().toString()
            postNovelViewModel.updateReadDate(readStartDate, readEndDate)

            val novelRating = dummyData?.userNovelRating ?: 0.0f
            postNovelViewModel.updateRating(novelRating)
        }
    }

    private fun setupReadStatusUI() {
        postNovelViewModel.updateReadStatus(postNovelViewModel.readStatus.value.toString())
        postNovelViewModel.readStatus.observe(this@PostNovelActivity) {
            handleReadStatus(it)
        }
    }

    private fun handleReadStatus(readStatus: String) {
        when (readStatus) {
            getString(R.string.api_read_status_finish) -> updateUIForStatusFinish()
            getString(R.string.api_read_status_reading) -> updateUIForStatusReading()
            getString(R.string.api_read_status_drop) -> updateUIForStatusDrop()
            getString(R.string.api_read_status_wish) -> updateUIForStatusWish()
        }
    }

    private fun updateUIForStatusFinish() {
        postNovelViewModel.updateIsDateVisible(
            isStartDateVisible = true,
            isEndDateVisible = true
        )
        binding.tvPostReadDateTitle.text = getString(R.string.post_read_status_finish)
    }

    private fun updateUIForStatusReading() {
        postNovelViewModel.updateIsDateVisible(
            isStartDateVisible = true,
            isEndDateVisible = false
        )
        binding.tvPostReadDateTitle.text = getString(R.string.post_read_status_reading)
    }

    private fun updateUIForStatusDrop() {
        postNovelViewModel.updateIsDateVisible(
            isStartDateVisible = false,
            isEndDateVisible = true
        )
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
}
