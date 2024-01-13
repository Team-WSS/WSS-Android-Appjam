package com.teamwss.websoso.ui.memoWrite

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.teamwss.websoso.databinding.DialogMemoCancelBinding

class DialogMemoCancel(private val clickExit: () -> Unit) :
    DialogFragment() {
    private var _binding: DialogMemoCancelBinding? = null
    private val binding: DialogMemoCancelBinding
        get() = requireNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = DialogMemoCancelBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        clickExitBtn()
        clickKeepWriteBtn()
    }

    private fun clickExitBtn() {
        binding.tvDialogCancelExitBtn.setOnClickListener {
            clickExit()
            dismiss()
        }
    }

    private fun clickKeepWriteBtn() {
        binding.tvDialogCancelDismissBtn.setOnClickListener {
            dismiss()
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}