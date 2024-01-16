package com.teamwss.websoso.ui.main.myPage

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import coil.load
import com.teamwss.websoso.R
import com.teamwss.websoso.databinding.FragmentMyPageBinding
import com.teamwss.websoso.ui.main.myPage.changeName.ChangeNameActivity
import com.teamwss.websoso.ui.main.myPage.checkUserInfo.CheckUserInfoActivity
import jp.wasabeef.transformers.coil.CenterCropTransformation

class MyPageFragment : Fragment() {
    private lateinit var binding: FragmentMyPageBinding
    private val myPageViewModel: MyPageViewModel by viewModels()
    private val avatarAdapter: MyPageAdapter by lazy {
        MyPageAdapter(::showAvatarDialog())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeUserInfo()
        observeRepresentiveAvatar()
        observeUserAvatar()
        initRecyclerView()
        launchOnClick()
    }

    @SuppressLint("StringFormatMatches")
    private fun observeUserInfo() {
        myPageViewModel.userName.observe(viewLifecycleOwner) { userName ->
            binding.tvMyPageUserName.text = getString(R.string.my_page_user_name, userName)
        }

        myPageViewModel.userNovelCount.observe(viewLifecycleOwner) { userNovelCount ->
            binding.tvMyPageRegistrationCount.text = userNovelCount.toString()
        }

        myPageViewModel.memoCount.observe(viewLifecycleOwner) { memoCount ->
            binding.tvMyPageMemoCount.text = memoCount.toString()
        }
    }

    private fun observeRepresentiveAvatar() {
        myPageViewModel.representativeAvatarTag.observe(viewLifecycleOwner) { representativeAvatarTag ->
            binding.tvMyPageAvatarName.text = representativeAvatarTag.toString()
        }

        myPageViewModel.representativeAvatarImg.observe(viewLifecycleOwner) { representativeAvatarImg ->
            binding.ivMyPageAvatar.load(representativeAvatarImg)
        }

        myPageViewModel.representativeAvatarLineContent.observe(viewLifecycleOwner) { representativeAvatarLineContent ->
            binding.tvMyPageAvatarTagline.text = representativeAvatarLineContent.toString()
        }

        binding.ivMyPageAvatar.load(myPageViewModel.representativeAvatarImg) {
            crossfade(true)
            transformations(CenterCropTransformation())
        }

        binding.ivMyPageGenreMark.load(myPageViewModel.representativeAvatarGenreBadge) {
            crossfade(true)
            transformations(CenterCropTransformation())
        }
    }


    private fun observeUserAvatar() {
        myPageViewModel.avatars.observe(viewLifecycleOwner) { avatars ->
            avatarAdapter.updateAvatarList(avatars)
        }
    }


    private fun initRecyclerView() {
        binding.rvMyPageSelected.adapter = avatarAdapter
    }

    private fun launchOnClick() {
        launchChangeName()
        launchCheckUserName()
    }

    private fun launchChangeName() {
        binding.ivMyPageEditUserName.setOnClickListener {
            val defaultName = binding.tvMyPageUserName.text.toString()

            val userNameWithoutSuffix = defaultName.removeSuffix("님")

            val intent = newChangeNameIntent(binding.root.context, userNameWithoutSuffix)
            startActivity(intent)
        }
    }

    private fun launchCheckUserName() {
        binding.tvMyPageCheckUserInfo.setOnClickListener {
            val intent = Intent(binding.root.context, CheckUserInfoActivity::class.java)

            startActivity(intent)
        }
    }

    private fun showAvatarDialog(id: Long) {
        myPageViewModel.getAvatar(id)
        val dialogFragment = AvatarDialogFragment.newInstance()
        dialogFragment.show(parentFragmentManager, AvatarDialogFragment.TAG)
    }


    companion object {
        fun newInstance() = MyPageFragment()
        fun newChangeNameIntent(context: Context, userName: String): Intent {
            val intent = Intent(context, ChangeNameActivity::class.java)
            intent.putExtra("userName", userName)
            return intent
        }
    }
}