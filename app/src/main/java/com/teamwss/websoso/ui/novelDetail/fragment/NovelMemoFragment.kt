package com.teamwss.websoso.ui.novelDetail.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.teamwss.websoso.databinding.FragmentNovelMemoBinding
import com.teamwss.websoso.ui.memoPlain.MemoPlainActivity
import com.teamwss.websoso.ui.novelDetail.NovelDetailViewModel
import com.teamwss.websoso.ui.novelDetail.adapter.NovelDetailMemoAdapter

class NovelMemoFragment : Fragment() {
    private var _binding: FragmentNovelMemoBinding? = null
    private val binding: FragmentNovelMemoBinding
        get() = requireNotNull(_binding)
    private val novelMemoAdapter: NovelDetailMemoAdapter by lazy { NovelDetailMemoAdapter(::navigateToMemoPlain) }
    private val novelDetailViewModel: NovelDetailViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNovelMemoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupLifecycleOwner()
        setupDataBinding()
        initRecyclerView()
//        onClickAddingMemoBox()
        observeNovelMemoData()
    }

    private fun setupLifecycleOwner() {
        binding.lifecycleOwner = viewLifecycleOwner
    }

    private fun setupDataBinding() {
        binding.novelDetailViewModel = novelDetailViewModel
    }

    private fun initRecyclerView() {
        binding.rvNovelMemo.apply {
            adapter = novelMemoAdapter
        }
    }

    //    private fun onClickAddingMemoBox() {
//        binding.clNovelMemoNavigateNewMemo.setOnClickListener {
//            navigateMemoWrite()
//        }
//    }
    private fun navigateToMemoPlain(memoId: Long) {
        val intent = MemoPlainActivity.createIntent(requireActivity(), memoId)
        startActivity(intent)
    }

    private fun observeNovelMemoData() {
        novelDetailViewModel.userNovelMemoInfoResponse.observe(viewLifecycleOwner) { usersResponse ->
            novelMemoAdapter.updateUserNovelMemo(usersResponse.memos)
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}