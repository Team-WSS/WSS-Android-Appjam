package com.teamwss.websoso.ui.memoWrite

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.teamwss.websoso.databinding.ActivityMemoWriteBinding

class MemoWriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMemoWriteBinding
    private val memoWriteViewModel: MemoWriteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMemoWriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setTranslucentOnStatusBar()

        val memoId = intent.getLongExtra("memoId", -1)
        val userNovelTitle = intent.getStringExtra("userNovelTitle")
        val userNovelAuthor = intent.getStringExtra("userNovelAuthor")
        val userNovelId = intent.getLongExtra("userNovelId", -1)

        if (userNovelId != -1L) {
            clickListener(userNovelId)
        }

        if(memoId != -1L) {
            clickListenerPatch(memoId)
        }
    }

    private fun setTranslucentOnStatusBar() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
    }

    private fun clickListener(userNovelId: Long) {
        binding.tvMemoWriteCompleteBtn.setOnClickListener {
            memoWriteViewModel.postMemo(userNovelId)
        }
    }

    private fun clickListenerPatch(memoId: Long) {
        binding.tvMemoWriteCompleteBtn.setOnClickListener {
            memoWriteViewModel.patchMemo(memoId)
        }
    }

    companion object {
        fun createIntent(
            context: Context,
            memoId: Long,
            userNovelTitle: String,
            userNovelAuthor: String,
            userNovelImg: String
        ): Intent {
            return Intent(context, MemoWriteActivity::class.java).apply {
                putExtra("memoId", memoId)
                putExtra("userNovelTitle", userNovelTitle)
                putExtra("userNovelAuthor", userNovelAuthor)
                putExtra("userNovelImg", userNovelImg)
            }
        }

        fun createNewMemoIntent(
            context: Context,
            userNovelId: Long,
            userNovelTitle: String,
            userNovelAuthor: String,
            userNovelImg: String
        ): Intent {
            return Intent(context, MemoWriteActivity::class.java).apply {
                putExtra("userNovelId", userNovelId)
                putExtra("userNovelTitle", userNovelTitle)
                putExtra("userNovelAuthor", userNovelAuthor)
                putExtra("userNovelImg", userNovelImg)
            }
        }
    }
}