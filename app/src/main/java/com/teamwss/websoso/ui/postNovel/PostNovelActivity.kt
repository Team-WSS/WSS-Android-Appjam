package com.teamwss.websoso.ui.postNovel

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.teamwss.websoso.R
import com.teamwss.websoso.databinding.ActivityPostNovelBinding
import com.teamwss.websoso.ui.postNovel.postNovelDialog.DatePickerDialog
import com.teamwss.websoso.ui.postNovel.postNovelDialog.PostNavigateLeftDialog
import com.teamwss.websoso.ui.postNovel.postNovelViewModel.PostNovelViewModel
import jp.wasabeef.transformers.coil.BlurTransformation
import jp.wasabeef.transformers.coil.RoundedCornersTransformation
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

        setTranslucentOnStatusBar()
        setupNavigateLeftDialog()
        setupDatePickerDialog()

        setupAppBar()
        setupDateToggle()
        setupReadStatusChipClickListener()

        initUserNovelInfo()
        updateReadStatusUI()
        updateRatingBar()
    }
    private fun setTranslucentOnStatusBar() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
    }

    private fun setupNavigateLeftDialog() {
        binding.ivPostNavigateLeft.setOnClickListener {
            postNovelViewModel.updateIsDialogShown(true)
            val dialogFragment = PostNavigateLeftDialog()
            dialogFragment.show(supportFragmentManager, "PostNavigateLeftDialog")
        }
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
            binding.llPostReadDate.setVisibility(isChecked)
        }
    }

    private fun setupReadStatusChipClickListener() {
        binding.cReadStatusRead.setOnClickListener {
            postNovelViewModel.updateReadStatus(getString(R.string.api_read_status_finish))
        }
        binding.cReadStatusReading.setOnClickListener {
            postNovelViewModel.updateReadStatus(getString(R.string.api_read_status_reading))
        }
        binding.cReadStatusStop.setOnClickListener {
            postNovelViewModel.updateReadStatus(getString(R.string.api_read_status_drop))
        }
        binding.cReadStatusWant.setOnClickListener {
            postNovelViewModel.updateReadStatus(getString(R.string.api_read_status_wish))
        }
    }


    private fun updateReadStatusUI() {
        postNovelViewModel.updateReadStatus(postNovelViewModel.readStatus.value.toString())
        postNovelViewModel.readStatus.observe(this@PostNovelActivity) {
            when (it) {
                getString(R.string.api_read_status_finish) -> {
                    postNovelViewModel.updateIsDateVisible(isStartDateVisible = true, isEndDateVisible = true)
                    binding.tvPostReadDateTitle.text = getString(R.string.post_read_status_read)
                }

                getString(R.string.api_read_status_reading) -> {
                    postNovelViewModel.updateIsDateVisible(isStartDateVisible = true, isEndDateVisible = false)
                    binding.tvPostReadDateTitle.text = getString(R.string.post_read_status_reading)
                }

                getString(R.string.api_read_status_drop) -> {
                    postNovelViewModel.updateIsDateVisible(isStartDateVisible = false, isEndDateVisible = true)
                    binding.tvPostReadDateTitle.text = getString(R.string.post_read_status_stop)
                }

                getString(R.string.api_read_status_wish) -> {
                    binding.clPostReadDate.visibility = View.GONE
                }
            }
        }
    }

    private fun View.setVisibility(isVisible: Boolean) {
        this.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    private fun updateDateVisibility(isStartDateVisible: Boolean, isEndDateVisible: Boolean) {
        with(binding) {
            clPostReadDate.visibility = View.VISIBLE

            tvPostReadDateStart.setVisibility(isStartDateVisible)
            tvPostReadDateTilde.setVisibility(isStartDateVisible && isEndDateVisible)
            tvPostReadDateEnd.setVisibility(isEndDateVisible)
        }
    }

    private fun setupDatePickerDialog() {
        binding.llPostReadDate.setOnClickListener {
            postNovelViewModel.updateIsDialogShown(true)

            val dialogFragment = DatePickerDialog()
            dialogFragment.show(supportFragmentManager, "DatePickerDialog")
        }
    }

    private fun initUserNovelInfo() {
        postNovelViewModel.getUserNovelInfo()
        postNovelViewModel.dummyData.observe(this@PostNovelActivity) {
            when (postNovelViewModel.dummyData.value?.userNovelReadStatus) {
                getString(R.string.api_read_status_finish) -> {
                    binding.cReadStatusRead.isChecked = true
                    postNovelViewModel.updateReadStatus(getString(R.string.api_read_status_finish))
                }

                getString(R.string.api_read_status_reading) -> {
                    binding.cReadStatusReading.isChecked = true
                    postNovelViewModel.updateReadStatus(getString(R.string.api_read_status_reading))
                }

                getString(R.string.api_read_status_drop) -> {
                    binding.cReadStatusStop.isChecked = true
                    postNovelViewModel.updateReadStatus(getString(R.string.api_read_status_drop))
                }

                getString(R.string.api_read_status_wish) -> {
                    binding.cReadStatusWant.isChecked = true
                    postNovelViewModel.updateReadStatus(getString(R.string.api_read_status_wish))
                }
            }

            postNovelViewModel.updateReadDate(
                postNovelViewModel.dummyData.value?.readStartDate ?: LocalDate.now().toString(),
                postNovelViewModel.dummyData.value?.readEndDate ?: LocalDate.now().toString()
            )
        }
    }

    private fun updateRatingBar() {
            binding.rbPostRating.setOnRatingBarChangeListener { _, rating, _ ->
                postNovelViewModel.updateRating(rating)
            }
            postNovelViewModel.rating.observe(this@PostNovelActivity) {
                binding.rbPostRating.rating = it
            }
        }
    }
