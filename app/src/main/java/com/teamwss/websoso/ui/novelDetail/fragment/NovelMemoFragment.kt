package com.teamwss.websoso.ui.novelDetail.fragment

import android.os.Bundle
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
    private lateinit var novelMemoAdapter: NovelDetailMemoAdapter
    private val novelDetailViewModel: NovelDetailViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNovelMemoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerAdapter()
        initRecyclerView()
        onClickAddingMemoBox()
        observeNovelMemoData()
    }

    private fun initRecyclerAdapter() {
        novelMemoAdapter = NovelDetailMemoAdapter {
            val intent = MemoPlainActivity.createIntent(requireContext())
            startActivity(intent)
        }
    }

    private fun initRecyclerView() {
        binding.rvNovelMemo.apply {
            adapter = novelMemoAdapter
            setHasFixedSize(true)
        }
    }

    private fun onClickAddingMemoBox() {
        binding.clNovelMemoNavigateNewMemo.setOnClickListener {
            navigateMemoWrite()
        }
    }

    private fun navigateMemoWrite() {
        val intent = MemoPlainActivity.createIntent(requireActivity())
        startActivity(intent)
    }

    private fun observeNovelMemoData() {
        novelDetailViewModel.userNovelMemoInfoResponse.observe(viewLifecycleOwner) { usersResponse ->
            novelMemoAdapter.updateUserNovelMemo(usersResponse.memos)
        }
    }

    override fun onResume() {
        super.onResume()

        view?.requestLayout()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}