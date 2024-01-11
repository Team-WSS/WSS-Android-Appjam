package com.teamwss.websoso.ui.postNovel.postNovelDialog

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
import com.teamwss.websoso.ui.postNovel.postNovelViewModel.PostNovelViewModel

class DatePickerDialog : DialogFragment() {
    private var _binding: DialogDatePickerBinding? = null
    private val binding: DialogDatePickerBinding get() = requireNotNull(_binding)
    private val postNovelViewModel: PostNovelViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = DialogDatePickerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = this
        binding.postNovelViewModel = postNovelViewModel

        initDialogDateInfo()
        setupDatePicker()
        setupPostButtonClickListener()
        setupDateTypeSelector()

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
        super.onDestroyView()
        _binding = null
        postNovelViewModel.updateIsDialogShown(false)
    }

    private fun initDialogDateInfo() {
        setupNumberPickerListener()
        postNovelViewModel.updateSelectedDate(
            postNovelViewModel.startDate.value!!,
            postNovelViewModel.endDate.value!!
        )
        postNovelViewModel.updateIsDateValid()
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

    private fun setupPostButtonClickListener() {
        binding.llDatePostButton.setOnClickListener {
            postNovelViewModel.setupAnotherDateValid(getString(R.string.api_read_status_reading), getString(R.string.api_read_status_drop))
            postNovelViewModel.updateReadDate(
                postNovelViewModel.selectedStartDate.value!!,
                postNovelViewModel.selectedEndDate.value!!
            )
            dismiss()
        }
    }

    private fun setupDateTypeSelector() {
        postNovelViewModel.updateIsNumberPickerStartSelected(true)
        binding.llPostDatePickerReadDateStart.setOnClickListener {
            postNovelViewModel.updateIsNumberPickerStartSelected(true)
        }

        binding.llPostDatePickerReadDateEnd.setOnClickListener {
            postNovelViewModel.updateIsNumberPickerStartSelected(false)
        }

        postNovelViewModel.isNumberPickerStartSelected.observe(this@DatePickerDialog) {
            updateDateSelector()
        }
    }

    private fun updateDateSelector() {
        with(binding) {
            val isStart = postNovelViewModel!!.isNumberPickerStartSelected.value!!
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

    private fun updateDayMaxValue() {
        binding.npPostDatePickerDay.maxValue =
            postNovelViewModel.setDayMaxValue(
                binding.npPostDatePickerYear.value,
                binding.npPostDatePickerMonth.value
            )
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
            postNovelViewModel.updateSelectedDate(
                formattedDate,
                postNovelViewModel.selectedEndDate.value!!
            )
        } else {
            postNovelViewModel.updateSelectedDate(
                postNovelViewModel.selectedStartDate.value!!,
                formattedDate
            )
        }
    }

    private fun setupNumberPickerListener() {
        binding.npPostDatePickerYear.setOnValueChangedListener { _, _, _ ->
            updateDayMaxValue()
            formatDate(postNovelViewModel.isNumberPickerStartSelected.value!!)
            postNovelViewModel.updateIsDateValid()
        }
        binding.npPostDatePickerMonth.setOnValueChangedListener { _, _, _ ->
            updateDayMaxValue()
            formatDate(postNovelViewModel.isNumberPickerStartSelected.value!!)
            postNovelViewModel.updateIsDateValid()
        }
        binding.npPostDatePickerDay.setOnValueChangedListener { _, _, _ ->
            updateDayMaxValue()
            formatDate(postNovelViewModel.isNumberPickerStartSelected.value!!)
            postNovelViewModel.updateIsDateValid()
        }
    }
}