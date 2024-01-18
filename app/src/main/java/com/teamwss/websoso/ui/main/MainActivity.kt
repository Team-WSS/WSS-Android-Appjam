package com.teamwss.websoso.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.teamwss.websoso.R
import com.teamwss.websoso.databinding.ActivityMainBinding
import com.teamwss.websoso.ui.main.home.HomeFragment
import com.teamwss.websoso.ui.main.library.LibraryFragment
import com.teamwss.websoso.ui.main.myPage.MyPageFragment
import com.teamwss.websoso.ui.main.record.RecordFragment
import com.teamwss.websoso.ui.postNovel.PostNovelActivity

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setTranslucentOnStatusBar()
        setupBottomNavigation()
        initializeDefaultFragment(savedInstanceState)
    }

    private fun setTranslucentOnStatusBar() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
    }

    private fun setupBottomNavigation() {
        binding.bnvMain.setOnItemSelectedListener { item ->
            val selectedFragment = when (item.itemId) {
                R.id.menu_home -> HomeFragment.newInstance()
                R.id.menu_library -> LibraryFragment.newInstance()
                R.id.menu_record -> RecordFragment.newInstance()
                R.id.menu_my_page -> MyPageFragment.newInstance()
                else -> null
            }
            selectedFragment?.let { changeFragment(it) }
            return@setOnItemSelectedListener selectedFragment != null
        }

        binding.bnvMain.setOnItemReselectedListener { }
    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager.commit {
            replace(R.id.fcvMain, fragment)
        }
    }

    private fun initializeDefaultFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            val homeFragment = HomeFragment.newInstance()
            changeFragment(homeFragment)
        }
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java).apply {
            }
        }
    }
}