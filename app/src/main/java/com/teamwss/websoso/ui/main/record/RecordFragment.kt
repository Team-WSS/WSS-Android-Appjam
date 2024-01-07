package com.teamwss.websoso.ui.main.record

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.teamwss.websoso.R
import com.teamwss.websoso.databinding.FragmentRecordBinding
import com.teamwss.websoso.ui.main.record.model.RecordViewModel

class RecordFragment : Fragment() {
    private lateinit var binding: FragmentRecordBinding
    private lateinit var viewModel: RecordViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRecordBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(RecordViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerViewWithMemo()
        handleNovelCountUIChanges()
    }

    private fun setupRecyclerViewWithMemo() {
        val memoAdapter = RecordAdapter()
        binding.rvMemoList.adapter = memoAdapter
        viewModel.memoData.observe(viewLifecycleOwner) { memoList ->
            memoAdapter.setFriendList(memoList)
        }
    }

    private fun handleNovelCountUIChanges() {
        viewModel.novelCount.observe(viewLifecycleOwner) { novelCount ->
            val displayText = getString(R.string.record_novel_count, novelCount.toIntOrNull())
            binding.tvRecordNovelCount.text = displayText

            when (novelCount.toIntOrNull()) {

                0 -> {
                    binding.lyNovelNoExist.visibility = View.VISIBLE
                    binding.lyNovelExist.visibility = View.GONE
                }

                else -> {
                    binding.lyNovelExist.visibility = View.VISIBLE
                    binding.lyNovelNoExist.visibility = View.GONE
                }
            }
        }

    }

    companion object {
        fun newInstance() = RecordFragment()
    }
}