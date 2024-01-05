package com.teamwss.websoso.ui.postNovel

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import com.google.android.material.appbar.AppBarLayout
import com.teamwss.websoso.databinding.ActivityPostNovelBinding

class PostNovelActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPostNovelBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostNovelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAppBar()
    }

    private fun setupAppBar() {
        val nestedScrollView: NestedScrollView = binding.svPost
        val appBarLayout: AppBarLayout = binding.alPostAppBar

        nestedScrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { _, _, scrollY, _, _ ->
            val scrollRatio = scrollY.toFloat() / appBarLayout.height
            val colorAlpha = 255.coerceAtMost((scrollRatio * 255).toInt())

            appBarLayout.setBackgroundColor(Color.argb(colorAlpha, 255, 255, 255))
        })
    }
}