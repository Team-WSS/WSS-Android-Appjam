package com.teamwss.websoso.ui.postNovel

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.teamwss.websoso.R
import com.teamwss.websoso.databinding.DialogDatePickerBinding
import java.time.LocalDate

class DatePickerDialog : DialogFragment() {
    private var _binding: DialogDatePickerBinding? = null
    private val binding: DialogDatePickerBinding get() = requireNotNull(_binding)
    private val postNovelViewModel: PostNovelViewModel by activityViewModels()

    private lateinit var readStatus: String
    private lateinit var startDate: String
    private lateinit var endDate: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = DialogDatePickerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        readStatus = postNovelViewModel.readStatus.value ?: return
        startDate = postNovelViewModel.startDate.value ?: return
        endDate = postNovelViewModel.endDate.value ?: return

        setupDatePicker()
        setupPostButtonClickListener()
        setupDateTypeSelector()
        setupNumberPickerListener()
        checkReadStatus()
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.apply {
            setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            attributes?.windowAnimations = R.style.BottomSheetDialogAnimation
            setGravity(Gravity.BOTTOM)
        }
    }

    override fun onDestroyView() {
        postNovelViewModel.updateIsDialogShown(false)
        _binding = null
        super.onDestroyView()
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
            postNovelViewModel.updateReadDate(startDate, endDate)
            dismiss()
        }
    }

    private fun setupDateTypeSelector() {
        with(binding) {
            when (readStatus) {
                getString(R.string.c_read_status_stop) -> {
                    updateDateSelector(false)
                    updateDateNumberPicker(false)
                }

                else -> {
                    updateDateSelector(true)
                    updateDateNumberPicker(true)
                }
            }

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

            val startColorResId =
                if (isStart) R.color.primary_100_6341F0 else R.color.gray_100_CBCBD1
            val endColorResId =
                if (!isStart) R.color.primary_100_6341F0 else R.color.gray_100_CBCBD1
            val startColorValue = ContextCompat.getColor(requireContext(), startColorResId)
            val endColorValue = ContextCompat.getColor(requireContext(), endColorResId)
            tvPostDatePickerReadDateStartTitle.setTextColor(startColorValue)
            tvPostDatePickerReadDateStart.setTextColor(startColorValue)
            tvPostDatePickerReadDateEndTitle.setTextColor(endColorValue)
            tvPostDatePickerReadDateEnd.setTextColor(endColorValue)
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

    private fun formatDate(isStart: Boolean) {
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
        val readStatusRead = getString(R.string.c_read_status_read)
        with(binding) {
            npPostDatePickerYear.setOnValueChangedListener { _, _, _ ->
                setDayMaxValue()
                formatDate(llPostDatePickerReadDateStart.isSelected)
                if (readStatus == readStatusRead) isDateValid()
            }
            npPostDatePickerMonth.setOnValueChangedListener { _, _, _ ->
                setDayMaxValue()
                formatDate(llPostDatePickerReadDateStart.isSelected)
                if (readStatus == readStatusRead) isDateValid()
            }
            npPostDatePickerDay.setOnValueChangedListener { _, _, _ ->
                setDayMaxValue()
                formatDate(llPostDatePickerReadDateStart.isSelected)
                if (readStatus == readStatusRead) isDateValid()
            }
        }
    }

    private fun checkReadStatus() {
        when (readStatus) {
            getString(R.string.c_read_status_read) -> {
                binding.clPostDatePickerReadDateDefault.visibility = View.VISIBLE
            }

            getString(R.string.c_read_status_reading) -> {
                binding.clPostDatePickerReadDateDefault.visibility = View.GONE
                binding.tvPostDatePickerReadDateTitle.text =
                    getString(R.string.post_read_status_reading)
            }

            getString(R.string.c_read_status_stop) -> {
                binding.clPostDatePickerReadDateDefault.visibility = View.GONE
                binding.tvPostDatePickerReadDateTitle.text =
                    getString(R.string.post_read_status_stop)
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
                ColorStateList.valueOf(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.gray_200_AEADB3
                    )
                )
        } else {
            binding.llDatePostButton.isEnabled = true
            binding.llDatePostButton.backgroundTintList =
                ColorStateList.valueOf(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.primary_100_6341F0
                    )
                )
        }
    }

    private fun setupAnotherDateValid() {
        when (readStatus) {
            getString(R.string.c_read_status_reading) -> {
                endDate = startDate
            }

            getString(R.string.c_read_status_stop) -> {
                startDate = endDate
            }
        }
    }
}