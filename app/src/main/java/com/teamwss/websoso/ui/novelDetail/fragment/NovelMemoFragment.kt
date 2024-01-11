package com.teamwss.websoso.ui.novelDetail.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.teamwss.websoso.databinding.FragmentNovelMemoBinding
import com.teamwss.websoso.ui.memoWrite.MemoWriteActivity
import com.teamwss.websoso.ui.novelDetail.adapter.NovelDetailMemoAdapter

class NovelMemoFragment : Fragment() {
    private var _binding: FragmentNovelMemoBinding? = null
    private val binding: FragmentNovelMemoBinding
        get() = requireNotNull(_binding)

    private lateinit var rvNovelMemoAdapter: NovelDetailMemoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNovelMemoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        initRecyclerView()
        clickAddingMemoFab()
        clickAddingMemoBox()
    }

    private fun initAdapter() {
        rvNovelMemoAdapter = NovelDetailMemoAdapter()
    }

    private fun initRecyclerView() {
        with(binding.rvNovelMemo) {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        }
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

    override fun onResume() {
        super.onResume()

        view?.requestLayout()
    }
}