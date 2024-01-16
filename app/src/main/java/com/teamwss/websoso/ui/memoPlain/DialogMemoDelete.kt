package com.teamwss.websoso.ui.memoPlain

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.snackbar.Snackbar
import com.teamwss.websoso.databinding.DialogMemoDeleteBinding

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
                Snackbar.make(
                    requireActivity().findViewById(android.R.id.content),
                    "메모 삭제 실패",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}