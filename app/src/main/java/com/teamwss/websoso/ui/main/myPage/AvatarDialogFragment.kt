package com.teamwss.websoso.ui.main.myPage

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
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
        myPageViewModel = ViewModelProvider(requireParentFragment()).get(MyPageViewModel::class.java)
        binding.lifecycleOwner = this
        binding.viewModel = myPageViewModel


        Toast.makeText(requireContext(), myPageViewModel.selectedAvatarId.value.toString(), Toast.LENGTH_SHORT).show()
        setupDialogUI()
        setupRepresentativeAvatarButtonClickListener()
    }

    private fun setupDialogUI() {
        dialog?.window?.apply {
            setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            attributes?.windowAnimations = R.style.PostBottomSheetDialogAnimation
            setGravity(Gravity.BOTTOM)
        }
    }

    private fun setupRepresentativeAvatarButtonClickListener() {
        binding.btnDialogAvatarRepresent.setOnClickListener {
            myPageViewModel.patchRepresentativeAvatar()
            dismiss()
        }
    }

    companion object {
        const val TAG = "AvatarDialogFragment"
        private const val ARG_AVATAR_ID = "avatar_id"

        fun newInstance() = AvatarDialogFragment()
    }
}


