package com.teamwss.websoso.ui.postNovel.postNovelDialog

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.teamwss.websoso.databinding.DialogPostExitBinding
import com.teamwss.websoso.ui.main.MainActivity
import com.teamwss.websoso.ui.postNovel.postNovelViewModel.PostNovelViewModel

class ExitPopupDialog : DialogFragment() {
    private var _binding: DialogPostExitBinding? = null
    private val binding: DialogPostExitBinding get() = requireNotNull(_binding)
    private val postNovelViewModel: PostNovelViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = DialogPostExitBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupDialogUI()
        setupExitDialogListener()
        setupExitActivityListener()
    }

    override fun onDestroyView() {
        postNovelViewModel.updateIsDialogShown(false)
        _binding = null
        super.onDestroyView()
    }

    private fun setupDialogUI() {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    private fun setupExitDialogListener() {
        binding.llPostDialogKeepButton.setOnClickListener {
            dismiss()
        }
    }

    private fun setupExitActivityListener() {
        binding.llPostDialogExitButton.setOnClickListener {
            val intent = Intent(activity, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            dismiss()
        }
    }
}