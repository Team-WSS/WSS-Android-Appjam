package com.teamwss.websoso.ui.view.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.teamwss.websoso.R
import com.teamwss.websoso.databinding.ActivityMainBinding
import com.teamwss.websoso.ui.view.main.home.HomeFragment
import com.teamwss.websoso.ui.view.main.library.LibraryFragment
import com.teamwss.websoso.ui.view.main.mypage.MyPageFragment
import com.teamwss.websoso.ui.view.main.recode.RecodeFragment

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupBottomNavigation()
        initializeDefaultFragment(savedInstanceState)
    }

    private fun setupBottomNavigation() {
        binding.bnvMain.setOnItemSelectedListener { item ->
            val selectedFragment = when (item.itemId) {
                R.id.menu_home -> HomeFragment.newInstance()
                R.id.menu_library -> LibraryFragment.newInstance()
                R.id.menu_recode -> RecodeFragment.newInstance()
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
            replace(R.id.bnvMain, fragment)
        }
    }

    private fun initializeDefaultFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            val homeFragment = HomeFragment.newInstance()
            changeFragment(homeFragment)
        }
    }

}