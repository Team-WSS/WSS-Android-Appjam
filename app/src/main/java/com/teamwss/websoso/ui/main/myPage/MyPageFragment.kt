package com.teamwss.websoso.ui.main.myPage

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import com.teamwss.websoso.R
import com.teamwss.websoso.databinding.FragmentMyPageBinding
import com.teamwss.websoso.ui.main.MainActivity
import com.teamwss.websoso.ui.main.library.LibraryFragment
import com.teamwss.websoso.ui.main.myPage.changeNickName.ChangeNicknameActivity
import com.teamwss.websoso.ui.main.myPage.checkUserInfo.CheckUserInfoActivity
import com.teamwss.websoso.ui.main.record.RecordFragment

class MyPageFragment : Fragment() {
    private lateinit var binding: FragmentMyPageBinding
    private val myPageViewModel: MyPageViewModel by viewModels {
        MyPageViewModel.Factory
    }
    private val avatarAdapter: MyPageAdapter by lazy {
        MyPageAdapter(::showAvatarDialog)
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
        setupLinkToWebClickListener()
        setupRegistrationNovelLayoutClickListener()
        setupMemoLayoutClickListener()
        observeUserAvatar()
    }

    override fun onResume() {
        super.onResume()
        myPageViewModel.getMyPageUserInfo()
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
        binding.viewMyPageUserInfoButton.setOnClickListener {
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

    private fun setupLinkToWebClickListener() {
        binding.viewMyPageInstagramButton.setOnClickListener {
            openUrl(INSTAGRAM_URL)
        }

        binding.viewMyPageTermsOfUseButton.setOnClickListener {
            openUrl(TERMS_OF_USE_URL)
        }
    }

    private fun setupRegistrationNovelLayoutClickListener() {
        binding.clMyPageRegistrationNovel.setOnClickListener {
            navigateToFragment(LibraryFragment.newInstance())
        }
    }

    private fun setupMemoLayoutClickListener() {
        binding.clMyPageMemo.setOnClickListener {
            navigateToFragment(RecordFragment.newInstance())
        }
    }

    private fun navigateToFragment(fragment: Fragment) {
        parentFragmentManager.commit {
            replace(R.id.fcvMain, fragment)
        }
        updateBottomNavigationForFragment(fragment)
    }

    private fun updateBottomNavigationForFragment(fragment: Fragment) {
        val mainActivity = requireActivity() as MainActivity

        when (fragment) {
            is LibraryFragment -> {
                mainActivity.updateBottomNavigation(R.id.menu_library)
            }

            is RecordFragment -> {
                mainActivity.updateBottomNavigation(R.id.menu_record)
            }
        }
    }

    private fun openUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
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

        private const val INSTAGRAM_URL =
            "https://www.instagram.com/websoso_official?utm_source=ig_web_button_share_sheet&igsh=ZDNlZDc0MzIxNw=="
        private const val TERMS_OF_USE_URL =
            "https://www.notion.so/kimmjabc/4acd397608c146cbbf8dd4fe11a82e19"
    }
}