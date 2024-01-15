package com.teamwss.websoso.ui.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.teamwss.websoso.databinding.FragmentHomeBinding
import com.teamwss.websoso.ui.main.home.adapter.HomeAdapter


class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = requireNotNull(_binding)
    private val homeViewModel: HomeViewModel by viewModels()
    private val homeAdapter: HomeAdapter by lazy { HomeAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updateAdapter()
        initRecyclerView()
    }

    private fun updateAdapter() {
        homeAdapter.submitList(homeViewModel.mockSosoPickData)
    }

    private fun initRecyclerView() {
        with(binding.rvHomeSosoPick) {
            adapter = homeAdapter
            addItemDecoration(CustomItemDecoration(requireContext()))
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    companion object {
        fun newInstance() = HomeFragment()
    }
}