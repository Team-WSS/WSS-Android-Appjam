package com.teamwss.websoso.ui.novelDetail.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.teamwss.websoso.databinding.FragmentNovelInfoBinding
import com.teamwss.websoso.ui.novelDetail.NovelDetailViewModel

class NovelInfoFragment : Fragment() {
    private var _binding: FragmentNovelInfoBinding? = null
    private val binding: FragmentNovelInfoBinding
        get() = requireNotNull(_binding)
    private val novelDetailViewModel: NovelDetailViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNovelInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupLifecycleOwner()
        setupDataBinding()
    }

    private fun setupLifecycleOwner() {
        binding.lifecycleOwner = viewLifecycleOwner
    }

    private fun setupDataBinding() {
        binding.novelDetailViewModel = novelDetailViewModel
    }

    override fun onResume() {
        super.onResume()

        view?.requestLayout()
    }
}