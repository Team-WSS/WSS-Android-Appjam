package com.teamwss.websoso.ui.memoPlain

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.teamwss.websoso.databinding.ActivityMemoPlainBinding
import com.teamwss.websoso.ui.memoWrite.MemoWriteActivity
import kotlin.properties.Delegates

class MemoPlainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMemoPlainBinding
    private val memoPlainViewModel: MemoPlainViewModel by viewModels()
    private var memoId by Delegates.notNull<Long>()

    private lateinit var memoContent: String
    private lateinit var userNovelTitle: String
    private lateinit var userNovelAuthor: String
    private lateinit var userNovelImage: String
    private val dialogMemoDelete: DialogMemoDelete by lazy {
        DialogMemoDelete(::finish)
    }

    private lateinit var patchedMemoLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMemoPlainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        memoId = intent.getLongExtra("memoId", -1)
        memoPlainViewModel.updateMemoId(memoId)
        memoPlainViewModel.getMemo(memoId)

        patchedMemoLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    Snackbar.make(binding.root, "메모를 수정했어요", Snackbar.LENGTH_SHORT).show()
                }
            }

        setTranslucentOnStatusBar()
        setupLifecycleOwner()
        setupDataBinding()
        getUserNovelDataToIntent()
        observeMemoId()
        observeMemoContent()
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
        userNovelImage = intent.getStringExtra("userNovelImage").toString()
        userNovelTitle = intent.getStringExtra("userNovelTitle").toString()
        userNovelAuthor = intent.getStringExtra("userNovelAuthor").toString()
    }

    private fun observeMemoId() {
        memoPlainViewModel.memoId.observe(this) {
            memoId = memoPlainViewModel.memoId.value!!
        }
    }

    private fun observeMemoContent() {
        memoPlainViewModel.memo.observe(this) { response ->
            memoContent = response.memoContent
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
        setResult(Activity.RESULT_OK)
    }

    private fun onClickMemoEditButton() {
        binding.tvMemoPlainEditBtn.setOnClickListener {
            val intent = MemoWriteActivity.newIntentFromPlain(
                this, memoId, memoContent, userNovelTitle, userNovelAuthor, userNovelImage
            )
            patchedMemoLauncher.launch(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        memoPlainViewModel.getMemo(memoId)
    }

    companion object {
        fun newIntent(
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
                putExtra("userNovelImage", userNovelImg)
            }
        }
    }
}