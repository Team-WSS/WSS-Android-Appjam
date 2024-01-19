package com.teamwss.websoso.ui.main.record

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import com.teamwss.websoso.R
import com.teamwss.websoso.databinding.FragmentRecordBinding
import com.teamwss.websoso.ui.main.MainActivity
import com.teamwss.websoso.ui.main.library.LibraryFragment
import com.teamwss.websoso.ui.memoPlain.MemoPlainActivity

class RecordFragment : Fragment() {
    private lateinit var binding: FragmentRecordBinding
    private val recordViewModel: RecordViewModel by viewModels()
    private val memoAdapter: RecordAdapter by lazy { RecordAdapter(::navigateToMemoPlainActivity) }
    private lateinit var deleteMemoLauncher: ActivityResultLauncher<Intent>

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
        registerForDeleteMemoLauncher()
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
        deleteMemoLauncher.launch(intent)
    }

    private fun setupButtonRecordClickListener() {
        binding.btnRecordGoToPostNovel.setOnClickListener {
            navigateToLibraryFragment()
        }
    }

    private fun navigateToLibraryFragment() {
        parentFragmentManager.commit {
            replace(R.id.fcvMain, LibraryFragment.newInstance())
        }
        updateBottomNavigationForFragment()
    }

    private fun updateBottomNavigationForFragment() {
        val mainActivity = requireActivity() as MainActivity

        mainActivity.updateBottomNavigation(R.id.menu_library)
    }

    private fun registerForDeleteMemoLauncher() {
        deleteMemoLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val drawable =
                        ContextCompat.getDrawable(requireContext(), R.drawable.ic_alert_warning)
                    CustomSnackBar.make(binding.root)
                        .setText("메모를 삭제했어요")
                        .setIcon(
                            drawable ?: ContextCompat.getDrawable(
                                requireContext(),
                                R.drawable.ic_alert_warning
                            )!!
                        )
                        .show()
                }
            }
    }

    override fun onResume() {
        super.onResume()
        recordViewModel.updateMemo()
    }

    companion object {
        fun newInstance() = RecordFragment()
    }
}

