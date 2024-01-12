package com.teamwss.websoso.ui.postNovel.postNovelDialog

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.teamwss.websoso.databinding.DialogPostSuccessBinding
import com.teamwss.websoso.ui.main.MainActivity

class PostSuccessDialog : DialogFragment() {
    private var _binding: DialogPostSuccessBinding? = null
    private val binding: DialogPostSuccessBinding get() = requireNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = DialogPostSuccessBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupExitDialogListenser()
        setupExitActivityListener()
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.apply {
            setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
            isCancelable = false
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun setupExitDialogListenser() {
        binding.llNavigateToMemo.setOnClickListener {
            dismiss()
        }
    }

    private fun setupExitActivityListener() {
        binding.tvBackToHome.setOnClickListener {
            val intent = Intent(activity, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            dismiss()
        }
    }
}