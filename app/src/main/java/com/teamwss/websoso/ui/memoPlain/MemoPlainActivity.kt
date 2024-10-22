package com.teamwss.websoso.ui.memoPlain

import com.teamwss.websoso.ui.common.view.CustomSnackBar
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.teamwss.websoso.R
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
        DialogMemoDelete {
            setResult(Activity.RESULT_OK)
            finish()
        }
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
                    val drawable = ContextCompat.getDrawable(this, R.drawable.ic_alert_default)
                    CustomSnackBar.make(binding.root).setText("메모를 수정했어요").setIcon(
                        drawable ?: ContextCompat.getDrawable(
                            this, R.drawable.ic_alert_default
                        )!!
                    ).show()
                }
            }

        setTranslucentOnStatusBar()
        setupLifecycleOwner()
        setupDataBinding()
        getUserNovelData()
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

    private fun getUserNovelData() {
        memoPlainViewModel.memo.observe(this) { response ->
            userNovelImage = response.userNovelImg
            userNovelTitle = response.userNovelTitle
            userNovelAuthor = response.userNovelAuthor
        }
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
        ): Intent {
            return Intent(context, MemoPlainActivity::class.java).apply {
                putExtra("memoId", memoId)
            }
        }
    }
}