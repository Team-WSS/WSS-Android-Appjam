package com.teamwss.websoso.ui.novelDetail.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.teamwss.websoso.databinding.FragmentNovelMemoBinding
import com.teamwss.websoso.ui.memoWrite.MemoWriteActivity

class NovelMemoFragment : Fragment() {
    private var _binding: FragmentNovelMemoBinding? = null
    private val binding: FragmentNovelMemoBinding
        get() = requireNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNovelMemoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        clickAddingMemoFab()
        clickAddingMemoBox()
    }

    private fun clickAddingMemoFab() {
        binding.btnNovelMemoAddMemo.setOnClickListener {
            navigateMemoWrite()
        }
    }

    private fun clickAddingMemoBox() {
        binding.clNovelMemoNavigateNewMemo.setOnClickListener {
            navigateMemoWrite()
        }
    }

    private fun navigateMemoWrite() {
        val intent = Intent(activity, MemoWriteActivity::class.java)
        startActivity(intent)
    }
}