package com.teamwss.websoso.ui.postNovel

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.teamwss.websoso.R
import com.teamwss.websoso.databinding.ActivityPostNovelBinding
import jp.wasabeef.transformers.coil.BlurTransformation
import jp.wasabeef.transformers.coil.RoundedCornersTransformation
import kotlin.math.pow

class PostNovelActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPostNovelBinding
    private lateinit var postNovelViewModel: PostNovelViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostNovelBinding.inflate(layoutInflater)
        setContentView(binding.root)
        postNovelViewModel = ViewModelProvider(this)[PostNovelViewModel::class.java]

        observeIsDialogShown()
        setupNavigateLeftDialog()
        setupDatePickerDialog()

        setupAppBar()

        setupDateToggle()
        setupReadStatusChip()

        observeSelectedDate()
        initUserNovelInfo()
        updateUserNovelInfoUI()
    }

    private fun observeIsDialogShown() {
        postNovelViewModel.isDialogShown.observe(this) {
            binding.vPostDialogBackground.visibility = if (it) View.VISIBLE else View.GONE
        }
    }

    private val navigateLeftDialog: PostNavigateLeftDialog by lazy {
        PostNavigateLeftDialog(this).apply {
            setExitButtonClickListener { finish() }
            setOnDismissListener {
                postNovelViewModel.updateIsDialogShown(false)
            }
        }
    }

    private fun setupNavigateLeftDialog() {
        binding.ivPostNavigateLeft.setOnClickListener {
            postNovelViewModel.updateIsDialogShown(true)
            navigateLeftDialog.show()
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

    private fun setupReadStatusChip() {
        with(binding) {
            cReadStatusRead.setOnClickListener {
                postNovelViewModel.updateReadStatus(getString(R.string.c_read_status_read))
            }
            cReadStatusReading.setOnClickListener {
                postNovelViewModel.updateReadStatus(getString(R.string.c_read_status_reading))
            }
            cReadStatusStop.setOnClickListener {
                postNovelViewModel.updateReadStatus(getString(R.string.c_read_status_stop))
            }
            cReadStatusWant.setOnClickListener {
                postNovelViewModel.updateReadStatus(getString(R.string.c_read_status_want))
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

    private fun observeSelectedDate() {
        with(postNovelViewModel) {
            startDate.observe(this@PostNovelActivity) {
                binding.tvPostReadDateStart.text = it
            }
            endDate.observe(this@PostNovelActivity) {
                binding.tvPostReadDateEnd.text = it
            }
        }
    }

    private fun initUserNovelInfo() {
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
                getString(R.string.c_read_status_read) -> {
                    cReadStatusRead.isChecked = true
                }

                getString(R.string.c_read_status_reading) -> {
                    cReadStatusReading.isChecked = true
                }

                getString(R.string.c_read_status_stop) -> {
                    cReadStatusStop.isChecked = true
                }

                getString(R.string.c_read_status_want) -> {
                    cReadStatusWant.isChecked = true
                }
            }

            postNovelViewModel.updateReadDate(
                tvPostReadDateStart.text.toString(),
                tvPostReadDateEnd.text.toString()
            )

            postNovelViewModel.updateReadStatus(dummyData.userNovelReadStatus)
        }
    }

    private fun updateUserNovelInfoUI() {
        with(postNovelViewModel) {
            readStatus.observe(this@PostNovelActivity) {
                checkReadStatus(it)
            }
            startDate.observe(this@PostNovelActivity) {
                binding.tvPostReadDateStart.text = it
            }
            endDate.observe(this@PostNovelActivity) {
                binding.tvPostReadDateEnd.text = it
            }
        }
    }

    private fun checkReadStatus(readStatus: String) {
        when (readStatus) {
            getString(R.string.c_read_status_read) -> {
                updateDateVisibility(isStartDateVisible = true, isEndDateVisible = true)
                binding.tvPostReadDateTitle.text = getString(R.string.post_read_status_read)
            }

            getString(R.string.c_read_status_reading) -> {
                updateDateVisibility(isStartDateVisible = true, isEndDateVisible = false)
                binding.tvPostReadDateTitle.text = getString(R.string.post_read_status_reading)
            }

            getString(R.string.c_read_status_stop) -> {
                updateDateVisibility(isStartDateVisible = false, isEndDateVisible = true)
                binding.tvPostReadDateTitle.text = getString(R.string.post_read_status_stop)
            }

            getString(R.string.c_read_status_want) -> {
                binding.clPostReadDate.visibility = View.GONE
            }
        }
    }
}