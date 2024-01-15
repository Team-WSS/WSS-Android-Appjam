package com.teamwss.websoso.ui.main.myPage

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.teamwss.websoso.R
import com.teamwss.websoso.databinding.DialogAvatarBinding

class AvatarDialogFragment : DialogFragment() {
    private lateinit var binding: DialogAvatarBinding

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

        setupDialogUI()
    }

    private fun setupDialogUI() {
        dialog?.window?.apply {
            setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            attributes?.windowAnimations = R.style.BottomSheetDialogAnimation
            setGravity(Gravity.BOTTOM)
        }
    }

    companion object {
        const val TAG = "AvatarDialogFragment"

        fun newInstance(): AvatarDialogFragment {
            return AvatarDialogFragment()
        }
    }

}

