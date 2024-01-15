package com.teamwss.websoso.ui.novelDetail.fragment

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.teamwss.websoso.databinding.DialogNovelDeleteBinding

class DialogNovelDelete(private val clickNovelDelete: () -> Unit) : DialogFragment() {
    private var _binding: DialogNovelDeleteBinding? = null
    private val binding: DialogNovelDeleteBinding
        get() = requireNotNull(_binding)

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

        clickNovelDeleteButton()
        clickCancelButton()
    }

    private fun clickNovelDeleteButton() {
        binding.tvDialogNovelDeleteDropBtn.setOnClickListener {
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