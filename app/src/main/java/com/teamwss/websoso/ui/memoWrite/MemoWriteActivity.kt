package com.teamwss.websoso.ui.memoWrite

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.teamwss.websoso.databinding.ActivityMemoWriteBinding

class MemoWriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMemoWriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMemoWriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setTranslucentOnStatusBar()

        val memoId = intent.getLongExtra("memoId", -1)
        val userNovelTitle = intent.getStringExtra("userNovelTitle")
        Log.d("userData", userNovelTitle.toString())
        val userNovelAuthor = intent.getStringExtra("userNovelAuthor")
        Log.d("userData", userNovelAuthor.toString())
        val userNovelId = intent.getLongExtra("userNovelId", -1)
        Log.d("userData", userNovelId.toString())
    }

    private fun setTranslucentOnStatusBar() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
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