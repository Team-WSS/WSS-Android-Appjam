package com.teamwss.websoso.ui.memoWrite

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.teamwss.websoso.databinding.ActivityMemoWriteBinding
import kotlin.properties.Delegates

class MemoWriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMemoWriteBinding
    private val memoWriteViewModel: MemoWriteViewModel by viewModels()

    private var memoId by Delegates.notNull<Long>()
    private var userNovelId by Delegates.notNull<Long>()
    private lateinit var userNovelTitle: String
    private lateinit var userNovelAuthor: String
    private lateinit var userNovelImage: String

    private var memoContent: String? = null

    private val dialogMemoCancel: DialogMemoCancel by lazy {
        DialogMemoCancel(::finish)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMemoWriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
        getUserNovelDataFromBeforeView()
        updateUserNovelToViewModel()
        updateMemoContentToViewModel()
        observeMemoContent()
        onClickBackButton()
        observePostMemoSuccess()
        observePatchedMemoSuccess()

        if (userNovelId != -1L) {
            clickListenerPostMemo(userNovelId)
        }

        if (memoId != -1L) {
            clickListenerPatchMemo(memoId)
        }
    }

    private fun setupUI() {
        setTranslucentOnStatusBar()
        binding.lifecycleOwner = this
        binding.memoWriteViewModel = memoWriteViewModel
    }

    private fun setTranslucentOnStatusBar() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
    }

    private fun getUserNovelDataFromBeforeView() {
        memoId = intent.getLongExtra("memoId", -1)
        userNovelId = intent.getLongExtra("userNovelId", -1)
        memoContent = intent.getStringExtra("memoContent").toString()
        userNovelTitle = intent.getStringExtra("userNovelTitle").toString()
        userNovelAuthor = intent.getStringExtra("userNovelAuthor").toString()
        userNovelImage = intent.getStringExtra("userNovelImage").toString()
    }

    private fun updateUserNovelToViewModel() {
        memoWriteViewModel.updateUserNovelData(
            memoId,
            userNovelId,
            userNovelTitle,
            userNovelAuthor,
            userNovelImage
        )
    }

    private fun updateMemoContentToViewModel() {
        if (memoId != -1L) {
            memoWriteViewModel.getMemoContent(memoContent!!)
        }
    }

    private fun observeMemoContent() {
        memoWriteViewModel.memoContent.observe(this) {
            memoContent = memoWriteViewModel.memoContent.value
        }
    }

    private fun onClickBackButton() {
        binding.ivMemoWriteCancelBtn.setOnClickListener {
            if (validateMemoContent(memoContent)) {
                dialogMemoCancel.show((supportFragmentManager), "memoCancelDialog")
            } else {
                finish()
            }
        }
    }

    private fun validateMemoContent(memoContent: String?): Boolean = memoContent?.isBlank() != true

    private fun clickListenerPostMemo(userNovelId: Long) {
        binding.tvMemoWriteCompleteBtn.setOnClickListener {
            memoWriteViewModel.postMemo(userNovelId)
        }
    }

    private fun observePostMemoSuccess() {
        memoWriteViewModel.isMemoPosted.observe(this) { isPosted ->
            if (isPosted) {
                setResult(Activity.RESULT_OK)
                finish()
            } else {
                Snackbar.make(
                    binding.root,
                    "메모 저장 실패",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun observePatchedMemoSuccess() {
        memoWriteViewModel.isMemoPatched.observe(this) { isPatched ->
            if (isPatched) {
                setResult(Activity.RESULT_OK)
                finish()
            } else {
                Snackbar.make(
                    binding.root,
                    "메모 저장에 실패했어요",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun clickListenerPatchMemo(memoId: Long) {
        binding.tvMemoWriteCompleteBtn.setOnClickListener {
            memoWriteViewModel.patchMemo(memoId)
        }
    }

    companion object {
        fun newIntentFromPlain(
            context: Context,
            memoId: Long,
            memoContent: String,
            userNovelTitle: String,
            userNovelAuthor: String,
            userNovelImage: String
        ): Intent {
            return Intent(context, MemoWriteActivity::class.java).apply {
                putExtra("memoId", memoId)
                putExtra("memoContent", memoContent)
                putExtra("userNovelTitle", userNovelTitle)
                putExtra("userNovelAuthor", userNovelAuthor)
                putExtra("userNovelImage", userNovelImage)
            }
        }

        fun newIntentFromDetail(
            context: Context,
            userNovelId: Long,
            userNovelTitle: String,
            userNovelAuthor: String,
            userNovelImage: String
        ): Intent {
            return Intent(context, MemoWriteActivity::class.java).apply {
                putExtra("userNovelId", userNovelId)
                putExtra("userNovelTitle", userNovelTitle)
                putExtra("userNovelAuthor", userNovelAuthor)
                putExtra("userNovelImage", userNovelImage)
            }
        }
    }
}