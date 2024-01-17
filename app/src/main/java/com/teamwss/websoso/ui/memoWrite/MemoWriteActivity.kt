package com.teamwss.websoso.ui.memoWrite

import CustomSnackBar
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.teamwss.websoso.R
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
        showKeyboardOnEditTextFocus()
        getUserNovelDataFromBeforeView()
        updateUserNovelToViewModel()
        updateMemoContentToViewModel()
        observeMemoContent()
        observeMemoContentIsChanged()
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

    private fun showKeyboardOnEditTextFocus() {
        val searchKeyboard = binding.etMemoWriteContent
        searchKeyboard.requestFocus()

        binding.etMemoWriteContent.post {
            binding.etMemoWriteContent.setSelection(binding.etMemoWriteContent.text.length)
        }

        val inputMethodManager =
            this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(binding.etMemoWriteContent, 0)


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

    private fun observeMemoContentIsChanged() {
        memoWriteViewModel.isChanged.observe(this) { isChanged ->
            if (isChanged) {
                binding.tvMemoWriteCompleteBtn.isEnabled = true
                binding.tvMemoWriteCompleteBtn.setTextColor(
                    ContextCompat.getColor(
                        this,
                        R.color.primary_100_6341F0
                    )
                )
            }
        }
    }

    private fun onClickBackButton() {
        binding.ivMemoWriteCancelBtn.setOnClickListener {
            memoWriteViewModel.isChanged.observe(this) { isChanged ->
                if (isChanged) {
                    dialogMemoCancel.show((supportFragmentManager), "memoCancelDialog")
                } else {
                    finish()
                }
            }
        }
    }

    private fun clickListenerPostMemo(userNovelId: Long) {
        binding.tvMemoWriteCompleteBtn.setOnClickListener {
            memoWriteViewModel.postMemo(userNovelId)
        }
    }

    private fun observePostMemoSuccess() {
        memoWriteViewModel.isMemoPosted.observe(this) { isPosted ->
            if (isPosted) {
                memoWriteViewModel.isAvatarUnlocked.observe(this) { isAvatarUnlocked ->
                    val resultIntent = Intent().apply {
                        putExtra("isAvatarUnlocked", isAvatarUnlocked)
                        Log.d("unlocked", isAvatarUnlocked.toString())
                    }
                    setResult(Activity.RESULT_OK, resultIntent)
                    finish()
                }
            } else {
                val drawable =
                    ContextCompat.getDrawable(this, R.drawable.ic_alert_warning)
                CustomSnackBar.make(binding.root)
                    .setText("메모 저장에 실패했어요")
                    .setIcon(
                        drawable ?: ContextCompat.getDrawable(
                            this,
                            R.drawable.ic_alert_warning
                        )!!
                    )
                    .show()
            }
        }
    }

    private fun observePatchedMemoSuccess() {
        memoWriteViewModel.isMemoPatched.observe(this) { isPatched ->
            if (isPatched) {
                setResult(Activity.RESULT_OK)
                finish()
            } else {
                val drawable =
                    ContextCompat.getDrawable(this, R.drawable.ic_alert_warning)
                CustomSnackBar.make(binding.root)
                    .setText("메모 저장에 실패했어요")
                    .setIcon(
                        drawable ?: ContextCompat.getDrawable(
                            this,
                            R.drawable.ic_alert_warning
                        )!!
                    )
                    .show()
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