package com.teamwss.websoso.ui.memoWrite

import android.os.Bundle
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
        clickMemoEditCancelBtn()
    }

    private fun setTranslucentOnStatusBar() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
    }

    private fun clickMemoEditCancelBtn() {
        binding.btnMemoEditCancel.setOnClickListener {
            showMemoCancelDialog()
        }
    }

    private fun showMemoCancelDialog() {
        val dialog = DialogMemoCancel(clickExit = {
            finish()
        })
        dialog.show((supportFragmentManager), "CancelEditMemoDialog")
    }
}