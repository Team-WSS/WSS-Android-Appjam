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
    private lateinit var userNovelTitle: String
    private lateinit var userNovelAuthor: String
    private lateinit var userNovelImage: String
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
        getUserNovelDataToIntent()
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

    private fun getUserNovelDataToIntent() {
        userNovelImage = intent.getStringExtra("userNovelImg").toString()
        userNovelTitle = intent.getStringExtra("userNovelTitle").toString()
        userNovelAuthor = intent.getStringExtra("userNovelAuthor").toString()
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
            val intent = MemoWriteActivity.createIntent(
                this,
                memoId,
                userNovelTitle,
                userNovelAuthor,
                userNovelImage
            )
            startActivity(intent)
            finish()
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
            return Intent(context, MemoPlainActivity::class.java).apply {
                putExtra("memoId", memoId)
                putExtra("userNovelTitle", userNovelTitle)
                putExtra("userNovelAuthor", userNovelAuthor)
                putExtra("userNovelImg", userNovelImg)
            }
        }
    }
}