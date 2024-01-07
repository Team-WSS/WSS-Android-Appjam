package com.teamwss.websoso.ui.postNovel

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.AppBarLayout
import com.teamwss.websoso.databinding.ActivityPostNovelBinding
import kotlin.math.pow

class PostNovelActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPostNovelBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostNovelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAppBar()
        showNavigateLeftDialog()
        setupDateToggle()
        setupChipGroupListener()
        showDatePickerBottomSheet()
    }

    private fun setupAppBar() {
        val scrollView: ScrollView = binding.svPost
        val appBarLayout: AppBarLayout = binding.alPostAppBar
        val titleView: TextView = binding.tvPostTitle

        // 스크롤 높이에 따라 alpha값 변화
        scrollView.viewTreeObserver.addOnScrollChangedListener {
            val scrollY = scrollView.scrollY
            val maxHeight = binding.ivPostCoverBackground.height - appBarLayout.height

            // 제곱식을 통해 alpha값 보정
            val scrollRatio = (scrollY.toFloat() / maxHeight).coerceAtMost(1f).pow(3 / 2)
            val colorAlpha = (scrollRatio * 255).toInt()

            appBarLayout.setBackgroundColor(Color.argb(colorAlpha, 255, 255, 255))
            titleView.setTextColor(Color.argb(colorAlpha, 0, 0, 0))
        }
    }

    // 뒤로가기를 눌렀을 때 나오는 Dialog
    private fun showNavigateLeftDialog() {
        binding.ivPostNavigateLeft.setOnClickListener {
            binding.vPostDialogBackground.visibility = View.VISIBLE
            setupNavigateLeftDialog.show()
        }
    }

    // PostNavigateLeftDialog 객체를 필드에 보관
    private val setupNavigateLeftDialog: PostNavigateLeftDialog by lazy {
        PostNavigateLeftDialog(this).apply {
            setItemClickListener(object : PostNavigateLeftDialog.ItemClickListener {
                override fun onExitButtonClick() {
                    finish()
                }
            })
            setOnDismissListener {
                binding.vPostDialogBackground.visibility = View.INVISIBLE
            }
        }
    }

    // 날짜 토글 설정
    private fun setupDateToggle() {
        val ivPostDateSwitch = binding.ivPostDateSwitch
        val llPostReadDate = binding.llPostReadDate
        ivPostDateSwitch.isSelected = true
        ivPostDateSwitch.setOnClickListener {
            it.isSelected = !it.isSelected
            llPostReadDate.visibility = if (it.isSelected) View.VISIBLE else View.GONE
        }
    }

    // 체크 상태에 따라 날짜 상태 분기
    private fun setupChipGroupListener() {
        val readStatusChipGroup = binding.cgPostReadStatus
        readStatusChipGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                binding.cReadStatusRead.id -> {
                    updateDateVisibility(isStartDateVisible = true, isEndDateVisible = true)
                    binding.tvPostReadDateTitle.text = "읽은 날짜"
                }

                binding.cReadStatusReading.id -> {
                    updateDateVisibility(isStartDateVisible = true, isEndDateVisible = false)
                    binding.tvPostReadDateTitle.text = "시작 날짜"
                }

                binding.cReadStatusStop.id -> {
                    updateDateVisibility(isStartDateVisible = false, isEndDateVisible = true)
                    binding.tvPostReadDateTitle.text = "종료 날짜"
                }

                binding.cReadStatusWant.id -> {
                    binding.clPostReadDate.visibility = View.GONE
                }
            }
        }
    }

    // 가시 여부 설정
    private fun View.setVisibility(isVisible: Boolean) {
        this.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    // 날짜 변경이 보일지 설정
    private fun updateDateVisibility(isStartDateVisible: Boolean, isEndDateVisible: Boolean) {
        with(binding) {
            clPostReadDate.visibility = View.VISIBLE

            tvPostReadDateStart.setVisibility(isStartDateVisible)
            tvPostReadDateTilde.setVisibility(isStartDateVisible && isEndDateVisible)
            tvPostReadDateEnd.setVisibility(isEndDateVisible)
        }
    }

    // 바텀시트 설정
    private val datePickerBottomSheet: DatePickerBottomSheet by lazy {
        DatePickerBottomSheet(this).apply {
            setOnDismissListener {
                binding.vPostDialogBackground.visibility = View.INVISIBLE
            }
        }
    }

    // 바텀시트 표시
    private fun showDatePickerBottomSheet() {
        binding.llPostReadDate.setOnClickListener {
            binding.vPostDialogBackground.visibility = View.VISIBLE
            datePickerBottomSheet.show()
        }
    }
}