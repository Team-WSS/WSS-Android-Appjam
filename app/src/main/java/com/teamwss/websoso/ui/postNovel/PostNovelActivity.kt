package com.teamwss.websoso.ui.postNovel

import android.graphics.Color
import android.os.Bundle
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.AppBarLayout
import com.teamwss.websoso.databinding.ActivityPostNovelBinding
import kotlin.math.pow

class PostNovelActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPostNovelBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostNovelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAppBar()
        setupNavigateLeftDialog()
    }

    private fun setupAppBar() {
        val scrollView: ScrollView = binding.svPost
        val appBarLayout: AppBarLayout = binding.alPostAppBar
        val titleView: TextView = binding.tvPostTitle

        scrollView.viewTreeObserver.addOnScrollChangedListener {
            val scrollY = scrollView.scrollY
            val maxHeight = binding.ivPostCoverBackground.height - appBarLayout.height
            val scrollRatio = (scrollY.toFloat() / maxHeight).coerceAtMost(1f).pow(3 / 2)
            val colorAlpha = (scrollRatio * 255).toInt()

            appBarLayout.setBackgroundColor(Color.argb(colorAlpha, 255, 255, 255))
            titleView.setTextColor(Color.argb(colorAlpha, 0, 0, 0))
        }
    }


    private fun setupNavigateLeftDialog() {
        binding.ivPostNavigateLeft.setOnClickListener {
            val dialog = PostNavigateLeftDialog(this)

            dialog.setItemClickListener(object : PostNavigateLeftDialog.ItemClickListener {
                override fun onExitButtonClick() {
                    finish()
                }

                override fun onKeepButtonClick() {
                    binding.vPostDialogBackground.visibility = android.view.View.INVISIBLE
                }
            })
            binding.vPostDialogBackground.visibility = android.view.View.VISIBLE
            dialog.show()
        }
    }
}