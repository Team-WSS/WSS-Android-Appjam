package com.teamwss.websoso.ui.main.record

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import com.teamwss.websoso.R
import com.teamwss.websoso.databinding.FragmentRecordBinding
import com.teamwss.websoso.ui.main.library.LibraryFragment
import com.teamwss.websoso.ui.memoPlain.MemoPlainActivity

class RecordFragment : Fragment() {
    private lateinit var binding: FragmentRecordBinding
    private val recordViewModel: RecordViewModel by viewModels()
    private val memoAdapter: RecordAdapter by lazy { RecordAdapter(::navigateToMemoPlainActivity) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupMemos()
        setupMemoCount()
        setupButtonRecordClickListener()
    }

    private fun setupRecyclerView() {
        binding.rvMemoList.adapter = memoAdapter
    }

    private fun setupMemos() {
        recordViewModel.memos.observe(viewLifecycleOwner) { memos ->
            memoAdapter.updateMemoItems(memos)
        }
    }

    private fun setupMemoCount() {
        recordViewModel.memoCount.observe(viewLifecycleOwner) { count ->
            binding.tvRecordNovelCount.text = getString(R.string.record_novel_count, count)
            when (count == 0L) {
                true -> {
                    binding.viewHeaderUnderLine.visibility = View.VISIBLE
                    binding.lyNovelNoExist.visibility = View.VISIBLE
                    binding.lyNovelExist.visibility = View.INVISIBLE
                }

                false -> {
                    binding.lyNovelNoExist.visibility = View.INVISIBLE
                    binding.lyNovelExist.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun navigateToMemoPlainActivity(memoId: Long) {
        val intent = MemoPlainActivity.newIntent(requireContext(), memoId)
        startActivity(intent)
    }

    private fun setupButtonRecordClickListener() {
        binding.btnRecordGoToPostNovel.setOnClickListener {
            parentFragmentManager.commit {
                replace(R.id.fcvMain, LibraryFragment.newInstance())
            }
        }
    }

    companion object {
        fun newInstance() = RecordFragment()
    }
}

