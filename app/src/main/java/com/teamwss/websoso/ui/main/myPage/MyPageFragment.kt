package com.teamwss.websoso.ui.main.myPage

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.teamwss.websoso.R
import com.teamwss.websoso.databinding.FragmentMyPageBinding
import com.teamwss.websoso.ui.main.myPage.changeName.ChangeNameActivity
import com.teamwss.websoso.ui.main.myPage.checkUserInfo.CheckUserInfoActivity
import com.teamwss.websoso.ui.main.myPage.model.Avatar

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
        observeUserName()
        setupAvatarRecyclerView()
        launchOnClick()
        setupAvatarDialog()
    }

    @SuppressLint("StringFormatMatches")
    private fun observeUserName() {
        viewModel.userName.observe(viewLifecycleOwner) { userName ->
            val displayText = getString(R.string.my_page_user_name, userName)
            binding.tvMyPageUserName.text = displayText
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
        viewModel.avatarData.observe(viewLifecycleOwner) { avatarList ->
            avatarAdapter.setAvatarList(avatarList)
        }
    }

    private fun launchOnClick() {
        launchChangeNameOnClick()
        launchCheckUserNameOnClick()
    }

    private fun launchChangeNameOnClick() {
        binding.ivMyPageEditUserName.setOnClickListener {
            val defaultName = binding.tvMyPageUserName.text.toString()

            val userNameWithoutSuffix = defaultName.removeSuffix("님")

            val intent = Intent(binding.root.context, ChangeNameActivity::class.java)
            intent.putExtra("userName", userNameWithoutSuffix)

            try {
                startActivity(intent)
            } catch (e: Exception) {
                Log.e("error", "이동실패", e)
            }
        }
    }

    private fun launchCheckUserNameOnClick() {
        binding.tvMyPageCheckUserInfo.setOnClickListener {
            val intent = Intent(binding.root.context, CheckUserInfoActivity::class.java)

            try {
                startActivity(intent)
            } catch (e: Exception) {
                Log.e("error", "이동실패", e)
            }
        }
    }

    private fun setupAvatarDialog() {
        avatarAdapter.onAvatarItemClickListener { avatar ->
            showAvatarDialog(avatar)
        }
    }

    private fun showAvatarDialog(avatar: Avatar) {
        val dialogFragment = AvatarDialogFragment.newInstance(avatar)
        dialogFragment.show(parentFragmentManager, AvatarDialogFragment.TAG)
    }

    companion object {
        fun newInstance() = MyPageFragment()
    }
}