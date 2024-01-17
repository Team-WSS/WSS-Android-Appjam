package com.teamwss.websoso.ui.main.myPage

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.teamwss.websoso.databinding.FragmentMyPageBinding
import com.teamwss.websoso.ui.main.myPage.changeNickName.ChangeNicknameActivity
import com.teamwss.websoso.ui.main.myPage.checkUserInfo.CheckUserInfoActivity

class MyPageFragment : Fragment() {
    private lateinit var binding: FragmentMyPageBinding
    private val myPageViewModel: MyPageViewModel by viewModels {
        MyPageViewModel.Factory
    }
    private val avatarAdapter: MyPageAdapter by lazy {
        MyPageAdapter(::showAvatarDialog, myPageViewModel.userInfo.value?.representativeAvatarId ?: 1)
    }
    private lateinit var changeNicknameActivityResultLauncher: ActivityResultLauncher<Intent>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = myPageViewModel

        setupChangeNicknameActivityResultLauncher()
        initRecyclerView()
        setupEditButtonClickListener()
        setupCheckUserInfoButtonClickListener()
        observeUserAvatar()
    }

    private fun setupChangeNicknameActivityResultLauncher() {
        changeNicknameActivityResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == AppCompatActivity.RESULT_OK) {
                    myPageViewModel.getMyPageUserInfo()
                }
            }
    }

    private fun initRecyclerView() {
        binding.rvMyPageSelected.adapter = avatarAdapter
    }

    private fun setupEditButtonClickListener() {
        binding.ivMyPageEditUserName.setOnClickListener {
            navigateToChangeNameActivity()
        }
    }

    private fun navigateToChangeNameActivity() {
        val intent = ChangeNicknameActivity.createIntent(
            requireContext(),
            myPageViewModel.userInfo.value?.userNickName ?: ""
        )
        changeNicknameActivityResultLauncher.launch(intent)
    }

    private fun setupCheckUserInfoButtonClickListener() {
        binding.tvMyPageCheckUserInfo.setOnClickListener {
            navigateToCheckUserInfoActivity()
        }
    }

    private fun navigateToCheckUserInfoActivity() {
        val intent = CheckUserInfoActivity.createIntent(
            requireContext(),
            myPageViewModel.userInfo.value?.userNickName ?: ""
        )
        startActivity(intent)
    }

    private fun observeUserAvatar() {
        myPageViewModel.avatars.observe(viewLifecycleOwner) { avatars ->
            avatarAdapter.updateAvatarList(avatars)
        }
    }

    private fun showAvatarDialog(id: Long) {
        myPageViewModel.getAvatar(id)
        val dialogFragment = AvatarDialogFragment.newInstance()
        dialogFragment.show(childFragmentManager, AvatarDialogFragment.TAG)
    }

    companion object {
        fun newInstance() = MyPageFragment()
    }
}