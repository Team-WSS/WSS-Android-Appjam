package com.teamwss.websoso.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.teamwss.websoso.R
import com.teamwss.websoso.databinding.ActivityMainBinding
import com.teamwss.websoso.ui.main.home.HomeFragment
import com.teamwss.websoso.ui.main.library.LibraryFragment
import com.teamwss.websoso.ui.main.myPage.MyPageFragment
import com.teamwss.websoso.ui.main.record.RecordFragment

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initializeDefaultFragment(savedInstanceState)
        setTranslucentOnStatusBar()
        setupBottomNavigation()
        initFragmentPage()
    }

    private fun initFragmentPage() {
        val selectedMenuId = intent.getIntExtra(FRAGMENT_PAGE, R.id.menu_home)
        binding.bnvMain.selectedItemId = selectedMenuId
    }

    private fun setTranslucentOnStatusBar() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
    }

    private fun setupBottomNavigation() {
        binding.bnvMain.setOnItemSelectedListener { item ->
            val selectedFragment = createFragmentByMenuId(item.itemId)
            selectedFragment?.let { changeFragment(it) }
            return@setOnItemSelectedListener selectedFragment != null
        }
        binding.bnvMain.setOnItemReselectedListener { }
    }

    private fun createFragmentByMenuId(menuId: Int) = when (menuId) {
        R.id.menu_home -> HomeFragment.newInstance()
        R.id.menu_library -> LibraryFragment.newInstance()
        R.id.menu_record -> RecordFragment.newInstance()
        R.id.menu_my_page -> MyPageFragment.newInstance()
        else -> null
    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager.commit {
            replace(R.id.fcvMain, fragment)
        }
    }

    fun updateBottomNavigation(selectedItemId: Int) {
        binding.bnvMain.selectedItemId = selectedItemId
    }

    private fun initializeDefaultFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            val homeFragment = HomeFragment.newInstance()
            changeFragment(homeFragment)
        }
    }

    companion object {
        const val FRAGMENT_PAGE = "FRAGMENT_PAGE"
        fun newIntent(context: Context, page: Int): Intent {
            return Intent(context, MainActivity::class.java).apply {
                putExtra(FRAGMENT_PAGE, page)
            }
        }
    }
}