package com.teamwss.websoso.ui.memoWrite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.teamwss.websoso.databinding.DialogMemoCancelBinding

class DialogMemoCancel : DialogFragment() {
    private var _binding: DialogMemoCancelBinding? = null
    private val binding: DialogMemoCancelBinding
        get() = requireNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogMemoCancelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        clickExitBtn()
        clickKeepWriteBtn()
    }

    private fun clickExitBtn() {
        // 나가기 버튼 눌렀을 때 상태가 작성 취소로 바뀜 -> 뷰가 다르게 바껴야 함 + dismiss()
    }

    private fun clickKeepWriteBtn() {
        dismiss()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}