package com.teamwss.websoso.ui.postNovel

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import com.teamwss.websoso.databinding.DialogPostWarningBinding

class PostNavigateLeftDialog(context: Context): Dialog(context) {
    private lateinit var itemClickListener: ItemClickListener
    private lateinit var binding: DialogPostWarningBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogPostWarningBinding.inflate(LayoutInflater.from(context))
        setContentView(binding.root)

        // 배경 투명화를 통해 둥근 다이알로그
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        // 취소 가능 유무
        setCancelable(false)

        binding.llPostDialogKeepButton.setOnClickListener {
            itemClickListener.onKeepButtonClick()
            dismiss()
        }

        binding.llPostDialogExitButton.setOnClickListener {
            itemClickListener.onExitButtonClick()
            dismiss()
        }
    }

    interface ItemClickListener {
        fun onExitButtonClick()
        fun onKeepButtonClick()
    }

    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }
}