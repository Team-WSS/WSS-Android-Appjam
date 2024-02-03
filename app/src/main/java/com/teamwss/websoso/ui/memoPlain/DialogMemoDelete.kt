package com.teamwss.websoso.ui.memoPlain

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.teamwss.websoso.R
import com.teamwss.websoso.databinding.DialogMemoDeleteBinding
import com.teamwss.websoso.ui.common.view.CustomSnackBar

class DialogMemoDelete(private val memoDeleteSuccess: () -> Unit) : DialogFragment() {
    private var _binding: DialogMemoDeleteBinding? = null
    private val binding: DialogMemoDeleteBinding
        get() = requireNotNull(_binding)

    private val memoPlainViewModel: MemoPlainViewModel by activityViewModels()
    private var memoId: Long = 0

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

        observeMemoId()
        onClickMemoDropButton()
        onClickMemoKeepButton()
        observeDeleteMemoSuccess()
    }

    private fun observeMemoId() {
        memoPlainViewModel.memoId.observe(this) {
            this.memoId = memoPlainViewModel.memoId.value!!
        }
    }

    private fun onClickMemoDropButton() {
        binding.tvDialogMemoDeleteDropBtn.setOnClickListener {
            memoPlainViewModel.deleteMemo(memoId)
        }
    }

    private fun onClickMemoKeepButton() {
        binding.tvDialogMemoDeleteKeepBtn.setOnClickListener {
            dismiss()
        }
    }

    private fun observeDeleteMemoSuccess() {
        memoPlainViewModel.isMemoDelete.observe(viewLifecycleOwner) { isDeleted ->
            if (isDeleted) {
                memoDeleteSuccess()
                dismiss()
            } else {
                val drawable =
                    ContextCompat.getDrawable(requireContext(), R.drawable.ic_alert_warning)
                CustomSnackBar.make(binding.root)
                    .setText("메모를 저장에 실패했어요")
                    .setIcon(
                        drawable ?: ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.ic_alert_warning
                        )!!
                    )
                    .show()
            }
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}