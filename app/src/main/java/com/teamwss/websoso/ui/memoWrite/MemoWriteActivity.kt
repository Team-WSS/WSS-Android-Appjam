package com.teamwss.websoso.ui.memoWrite

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
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

    private val dialogMemoCancel: DialogMemoCancel by lazy {
        DialogMemoCancel(::finish)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMemoWriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()
        getUserNovelDataFromBeforeView()
        updateUserNovelToViewModel()
        onClickBackButton()
        if (userNovelId != -1L) {
            clickListener(userNovelId)
        }

        if (memoId != -1L) {
            clickListenerPatch(memoId)
        }
    }

    private fun initUI(){
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

    private fun onClickBackButton() {
        binding.ivMemoWriteCancelBtn.setOnClickListener {
            dialogMemoCancel.show((supportFragmentManager), "CancelMemoDialog")
        }
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