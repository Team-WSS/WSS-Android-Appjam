package com.teamwss.websoso.ui.main.myPage.changeNickName

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.teamwss.websoso.R
import com.teamwss.websoso.databinding.ActivityChangeNicknameBinding

class ChangeNicknameActivity : AppCompatActivity() {
    private val binding: ActivityChangeNicknameBinding by lazy {
        ActivityChangeNicknameBinding.inflate(layoutInflater)
    }
    private val changeNicknameViewModel: ChangeNicknameViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.viewModel = changeNicknameViewModel
        binding.lifecycleOwner = this

        setTranslucentOnStatusBar()
        getStringExtra()
        setupBackButtonClickListener()
        setupCompleteTextClickListener()
        setupFocusChangeListener()
        setupClearButtonClickListener()
        observePatchSuccess()
        observeUserName()
    }

    private fun setTranslucentOnStatusBar() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
    }

    private fun getStringExtra() {
        val userName = intent.getStringExtra(USER_NAME) ?: ""
        changeNicknameViewModel.setUserNickname(userName)
    }

    private fun setupBackButtonClickListener() {
        binding.ivChangeNicknameBack.setOnClickListener {
            finish()
        }
    }

    private fun setupCompleteTextClickListener() {
        binding.tvChangeNicknameComplete.setOnClickListener {
            changeNicknameViewModel.patchUserNickName()
        }
    }

    private fun setupClearButtonClickListener() {
        binding.ivChangeNicknameCancel.setOnClickListener {
            changeNicknameViewModel.setUserNickname("")
        }
    }

    private fun setupFocusChangeListener() {
        binding.etChangeNickname.setOnFocusChangeListener { _, hasFocus ->
            setEditTextBackgroundFocusTintColor(binding.etChangeNickname, hasFocus)
        }
    }

    private fun setEditTextBackgroundFocusTintColor(editText: EditText, hasFocus: Boolean) {
        val backgroundColor = if (hasFocus) R.color.primary_100_6341F0 else R.color.gray_200_AEADB3
        editText.background.setTint(ContextCompat.getColor(this, backgroundColor))
    }

    private fun observePatchSuccess() {
        changeNicknameViewModel.patchSuccess.observe(this) {
            when (it) {
                true -> finish()
                false -> {
                    Toast.makeText(this, "닉네임 변경에 실패했습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun observeUserName() {
        changeNicknameViewModel.userNickname.observe(this) {
            if (it.isNotBlank()) {
                binding.tvChangeNicknameComplete.setTextColor(
                    ContextCompat.getColor(
                        this,
                        R.color.primary_100_6341F0
                    )
                )
            } else {
                binding.tvChangeNicknameComplete.setTextColor(
                    ContextCompat.getColor(
                        this,
                        R.color.gray_200_AEADB3
                    )
                )
            }
        }
    }

    companion object {
        private const val USER_NAME = "userName"

        fun createIntent(context: Context, userName: String): Intent {
            return Intent(context, ChangeNicknameActivity::class.java).apply {
                putExtra(USER_NAME, userName)
            }
        }
    }
}

