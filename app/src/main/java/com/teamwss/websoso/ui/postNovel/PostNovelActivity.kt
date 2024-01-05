package com.teamwss.websoso.ui.postNovel

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.widget.ScrollView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.AppBarLayout
import com.teamwss.websoso.databinding.ActivityPostNovelBinding

class PostNovelActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPostNovelBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostNovelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAppBarHeight()
        setupAppBar()
        setupNavigateLeftDialog()
    }

    private fun setupAppBarHeight() {
        val densityOfDisplay = resources.displayMetrics.density
        val defaultAppBarHeight = (52 * densityOfDisplay).toInt()

        val appBarLayout: AppBarLayout = binding.alPostAppBar
        val params = appBarLayout.layoutParams
        params.height = defaultAppBarHeight + getActionBarHeight()
        appBarLayout.layoutParams = params
    }

    @SuppressLint("DiscouragedApi", "InternalInsetResource")
    private fun getActionBarHeight(): Int {
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        val statusBarHeight = if (resourceId > 0) {
            resources.getDimensionPixelSize(resourceId)
        } else 0
        return statusBarHeight
    }

    private fun setupAppBar() {
        val scrollView: ScrollView = binding.svPost
        val appBarLayout: AppBarLayout = binding.alPostAppBar

        scrollView.viewTreeObserver.addOnScrollChangedListener {
            val scrollY = scrollView.scrollY
            val scrollRatio = scrollY.toFloat() / appBarLayout.height
            val colorAlpha = 255.coerceAtMost((scrollRatio * 255).toInt())

            appBarLayout.setBackgroundColor(Color.argb(colorAlpha, 255, 255, 255))
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