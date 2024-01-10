package com.teamwss.websoso.ui.postNovel

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.teamwss.websoso.R
import com.teamwss.websoso.databinding.ActivityPostNovelBinding
import jp.wasabeef.transformers.coil.BlurTransformation
import jp.wasabeef.transformers.coil.RoundedCornersTransformation
import kotlin.math.pow

class PostNovelActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPostNovelBinding
    private val postNovelViewModel: PostNovelViewModel by lazy {
        PostNovelViewModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostNovelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showNavigateLeftDialog()
        setupAppBar()
        setupDateToggle()
        setupReadStatusChip()
        showDatePickerDialog()
        initNovelInfo()
    }

    private val setupNavigateLeftDialog: PostNavigateLeftDialog by lazy {
        PostNavigateLeftDialog(this).apply {
            setExitButtonClickListener { finish() }
            setOnDismissListener {
                hideBlackBackground()
            }
        }
    }

    private fun showNavigateLeftDialog() {
        binding.ivPostNavigateLeft.setOnClickListener {
            showBlackBackground()
            setupNavigateLeftDialog.show()
        }
    }

    private fun setupAppBar() {
        binding.svPost.viewTreeObserver.addOnScrollChangedListener {
            val scrollY = binding.svPost.scrollY
            val maxHeight = binding.ivPostCoverBackground.height - binding.alPostAppBar.height

            val scrollRatio = ((scrollY.toFloat() + 15) / maxHeight).coerceAtMost(1f).pow(3 / 2)
            val colorAlpha = (scrollRatio * 255).toInt()

            binding.alPostAppBar.setBackgroundColor(
                getColor(R.color.white).changeAlpha(colorAlpha)
            )
            binding.tvPostTitle.setTextColor(
                getColor(R.color.black).changeAlpha(colorAlpha)
            )
        }
    }

    private fun Int.changeAlpha(newAlpha: Int): Int {
        return Color.argb(newAlpha, Color.red(this), Color.green(this), Color.blue(this))
    }

    private fun setupDateToggle() {
        binding.scPostDateSwitch.setOnCheckedChangeListener { _, isChecked ->
            binding.llPostReadDate.setVisibility(
                isChecked
            )
        }
    }

    private fun setupReadStatusChip() {
        with(binding) {
            cReadStatusRead.setOnClickListener {
                updateDateVisibility(isStartDateVisible = true, isEndDateVisible = true)
                binding.tvPostReadDateTitle.text = getString(R.string.post_read_status_read)
            }
            cReadStatusReading.setOnClickListener {
                updateDateVisibility(isStartDateVisible = true, isEndDateVisible = false)
                binding.tvPostReadDateTitle.text = getString(R.string.post_read_status_reading)
            }
            cReadStatusStop.setOnClickListener {
                updateDateVisibility(isStartDateVisible = false, isEndDateVisible = true)
                binding.tvPostReadDateTitle.text = getString(R.string.post_read_status_stop)
            }
            cReadStatusWant.setOnClickListener {
                clPostReadDate.visibility = View.GONE
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

    private fun showDatePickerDialog() {
        binding.llPostReadDate.setOnClickListener {
            showBlackBackground()
            setDialogData(createDialog())
        }
    }

    private fun showBlackBackground() {
        binding.vPostDialogBackground.visibility = View.VISIBLE
    }

    private fun createDialog(): DatePickerDialog {
        return DatePickerDialog(this).apply {
            setOnDateSelectedListener(createDateSelectedListener())
            setOnDismissListener { hideBlackBackground() }
        }
    }

    private fun createDateSelectedListener(): DatePickerDialog.InputSelectedDateListener {
        return DatePickerDialog.InputSelectedDateListener { startDate, endDate ->
            binding.tvPostReadDateStart.text = startDate
            binding.tvPostReadDateEnd.text = endDate
        }
    }

    private fun hideBlackBackground() {
        binding.vPostDialogBackground.visibility = View.INVISIBLE
    }

    private fun setDialogData(datePickerDialog: DatePickerDialog) {
        with(binding) {
            val readStatus = tvPostReadDateTitle.text.toString()
            val startDate = tvPostReadDateStart.text.toString()
            val endDate = tvPostReadDateEnd.text.toString()

            datePickerDialog.setDialogReadStatus(readStatus, startDate, endDate)
            datePickerDialog.show()
        }
    }

    private fun initNovelInfo() {
        val dummyData = postNovelViewModel.getUserNovelInfo()
        with(binding) {
            tvPostNovelTitle.text = dummyData.userNovelTitle
            tvPostTitle.text = tvPostNovelTitle.text
            tvPostNovelAuthor.text = dummyData.userNovelAuthor
            tvPostNovelDetail.text = dummyData.userNovelDescription
            tvPostNovelGenre.text = dummyData.userNovelGenre

            rbPostRating.rating = dummyData.userNovelRating
            tvPostReadDateStart.text = dummyData.readStartDate
            tvPostReadDateEnd.text = dummyData.readEndDate

            val coverImg = dummyData.userNovelImg
            val loadingImg = R.drawable.img_loading_thumbnail
            ivPostCover.load(coverImg) {
                crossfade(true)
                placeholder(loadingImg)
                error(loadingImg)
                transformations(RoundedCornersTransformation(30))
            }
            ivPostCoverBackground.load(coverImg) {
                crossfade(true)
                placeholder(loadingImg)
                error(loadingImg)
                transformations(BlurTransformation(this@PostNovelActivity, 25))
            }

            when (dummyData.userNovelReadStatus) {
                getString(R.string.post_read_status_read) -> {
                    cReadStatusRead.isChecked = true
                }
                getString(R.string.post_read_status_reading) -> {
                    cReadStatusReading.isChecked = true
                }
                getString(R.string.post_read_status_stop) -> {
                    cReadStatusStop.isChecked = true
                }
                else -> {
                    cReadStatusWant.isChecked = true
                }
            }
        }
    }
}