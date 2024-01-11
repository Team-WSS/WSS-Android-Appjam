package com.teamwss.websoso.ui.postNovel.postNovelDialog

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
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
import java.time.LocalDate

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
        postNovelViewModel.updateIsDialogShown(false)
        _binding = null
        super.onDestroyView()
    }

    private fun initDialogDateInfo(){
        postNovelViewModel.readStatus.observe(this@DatePickerDialog) {
            setupNumberPickerListener(it)
            checkReadStatus(it)
        }
        postNovelViewModel.updateSelectedDate(
            postNovelViewModel.startDate.value!!,
            postNovelViewModel.endDate.value!!
        )
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
            postNovelViewModel.updateReadDate(
                postNovelViewModel.selectedStartDate.value!!,
                postNovelViewModel.selectedEndDate.value!!
            )
            dismiss()
        }
    }

    private fun setupDateTypeSelector() {
        postNovelViewModel.updateIsStartSelected(true)
        binding.llPostDatePickerReadDateStart.setOnClickListener {
            postNovelViewModel.updateIsStartSelected(true)
        }

        binding.llPostDatePickerReadDateEnd.setOnClickListener {
            postNovelViewModel.updateIsStartSelected(false)
        }

        postNovelViewModel.isStartSelected.observe(this@DatePickerDialog) {
            updateDateSelector(it)
            updateDateNumberPicker(it)
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
            binding.npPostDatePickerYear.value =
                postNovelViewModel.selectedStartDate.value!!.split("-")[0].toInt()
            binding.npPostDatePickerMonth.value =
                postNovelViewModel.selectedStartDate.value!!.split("-")[1].toInt()
            binding.npPostDatePickerDay.value =
                postNovelViewModel.selectedStartDate.value!!.split("-")[2].toInt()
        } else {
            binding.npPostDatePickerYear.value =
                postNovelViewModel.selectedEndDate.value!!.split("-")[0].toInt()
            binding.npPostDatePickerMonth.value =
                postNovelViewModel.selectedEndDate.value!!.split("-")[1].toInt()
            binding.npPostDatePickerDay.value =
                postNovelViewModel.selectedEndDate.value!!.split("-")[2].toInt()
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

    private fun setupNumberPickerListener(readStatus: String) {
        val readStatusRead = getString(R.string.api_read_status_finish)
        binding.npPostDatePickerYear.setOnValueChangedListener { _, _, _ ->
            setDayMaxValue()
            formatDate(postNovelViewModel.isStartSelected.value!!)
            if (readStatus == readStatusRead) isDateValid()
        }
        binding.npPostDatePickerMonth.setOnValueChangedListener { _, _, _ ->
            setDayMaxValue()
            formatDate(postNovelViewModel.isStartSelected.value!!)
            if (readStatus == readStatusRead) isDateValid()
        }
        binding.npPostDatePickerDay.setOnValueChangedListener { _, _, _ ->
            setDayMaxValue()
            formatDate(postNovelViewModel.isStartSelected.value!!)
            if (readStatus == readStatusRead) isDateValid()
        }
    }

    private fun checkReadStatus(readStatus: String) {
        when (readStatus) {
            getString(R.string.api_read_status_finish) -> {
                binding.clPostDatePickerReadDateDefault.visibility = View.VISIBLE
            }

            getString(R.string.api_read_status_reading) -> {
                binding.clPostDatePickerReadDateDefault.visibility = View.GONE
                binding.tvPostDatePickerReadDateTitle.text =
                    getString(R.string.post_read_status_reading)
                postNovelViewModel.updateIsStartSelected(true)
            }

            getString(R.string.api_read_status_drop) -> {
                binding.clPostDatePickerReadDateDefault.visibility = View.GONE
                binding.tvPostDatePickerReadDateTitle.text =
                    getString(R.string.post_read_status_stop)
                postNovelViewModel.updateIsStartSelected(false)
            }
        }
    }


    private fun isDateValid() {
        postNovelViewModel.selectedStartDate.observe(this@DatePickerDialog) {
            postNovelViewModel.updateIsDateValid()
            postNovelViewModel.isDateValid.observe(this@DatePickerDialog) {
                if (!it) {
                    binding.llDatePostButton.isEnabled = !it
                    binding.llDatePostButton.backgroundTintList =
                        ColorStateList.valueOf(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.gray_200_AEADB3
                            )
                        )
                } else {
                    binding.llDatePostButton.isEnabled = it
                    binding.llDatePostButton.backgroundTintList =
                        ColorStateList.valueOf(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.primary_100_6341F0
                            )
                        )
                }
            }
        }
    }

    private fun setupAnotherDateValid() {
        postNovelViewModel.readStatus.observe(this@DatePickerDialog) {
            when (it) {
                getString(R.string.api_read_status_reading) -> {
                    postNovelViewModel.updateSelectedDate(
                        postNovelViewModel.selectedStartDate.value!!,
                        postNovelViewModel.selectedStartDate.value!!
                    )
                }

                getString(R.string.api_read_status_drop) -> {
                    postNovelViewModel.updateSelectedDate(
                        postNovelViewModel.selectedEndDate.value!!,
                        postNovelViewModel.selectedEndDate.value!!
                    )
                }
            }
        }
    }
}