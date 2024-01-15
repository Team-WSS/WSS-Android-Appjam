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
import com.teamwss.websoso.R
import com.teamwss.websoso.databinding.FragmentMyPageBinding
import com.teamwss.websoso.ui.main.myPage.changeName.ChangeNameActivity
import com.teamwss.websoso.ui.main.myPage.checkUserInfo.CheckUserInfoActivity

class MyPageFragment : Fragment() {
    private lateinit var binding: FragmentMyPageBinding
    private lateinit var avatarAdapter: MyPageAdapter
    private val myPageViewModel: MyPageViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeUserName()
        setupAvatarRecyclerView()
        launchOnClick()
        setupAvatarDialog()
    }

    @SuppressLint("StringFormatMatches")
    private fun observeUserName() {
        myPageViewModel.userName.observe(viewLifecycleOwner) { userName ->
            binding.tvMyPageUserName.text = getString(R.string.my_page_user_name, userName)
        }
    }

    private fun setupAvatarRecyclerView() {
        initRecyclerView()
        setupAvatarList()
    }

    private fun initRecyclerView() {
        avatarAdapter = MyPageAdapter()
        binding.rvMyPageSelected.adapter = avatarAdapter
    }

    private fun setupAvatarList() {
        myPageViewModel.avatars.observe(viewLifecycleOwner) { avatarList ->
            avatarAdapter.updateAvatarList(avatarList)
        }
    }

    private fun launchOnClick() {
        launchChangeName()
        launchCheckUserName()
    }

    private fun launchChangeName() {
        binding.ivMyPageEditUserName.setOnClickListener {
            val defaultName = binding.tvMyPageUserName.text.toString()

            val userNameWithoutSuffix = defaultName.removeSuffix("님")

            val intent = createChangeNameIntent(binding.root.context, userNameWithoutSuffix)
            startActivity(intent)
        }
    }

    private fun launchCheckUserName() {
        binding.tvMyPageCheckUserInfo.setOnClickListener {
            val intent = Intent(binding.root.context, CheckUserInfoActivity::class.java)

            startActivity(intent)
        }
    }

    private fun setupAvatarDialog() {
        avatarAdapter.onAvatarItemClickListener { avatar ->
            showAvatarDialog()
        }
    }

    private fun showAvatarDialog() {
        val dialogFragment = AvatarDialogFragment.newInstance()
        dialogFragment.show(parentFragmentManager, AvatarDialogFragment.TAG)
    }

    companion object {
        fun newInstance() = MyPageFragment()
        fun createChangeNameIntent(context: Context, userName: String): Intent {
            val intent = Intent(context, ChangeNameActivity::class.java)
            intent.putExtra("userName", userName)
            return intent
        }
    }
}