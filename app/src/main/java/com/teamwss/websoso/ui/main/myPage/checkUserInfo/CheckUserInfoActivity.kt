package com.teamwss.websoso.ui.main.myPage.checkUserInfo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.teamwss.websoso.databinding.ActivityCheckUserInfoBinding

class CheckUserInfoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCheckUserInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckUserInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setTranslucentOnStatusBar()
        setupUI()
        navigateToMainActivity()
    }

    private fun setTranslucentOnStatusBar() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
    }

    private fun setupUI() {
        val userName = intent.getStringExtra(USER_NAME)
        binding.tvCheckUserInfoNickName.text = userName
    }

    private fun navigateToMainActivity() {
        binding.ivCheckUserInfoBack.setOnClickListener {
            finish()
        }
    }

    companion object {
        const val USER_NAME = "userName"

        fun createIntent(context: Context, userName: String): Intent {
            return Intent(context, CheckUserInfoActivity::class.java).apply {
                putExtra(USER_NAME, userName)
            }
        }
    }
}