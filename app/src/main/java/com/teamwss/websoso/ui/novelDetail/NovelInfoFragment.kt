package com.teamwss.websoso.ui.novelDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.teamwss.websoso.databinding.FragmentNovelInfoBinding

class NovelInfoFragment : Fragment() {
    private var _binding: FragmentNovelInfoBinding? = null
    private val binding: FragmentNovelInfoBinding
        get() = requireNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNovelInfoBinding.inflate(inflater, container, false)
        return binding.root
    }
}