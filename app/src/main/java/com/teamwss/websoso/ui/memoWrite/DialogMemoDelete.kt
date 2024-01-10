package com.teamwss.websoso.ui.memoWrite

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.teamwss.websoso.databinding.DialogMemoDeleteBinding

class DialogMemoDelete : DialogFragment() {
    private var _binding: DialogMemoDeleteBinding? = null
    private val binding: DialogMemoDeleteBinding
        get() = requireNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogMemoDeleteBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        clickMemoDropBtn()
        clickMemoKeepBtn()
    }

    private fun clickMemoDropBtn() {
        binding.btnDialogMemoDeleteDrop.setOnClickListener {
            // 뷰모델 @Delete /memos/{memoId}
        }
    }

    private fun clickMemoKeepBtn() {
        binding.btnDialogMemoDeleteKeep.setOnClickListener {
            dismiss()
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}