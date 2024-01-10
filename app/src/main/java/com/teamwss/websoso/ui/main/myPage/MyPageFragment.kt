package com.teamwss.websoso.ui.main.myPage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.teamwss.websoso.databinding.FragmentMyPageBinding

class MyPageFragment : Fragment() {
    private lateinit var binding: FragmentMyPageBinding
    private lateinit var avatarAdapter: MyPageAdapter
    private lateinit var viewModel: MyPageViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyPageBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(MyPageViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupAvatarList()
    }

    private fun setupRecyclerView() {
        avatarAdapter = MyPageAdapter()
        binding.rvMyPageSelected.adapter = avatarAdapter
    }

    private fun setupAvatarList() {
        viewModel.avatarData.observe(viewLifecycleOwner) { avatarList ->
            avatarAdapter.setAvatarList(avatarList)
        }
    }

    companion object {
        fun newInstance() = MyPageFragment()
    }
}