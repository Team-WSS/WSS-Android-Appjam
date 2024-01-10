package com.teamwss.websoso.ui.memoWrite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.teamwss.websoso.databinding.DialogNovelDeleteBinding

class DialogNovelDelete : DialogFragment() {
    private var _binding: DialogNovelDeleteBinding? = null
    private val binding: DialogNovelDeleteBinding
        get() = requireNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogNovelDeleteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        clickNovelDeleteBtn()
        clickCancelBtn()
    }

    private fun clickNovelDeleteBtn() {
        // @Delete /user-novels/{userNovelId}
        // dismiss()
        // 서재 뷰로 이동
    }

    private fun clickCancelBtn() {
        dismiss()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}