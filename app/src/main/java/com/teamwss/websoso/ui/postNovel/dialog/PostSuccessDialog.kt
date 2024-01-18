package com.teamwss.websoso.ui.postNovel.dialog

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.teamwss.websoso.databinding.DialogPostSuccessBinding
import com.teamwss.websoso.ui.main.MainActivity
import com.teamwss.websoso.ui.postNovel.PostNovelViewModel

class PostSuccessDialog(
    private val clickNavigateToMemo: () -> Unit,
    private val clickNavigateToHome: () -> Unit
) : DialogFragment() {

    private var _binding: DialogPostSuccessBinding? = null
    private val binding: DialogPostSuccessBinding get() = requireNotNull(_binding)
    private val viewModel: PostNovelViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = DialogPostSuccessBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupDialogUI()
        setupNavigateMemoListener()
        setupNavigateHomeListener()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun setupDialogUI() {
        dialog?.window?.apply {
            setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            isCancelable = false
        }
    }

    private fun setupNavigateMemoListener() {
        binding.llNavigateToMemo.setOnClickListener {
            clickNavigateToMemo()
            dismiss()
        }
    }

    private fun setupNavigateHomeListener() {
        binding.tvBackToHome.setOnClickListener {
            val intent = Intent(activity, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            dismiss()
        }
    }
}