package com.teamwss.websoso.ui.splash

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.teamwss.websoso.databinding.ActivitySplashBinding
import com.teamwss.websoso.ui.main.MainActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {
    private val binding: ActivitySplashBinding by lazy {
        ActivitySplashBinding.inflate(layoutInflater)
    }
    private val splashViewModel: SplashViewModel by viewModels {
        SplashViewModel.Factory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        splashViewModel.postLogin()

        lifecycleScope.launch {
            delay(1500L)
            navigateToMainActivity()
        }
    }

    private fun navigateToMainActivity() {
        val intent = MainActivity.newIntent(this)
        startActivity(intent)
        finish()
    }
}