package com.teamwss.websoso.ui.postNovel

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.teamwss.websoso.databinding.DialogPostNavigateLeftBinding

class PostNavigateLeftDialog : DialogFragment() {
    private var _binding: DialogPostNavigateLeftBinding? = null
    private val binding: DialogPostNavigateLeftBinding get() = requireNotNull(_binding)
    private val postNovelViewModel: PostNovelViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = DialogPostNavigateLeftBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.llPostDialogKeepButton.setOnClickListener {
            dismiss()
        }

        binding.llPostDialogExitButton.setOnClickListener {
            activity?.finish()
            dismiss()
        }
    }

    override fun onDestroyView() {
        postNovelViewModel.updateIsDialogShown(false)
        _binding = null
        super.onDestroyView()
    }
}