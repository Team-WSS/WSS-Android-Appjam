package com.teamwss.websoso.ui.postNovel

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import com.teamwss.websoso.databinding.DialogPostWarningBinding

class PostNavigateLeftDialog(context: Context) : Dialog(context) {
    private lateinit var exitButtonClickListener: ExitButtonClickListener
    private lateinit var binding: DialogPostWarningBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogPostWarningBinding.inflate(LayoutInflater.from(context))
        setContentView(binding.root)

        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.llPostDialogKeepButton.setOnClickListener {
            dismiss()
        }

        binding.llPostDialogExitButton.setOnClickListener {
            exitButtonClickListener.onExitButtonClick()
            dismiss()
        }
    }

    fun interface ExitButtonClickListener {
        fun onExitButtonClick()
    }

    fun setExitButtonClickListener(listener: ExitButtonClickListener) {
        this.exitButtonClickListener = listener
    }
}