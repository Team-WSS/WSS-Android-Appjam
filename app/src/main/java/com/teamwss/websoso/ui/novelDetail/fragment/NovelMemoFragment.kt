package com.teamwss.websoso.ui.novelDetail.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.teamwss.websoso.databinding.FragmentNovelMemoBinding
import com.teamwss.websoso.ui.memoPlain.MemoPlainActivity
import com.teamwss.websoso.ui.memoWrite.MemoWriteActivity
import com.teamwss.websoso.ui.novelDetail.NovelDetailViewModel
import com.teamwss.websoso.ui.novelDetail.adapter.NovelDetailMemoAdapter
import kotlin.properties.Delegates

class NovelMemoFragment : Fragment() {
    private var _binding: FragmentNovelMemoBinding? = null
    private val binding: FragmentNovelMemoBinding
        get() = requireNotNull(_binding)
    private val novelMemoAdapter: NovelDetailMemoAdapter by lazy { NovelDetailMemoAdapter(::navigateToMemoPlain) }
    private val novelDetailViewModel: NovelDetailViewModel by activityViewModels()

    private var userNovelId by Delegates.notNull<Long>()
    private lateinit var userNovelTitle: String
    private lateinit var userNovelAuthor: String
    private lateinit var userNovelImage: String

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
        onClickAddingMemoBox()
        observeNovelMemoData()
        observeUserNovelId()
        observeUserNovelData()
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

    private fun observeNovelMemoData() {
        novelDetailViewModel.userNovelMemoInfoResponse.observe(viewLifecycleOwner) { usersResponse ->
            novelMemoAdapter.updateUserNovelMemo(usersResponse.memos)
        }
    }

    private fun observeUserNovelId() {
        novelDetailViewModel.userNovelId.observe(viewLifecycleOwner) {
            userNovelId = novelDetailViewModel.userNovelId.value!!.toLong()
        }
    }

    private fun observeUserNovelData() {
        novelDetailViewModel.userNovelMemoInfoResponse.observe(viewLifecycleOwner) {
            userNovelAuthor = novelDetailViewModel.userNovelMemoInfoResponse.value!!.userNovelAuthor
            userNovelTitle = novelDetailViewModel.userNovelMemoInfoResponse.value!!.userNovelTitle
            userNovelImage = novelDetailViewModel.userNovelMemoInfoResponse.value!!.userNovelImg
        }
    }

    private fun onClickAddingMemoBox() {
        binding.clNovelMemoNavigateNewMemo.setOnClickListener {
            val intent = MemoWriteActivity.newIntentFromDetail(
                requireContext(),
                userNovelId,
                userNovelTitle,
                userNovelAuthor,
                userNovelImage
            )
            startActivity(intent)
        }
    }

    private fun navigateToMemoPlain(memoId: Long) {
        val intent = MemoPlainActivity.newIntent(
            requireActivity(),
            memoId,
            userNovelTitle,
            userNovelAuthor,
            userNovelImage
        )
        startActivity(intent)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}