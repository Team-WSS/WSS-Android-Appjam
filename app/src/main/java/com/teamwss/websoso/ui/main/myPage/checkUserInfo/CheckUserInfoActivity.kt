package com.teamwss.websoso.ui.main.myPage.checkUserInfo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.teamwss.websoso.databinding.ActivityCheckUserInfoBinding
import com.teamwss.websoso.ui.main.myPage.MyPageFragment

class CheckUserInfoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCheckUserInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckUserInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setTranslucentOnStatusBar()
        launchBackMyPageOnClick()
    }

    private fun setTranslucentOnStatusBar() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
    }

    private fun launchBackMyPageOnClick() {
        binding.ivCheckUserInfoBack.setOnClickListener {
            try {
                Intent(binding.root.context, MyPageFragment::class.java)
                finish()
            } catch (e: Exception) {

                Log.e("error", "이동실패", e)

            }
        }

    }
}