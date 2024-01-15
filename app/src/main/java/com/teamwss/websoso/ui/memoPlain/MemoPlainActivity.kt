package com.teamwss.websoso.ui.memoPlain

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.teamwss.websoso.databinding.ActivityMemoPlainBinding
import com.teamwss.websoso.ui.memoWrite.MemoWriteActivity
import kotlin.properties.Delegates

class MemoPlainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMemoPlainBinding
    private val memoPlainViewModel: MemoPlainViewModel by viewModels()
    private var memoId by Delegates.notNull<Long>()
    private val dialogMemoDelete: DialogMemoDelete by lazy {
        DialogMemoDelete(::finish)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMemoPlainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        memoId = intent.getLongExtra("memoId", -1)
        memoPlainViewModel.updateMemoId(memoId)
        memoPlainViewModel.getMemo(memoId)

        setTranslucentOnStatusBar()
        setupLifecycleOwner()
        setupDataBinding()
        observeMemoId()
        onClickMemoPlainCancelButton()
        onClickMemoDeleteButton()
        onClickMemoEditButton()
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

    private fun observeMemoId() {
        memoPlainViewModel.memoId.observe(this) {
            memoId = memoPlainViewModel.memoId.value!!
        }
    }

    private fun onClickMemoPlainCancelButton() {
        binding.ivMemoPlainCancelBtn.setOnClickListener {
            finish()
        }
    }

    private fun onClickMemoDeleteButton() {
        binding.ivMemoPlainDeleteBtn.setOnClickListener {
            showMemoDeleteDialog()
        }
    }

    private fun showMemoDeleteDialog() {
        dialogMemoDelete.show((supportFragmentManager), "DeleteMemoDialog")
    }

    private fun onClickMemoEditButton() {
        binding.tvMemoPlainEditBtn.setOnClickListener {
            val intent = MemoWriteActivity.createIntent(this, memoId)
            startActivity(intent)
            finish()
        }
    }

    companion object {
        fun createIntent(context: Context, memoId: Long): Intent {
            return Intent(context, MemoPlainActivity::class.java).apply {
                putExtra("memoId", memoId)
            }
        }
    }
}