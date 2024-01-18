package com.teamwss.websoso.ui.main.myPage

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.teamwss.websoso.R
import com.teamwss.websoso.databinding.DialogAvatarBinding

class AvatarDialogFragment : DialogFragment() {
    private lateinit var binding: DialogAvatarBinding
    private lateinit var myPageViewModel: MyPageViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogAvatarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myPageViewModel =
            ViewModelProvider(requireParentFragment()).get(MyPageViewModel::class.java)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = myPageViewModel

        setupDialogUI()
        setupAvatarRepresentButtonClickListener()
        setupTextViewMaintainClickListener()
        setupDialogAvatarReturnButtonClickListener()
    }

    private fun setupDialogUI() {
        dialog?.window?.apply {
            setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            attributes?.windowAnimations = R.style.PostBottomSheetDialogAnimation
            setGravity(Gravity.BOTTOM)
        }
    }

    private fun setupTextViewMaintainClickListener() {
        binding.tvDialogAvatarMaintain.setOnClickListener {
            dismiss()
        }
    }

    private fun setupAvatarRepresentButtonClickListener() {
        binding.btnDialogAvatarSetupRepresent.setOnClickListener {
            myPageViewModel.patchRepresentativeAvatar()
            dismiss()
        }
    }

    private fun setupDialogAvatarReturnButtonClickListener() {
        binding.btnDialogAvatarReturn.setOnClickListener {
            dismiss()
        }
    }

    companion object {
        const val TAG = "AvatarDialogFragment"

        fun newInstance() = AvatarDialogFragment()
    }
}


