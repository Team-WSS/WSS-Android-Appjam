package com.teamwss.websoso.ui.postNovel

import android.app.Dialog
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.teamwss.websoso.R
import com.teamwss.websoso.databinding.DialogDatePickerBinding
import java.time.LocalDate

class DatePickerDialog(context: Context) : Dialog(context) {
    private lateinit var inputSelectedDateListener: InputSelectedDateListener
    private lateinit var binding: DialogDatePickerBinding
    private lateinit var readStatus: String
    private lateinit var startDate: String
    private lateinit var endDate: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupView()
        setupDatePicker()
        setupDialog()
        setupPostButtonClickListener()
        setupDateTypeSelector()
        setupNumberPickerListener()
        checkReadStatus()
    }

    private fun setupView() {
        binding = DialogDatePickerBinding.inflate(LayoutInflater.from(context))
        setContentView(binding.root)
    }

    fun setDialogReadStatus(readStatus: String, startDate: String, endDate: String) {
        this.readStatus = readStatus
        this.startDate = startDate
        this.endDate = endDate
    }

    private fun setupDatePicker() {
        with(binding) {
            npPostDatePickerYear.wrapSelectorWheel = false
            npPostDatePickerMonth.wrapSelectorWheel = false
            npPostDatePickerDay.wrapSelectorWheel = false

            npPostDatePickerYear.minValue = 1
            npPostDatePickerMonth.minValue = 1
            npPostDatePickerDay.minValue = 1

            npPostDatePickerYear.maxValue = 9999
            npPostDatePickerMonth.maxValue = 12
            npPostDatePickerDay.maxValue = 31

            npPostDatePickerYear.setFormatter { String.format("%04d", it) }
            npPostDatePickerMonth.setFormatter { String.format("%02d", it) }
            npPostDatePickerDay.setFormatter { String.format("%02d", it) }
        }
    }

    private fun setupDialog() {
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window?.attributes?.windowAnimations = R.style.BottomSheetDialogAnimation
        window?.setGravity(Gravity.BOTTOM)
        window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    private fun setDayMaxValue() {
        val year = binding.npPostDatePickerYear.value
        val month = binding.npPostDatePickerMonth.value

        binding.npPostDatePickerDay.maxValue = when (month) {
            2 -> if (isLeapYear(year)) 29 else 28
            4, 6, 9, 11 -> 30
            else -> 31
        }
    }

    private fun isLeapYear(year: Int): Boolean {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)
    }

    private fun setupPostButtonClickListener() {
        binding.llDatePostButton.setOnClickListener {
            setupAnotherDateValid()
            inputSelectedDateListener.inputSelectedDate(startDate, endDate)
            dismiss()
        }
    }

    private fun setupDateTypeSelector() {
        with(binding) {
            updateDateSelector(true)
            updateDateNumberPicker(true)

            llPostDatePickerReadDateStart.setOnClickListener {
                updateDateSelector(true)
                updateDateNumberPicker(true)
            }

            llPostDatePickerReadDateEnd.setOnClickListener {
                updateDateSelector(false)
                updateDateNumberPicker(false)
            }
        }
    }

    private fun updateDateSelector(isStart: Boolean) {
        with(binding) {
            llPostDatePickerReadDateStart.isSelected = isStart
            llPostDatePickerReadDateEnd.isSelected = !isStart

            val startColor = if (isStart) R.color.primary_100_6341F0 else R.color.gray_100_CBCBD1
            val endColor = if (!isStart) R.color.primary_100_6341F0 else R.color.gray_100_CBCBD1

            tvPostDatePickerReadDateStartTitle.setTextColor(
                ContextCompat.getColor(
                    context, startColor
                )
            )
            tvPostDatePickerReadDateStart.setTextColor(ContextCompat.getColor(context, startColor))

            tvPostDatePickerReadDateEndTitle.setTextColor(
                ContextCompat.getColor(
                    context, endColor
                )
            )
            tvPostDatePickerReadDateEnd.setTextColor(ContextCompat.getColor(context, endColor))
        }
    }

    private fun updateDateNumberPicker(isStart: Boolean) {
        if (isStart) {
            with(binding) {
                npPostDatePickerYear.value = startDate.split("-")[0].toInt()
                npPostDatePickerMonth.value = startDate.split("-")[1].toInt()
                npPostDatePickerDay.value = startDate.split("-")[2].toInt()
            }
        } else {
            with(binding) {
                npPostDatePickerYear.value = endDate.split("-")[0].toInt()
                npPostDatePickerMonth.value = endDate.split("-")[1].toInt()
                npPostDatePickerDay.value = endDate.split("-")[2].toInt()
            }
        }
    }

    private fun updateDate(isStart: Boolean) {
        val year = binding.npPostDatePickerYear.value
        val month = binding.npPostDatePickerMonth.value
        val day = binding.npPostDatePickerDay.value

        val formattedYear = String.format("%04d", year)
        val formattedMonth = String.format("%02d", month)
        val formattedDay = String.format("%02d", day)
        val formattedDate = "$formattedYear-$formattedMonth-$formattedDay"

        if (isStart) {
            startDate = formattedDate
        } else {
            endDate = formattedDate
        }
    }

    private fun setupNumberPickerListener() {
        with(binding) {
            npPostDatePickerYear.setOnValueChangedListener { _, _, _ ->
                setDayMaxValue()
                updateDate(
                    llPostDatePickerReadDateStart.isSelected
                )
                if (readStatus == context.getString(R.string.post_read_status_read)) isDateValid()
            }
            npPostDatePickerMonth.setOnValueChangedListener { _, _, _ ->
                setDayMaxValue()
                updateDate(
                    llPostDatePickerReadDateStart.isSelected
                )
                if (readStatus == context.getString(R.string.post_read_status_read)) isDateValid()
            }
            npPostDatePickerDay.setOnValueChangedListener { _, _, _ ->
                setDayMaxValue()
                updateDate(
                    llPostDatePickerReadDateStart.isSelected
                )
                if (readStatus == context.getString(R.string.post_read_status_read)) isDateValid()
            }
        }
    }

    private fun checkReadStatus() {
        when (readStatus) {
            context.getString(R.string.post_read_status_read) -> {
                binding.clPostDatePickerReadDateDefault.visibility = View.VISIBLE
            }

            context.getString(R.string.post_read_status_reading) -> {
                binding.clPostDatePickerReadDateDefault.visibility = View.GONE
                binding.tvPostDatePickerReadDateTitle.text = readStatus
            }

            context.getString(R.string.post_read_status_stop) -> {
                binding.clPostDatePickerReadDateDefault.visibility = View.GONE
                binding.tvPostDatePickerReadDateTitle.text = readStatus
            }
        }
    }

    private fun isDateValid() {

        val selectedStartDate = LocalDate.of(
            startDate.split("-")[0].toInt(),
            startDate.split("-")[1].toInt(),
            startDate.split("-")[2].toInt()
        )
        val selectedEndDate = LocalDate.of(
            endDate.split("-")[0].toInt(),
            endDate.split("-")[1].toInt(),
            endDate.split("-")[2].toInt()
        )

        if (selectedStartDate.isAfter(selectedEndDate)) {
            binding.llDatePostButton.isEnabled = false
            binding.llDatePostButton.backgroundTintList =
                ColorStateList.valueOf(ContextCompat.getColor(context, R.color.gray_200_AEADB3))
        } else {
            binding.llDatePostButton.isEnabled = true
            binding.llDatePostButton.backgroundTintList =
                ColorStateList.valueOf(ContextCompat.getColor(context, R.color.primary_100_6341F0))
        }
    }

    private fun setupAnotherDateValid() {
        when (readStatus) {
            context.getString(R.string.post_read_status_reading) -> {
                endDate = startDate
            }

            context.getString(R.string.post_read_status_stop) -> {
                startDate = endDate
            }
        }
    }

    fun interface InputSelectedDateListener {
        fun inputSelectedDate(startDate: String, endDate: String)
    }

    fun setOnDateSelectedListener(listener: InputSelectedDateListener) {
        inputSelectedDateListener = listener
    }
}