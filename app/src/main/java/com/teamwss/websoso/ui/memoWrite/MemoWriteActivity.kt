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
        Log.d("memoId", memoId.toString())
    }

    private fun setTranslucentOnStatusBar() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
    }

    companion object {
        fun createIntent(context: Context, memoId: Long): Intent {
            return Intent(context, MemoWriteActivity::class.java).apply {
                putExtra("memoId", memoId)
            }
        }

        fun createNewMemoIntent(context: Context): Intent {
            return Intent(context, MemoWriteActivity::class.java)
        }
    }
}