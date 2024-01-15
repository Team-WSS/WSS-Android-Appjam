package com.teamwss.websoso.ui.memoPlain

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.teamwss.websoso.databinding.DialogMemoDeleteBinding

class DialogMemoDelete(private val clickMemoDelete: () -> Unit) : DialogFragment() {
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
    }

    private fun observeMemoId() {
        memoPlainViewModel.memoId.observe(this) {
            this.memoId = memoPlainViewModel.memoId.value!!
        }
    }

    private fun onClickMemoDropButton() {
        binding.tvDialogMemoDeleteDropBtn.setOnClickListener {
            Log.d("memoId", memoId.toString())
            memoPlainViewModel.deleteMemo(memoId)
            clickMemoDelete()
        }
    }

    private fun onClickMemoKeepButton() {
        binding.tvDialogMemoDeleteKeepBtn.setOnClickListener {
            dismiss()
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}