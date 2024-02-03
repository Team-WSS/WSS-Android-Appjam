package com.teamwss.websoso.ui.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.teamwss.websoso.databinding.FragmentHomeBinding
import com.teamwss.websoso.ui.main.home.adapter.HomeAdapter
import com.teamwss.websoso.ui.postNovel.PostNovelActivity
import com.teamwss.websoso.ui.search.SearchActivity


class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = requireNotNull(_binding)
    private val homeViewModel: HomeViewModel by viewModels {
        HomeViewModel.Factory
    }
    private val homeAdapter: HomeAdapter by lazy {
        HomeAdapter(::navigateToPostNovel)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = homeViewModel

        setupRecyclerView()
        setupSearchBarListener()
        observeSosoPickNovels()
    }

    private fun setupRecyclerView() {
        with(binding.rvHomeSosoPick) {
            adapter = homeAdapter
            addItemDecoration(CustomHomeItemDecoration(requireContext()))
        }
    }

    private fun setupSearchBarListener() {
        binding.clHomeSearch.setOnClickListener {
            val intent = SearchActivity.newIntent(requireContext())
            startActivity(intent)
        }
    }

    private fun navigateToPostNovel(novelId: Long) {
        val intent = PostNovelActivity.newIntent(requireContext(), novelId)
        startActivity(intent)
    }

    private fun observeSosoPickNovels() {
        homeViewModel.sosopickNovels.observe(viewLifecycleOwner) { sosoPickNovels ->
            homeAdapter.submitList(sosoPickNovels)
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