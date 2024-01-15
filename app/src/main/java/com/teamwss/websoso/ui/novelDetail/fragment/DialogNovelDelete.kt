package com.teamwss.websoso.ui.novelDetail.fragment

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.teamwss.websoso.databinding.DialogNovelDeleteBinding
import com.teamwss.websoso.ui.novelDetail.NovelDetailViewModel

class DialogNovelDelete(private val clickNovelDelete: () -> Unit) : DialogFragment() {
    private var _binding: DialogNovelDeleteBinding? = null
    private val binding: DialogNovelDeleteBinding
        get() = requireNotNull(_binding)

    private val novelDetailViewModel: NovelDetailViewModel by activityViewModels()
    private var userNovelId: Long = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogNovelDeleteBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeUserNovelId()
        clickNovelDeleteButton()
        clickCancelButton()
    }

    private fun observeUserNovelId() {
        novelDetailViewModel.userNovelId.observe(this) {
            userNovelId = novelDetailViewModel.userNovelId.value!!.toLong()
        }
    }

    private fun clickNovelDeleteButton() {
        binding.tvDialogNovelDeleteDropBtn.setOnClickListener {
            novelDetailViewModel.deleteNovel(userNovelId)
            clickNovelDelete()
            dismiss()
        }
    }

    private fun clickCancelButton() {
        binding.tvDialogNovelDeleteDismissBtn.setOnClickListener {
            dismiss()
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}