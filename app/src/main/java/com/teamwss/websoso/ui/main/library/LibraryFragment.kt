package com.teamwss.websoso.ui.main.library

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.teamwss.websoso.R
import com.teamwss.websoso.databinding.FragmentLibraryBinding
import com.teamwss.websoso.ui.main.library.adapter.LibraryViewPagerAdapter
import com.teamwss.websoso.ui.main.library.model.ReadState

class LibraryFragment : Fragment() {
    private lateinit var _binding: FragmentLibraryBinding
    private val binding: FragmentLibraryBinding get() = _binding
    private val viewPagerAdapter: LibraryViewPagerAdapter by lazy {
        LibraryViewPagerAdapter()
    }
    private val viewModel: LibraryViewModel by viewModels{
        LibraryViewModel.Factory
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLibraryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewPagerAndTabs()
        observeReadState()
        observeCurrentNovels()
        viewModel.getNovels()
    }

    private fun setupViewPagerAndTabs() {
        val viewPager = binding.vpLibraryNovel
        viewPager.adapter = viewPagerAdapter

        TabLayoutMediator(binding.tbLibrary, viewPager) { tab, position ->
            tab.text = getTabTitle(position)
          }.attach()


        binding.tbLibrary.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val readState : ReadState = when (tab.position) {
                    0 -> ReadState.ALL
                    1 -> ReadState.FINISH
                    2 -> ReadState.READING
                    3 -> ReadState.DROP
                    4 -> ReadState.WISH
                    else -> ReadState.ALL
                }
                viewModel.setReadState(readState)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
            }

            override fun onTabReselected(tab: TabLayout.Tab) {
            }
        })

        binding.tbLibrary

    }

    private fun observeReadState() {
        viewModel.readState.observe(viewLifecycleOwner) { readState ->
            Toast.makeText(requireContext(), readState.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    private fun observeCurrentNovels() {
        viewModel.currentUserNovels.observe(viewLifecycleOwner) { currentUserNovels ->
            currentUserNovels?.let {
                viewPagerAdapter.fetchUserNovels(it)
            }
        }
    }

    private fun getTabTitle(position: Int): String {
        return when (position) {
            0 -> getString(R.string.library_tb_item_all)
            1 -> getString(R.string.library_tb_item_finish)
            2 -> getString(R.string.library_tb_item_reading)
            3 -> getString(R.string.library_tb_item_drop)
            4 -> getString(R.string.library_tb_item_wish)
            else -> ""
        }
    }

    companion object {
        val newInstance = LibraryFragment()
    }
}