package com.teamwss.websoso.ui.memoPlain

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.teamwss.websoso.databinding.ActivityMemoPlainBinding

class MemoPlainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMemoPlainBinding

    private val dialogMemoCancel: DialogMemoCancel by lazy {
        DialogMemoCancel(::finish)
    }
    private val memoPlainViewModel: MemoPlainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMemoPlainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setTranslucentOnStatusBar()
        setupLifecycleOwner()
        setupDataBinding()
        onClickMemoEditCancelButton()

        val memoId: Long = 11
        memoPlainViewModel.getMemo(memoId)
    }

    private fun setTranslucentOnStatusBar() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
    }

    private fun setupLifecycleOwner() {
        binding.lifecycleOwner = this
    }

    private fun setupDataBinding() {
        binding.memoPlainViewModel = memoPlainViewModel
    }

    private fun onClickMemoEditCancelButton() {
        binding.ivMemoEditCancelBtn.setOnClickListener {
            showMemoCancelDialog()
        }
    }

    private fun showMemoCancelDialog() {
        dialogMemoCancel.show((supportFragmentManager), "CancelEditMemoDialog")
    }

    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, MemoPlainActivity::class.java)
        }
    }
}