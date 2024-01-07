package com.teamwss.websoso.ui.postNovel

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.AppBarLayout
import com.teamwss.websoso.R
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
        showDatePickerDialog()
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
            setExitButtonClickListener(object : PostNavigateLeftDialog.ExitButtonClickListener {
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

    // 바텀시트 표시
    private fun showDatePickerDialog() {
        binding.llPostReadDate.setOnClickListener {
            binding.vPostDialogBackground.visibility = View.VISIBLE

            // 바텀시트 설정 및 다이알로그 생성
            val datePickerDialog = DatePickerDialog(this).apply {
                setOnDateSelectedListener(object : DatePickerDialog.OnDateSelectedListener {
                    override fun onDateSelected(startDate: String, endDate: String) {
                        binding.tvPostReadDateStart.text = startDate
                        binding.tvPostReadDateEnd.text = endDate
                    }
                })
                setOnDismissListener {
                    binding.vPostDialogBackground.visibility = View.INVISIBLE
                }
            }

            with(binding) {
                val readStatus = tvPostReadDateTitle.text.toString()
                val startDate = tvPostReadDateStart.text.toString()
                val endDate = tvPostReadDateEnd.text.toString()

                datePickerDialog.setDialogReadStatus(readStatus, startDate, endDate)
                datePickerDialog.show()
            }
        }
    }
}