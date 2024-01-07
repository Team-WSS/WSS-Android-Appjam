package com.teamwss.websoso.ui.postNovel

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.NumberPicker
import com.teamwss.websoso.R
import com.teamwss.websoso.databinding.BottomSheetPostBinding

class DatePickerBottomSheet(context: Context) : Dialog(context) {
    private lateinit var binding: BottomSheetPostBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 바인딩과 뷰 설정
        setupView()

        // 날짜 피커 설정
        setupDatePicker()

        // 다이얼로그 설정
        setupDialog()

        // 버튼 클릭 이벤트 설정
        binding.llPostButton.setOnClickListener {
            dismiss()
        }
    }

    private fun setupView() {
        binding = BottomSheetPostBinding.inflate(LayoutInflater.from(context))
        setContentView(binding.root)
    }

    private fun setupDatePicker() {
        with(binding) {
            // 순환 안되게 막기
            npPostBottomSheetYear.wrapSelectorWheel = false
            npPostBottomSheetMonth.wrapSelectorWheel = false
            npPostBottomSheetDay.wrapSelectorWheel = false

            // 최소값 설정
            npPostBottomSheetYear.minValue = 1
            npPostBottomSheetMonth.minValue = 1
            npPostBottomSheetDay.minValue = 1

            // 최대값 설정
            npPostBottomSheetYear.maxValue = 9999
            npPostBottomSheetMonth.maxValue = 12
            npPostBottomSheetDay.maxValue = 31

            // 연도와 월이 바뀔 때마다 일의 최대값 설정
            npPostBottomSheetYear.setOnValueChangedListener { _, _, _ -> setDayMaxValue() }
            npPostBottomSheetMonth.setOnValueChangedListener { _, _, _ -> setDayMaxValue() }

            // 출력 형태 설정
            npPostBottomSheetYear.setFormatter { String.format("%04d", it) }
            npPostBottomSheetMonth.setFormatter { String.format("%02d", it) }
            npPostBottomSheetDay.setFormatter { String.format("%02d", it) }
        }
    }

    private fun setupDialog() {
        // 배경 투명화를 통해 둥근 다이얼로그
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window?.attributes?.windowAnimations = R.style.DialogAnimation
        window?.setGravity(Gravity.BOTTOM)
        window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    // 월 체크
    private fun setDayMaxValue() {
        val year = binding.npPostBottomSheetYear.value
        val month = binding.npPostBottomSheetMonth.value

        // 월에 따른 일의 최대값 설정
        binding.npPostBottomSheetDay.maxValue = when (month) {
            2 -> if (isLeapYear(year)) 29 else 28
            4, 6, 9, 11 -> 30
            else -> 31
        }
    }

    // 윤년 체크
    private fun isLeapYear(year: Int): Boolean {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)
    }
}