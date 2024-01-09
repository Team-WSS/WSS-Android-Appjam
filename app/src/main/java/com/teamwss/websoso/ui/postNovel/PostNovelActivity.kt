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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostNovelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showNavigateLeftDialog()
        setupAppBar()
        setupDateToggle()
        setupReadStatusChip()
        showDatePickerDialog()
        initDummyNovelInfo()
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

    private fun initDummyNovelInfo() {
        with(binding) {
            tvPostNovelTitle.text = "노 게임 노 라이프"
            tvPostTitle.text = "노 게임 노 라이프"
            tvPostNovelAuthor.text = "카미야 유우"
            ivPostCover.load("https://i.namu.wiki/i/j1S3TlFyve1UjbCnzF_g6qEgFnMi8usZ_DLCn8lP91FwgpPgwkv_GNCD2fmu5uEPgPU5CSdzDF5qwe_8Ains2UzdgGgI-bzT95MQeBrceU9E7Hr26fWBFREMLDGiZm01VtAXHgXRO9kviGz3sYwQ-w.webp") {
                crossfade(true)
                placeholder(R.drawable.img_cover_test)
                error(R.drawable.img_cover_test)
                transformations(RoundedCornersTransformation(30))
            }
            ivPostCoverBackground.load("https://i.namu.wiki/i/j1S3TlFyve1UjbCnzF_g6qEgFnMi8usZ_DLCn8lP91FwgpPgwkv_GNCD2fmu5uEPgPU5CSdzDF5qwe_8Ains2UzdgGgI-bzT95MQeBrceU9E7Hr26fWBFREMLDGiZm01VtAXHgXRO9kviGz3sYwQ-w.webp") {
                crossfade(true)
                placeholder(R.drawable.img_cover_test)
                error(R.drawable.img_cover_test)
                transformations(BlurTransformation(this@PostNovelActivity, 25))
            }
            rbPostRating.rating = 5.0f
            tvPostReadDateStart.text = "2023-12-26"
            tvPostReadDateEnd.text = "2023-12-26"
            tvPostNovelDetail.text =
                "백수에 골방지기지만 인터넷에서는 도시전설이라는 이야기마저 떠도는 천재 게이머 남매, 소라(空)와 시로(白). 둘이 합쳐 하나인 『　　』(공백)인 남매는 세상을 「쓰레기 게임」이라 부르며 지내던 어느 날, 『신』을 자칭하는 소년에게 이끌려 이세계로 소환된다. 그곳은 신에 의해 전쟁이 금지되었으며, 『모든 것』── 「국경선마저도 게임으로 결판이 나는」 세계였다.\n\n다른 종족들에게 연패를 거듭해 마지막 도시 하나만을 남겨둔 인류. 소라와 시로 폐인남매가 이세계에서는 ‘인류의 구세주’가 될 수 있을까?"
            tvPostNovelGenre.text = "라이트노벨"
        }
    }
}