package com.teamwss.websoso.ui.main.myPage.changeNickName

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.teamwss.websoso.R
import com.teamwss.websoso.databinding.ActivityChangeNicknameBinding

class ChangeNicknameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChangeNicknameBinding
    private val changeNicknameViewModel: ChangeNicknameViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangeNicknameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setTranslucentOnStatusBar()
        setupUI()
        observePatchSucess()
    }

    private fun setTranslucentOnStatusBar() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
    }

    private fun setupUI() {
        handelBackToMyPage()
        setupChangeNameEditText()
        setDefaultNameAndTextWatcher(binding.etChangeNickname, binding.tvChangeNicknameCount, 10)
        setupCompleteTextClickListener()
    }

    private fun handelBackToMyPage() {
        binding.ivChangeNicknameBack.setOnClickListener {
            finish()
        }
    }

    private fun setupChangeNameEditText() {
        setupTextWatcher()
        focusChangeListener()
        setupClearButtonClickListener()
    }

    private fun setupTextWatcher() {
        binding.etChangeNickname.addTextChangedListener(
            createTextWatcher(
                binding.etChangeNickname,
                binding.tvChangeNicknameCount,
                MAX_LENGTH,
            )
        )
    }

    private fun focusChangeListener() {
        binding.etChangeNickname.setOnFocusChangeListener { _, hasFocus ->
            setEditTextFocusColor(binding.etChangeNickname, hasFocus)
        }
    }

    private fun setEditTextFocusColor(editText: EditText, hasFocus: Boolean) {
        val backgroundColor = if (hasFocus) R.color.primary_100_6341F0 else R.color.gray_200_AEADB3
        editText.background.setTint(ContextCompat.getColor(this, backgroundColor))
    }

    private fun setupClearButtonClickListener() {
        binding.ivChangeNicknameCancel.setOnClickListener {
            clearChangeNameEditText()
        }
    }

    private fun clearChangeNameEditText() {
        binding.etChangeNickname.text.clear()
    }

    private fun createTextWatcher(
        editText: EditText,
        countTextView: TextView,
        maxLength: Int
    ): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(
                charSequence: CharSequence?,
                start: Int,
                count: Int,
                after: Int,
            ) {
            }

            override fun onTextChanged(
                charSequence: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                handleTextChanged(charSequence, start, count, maxLength)
                updateTextCount(charSequence, countTextView)
            }

            override fun afterTextChanged(text: Editable?) {
                toggleCancelIconVisibility(text)
            }
        }
    }

    private fun handleTextChanged(
        charSequence: CharSequence?,
        start: Int,
        count: Int,
        maxLength: Int
    ) {
        val currentLength = charSequence?.length ?: 0

        if (currentLength > maxLength) {
            binding.etChangeNickname.text?.delete(start + count - 1, start + count)
        }
    }

    private fun updateTextCount(charSequence: CharSequence?, countTextView: TextView) {
        val currentLength = charSequence?.length ?: 0
        countTextView.text = getString(R.string.change_name_text_count, currentLength)
    }

    private fun toggleCancelIconVisibility(text: Editable?) {
        binding.ivChangeNicknameCancel.visibility =
            if (text.isNullOrEmpty()) View.GONE else View.VISIBLE
    }

    private fun setDefaultNameAndTextWatcher(
        editText: EditText,
        countTextView: TextView,
        maxLength: Int
    ) {
        val defaultName = intent.getStringExtra("userName")
        editText.text = Editable.Factory.getInstance().newEditable(defaultName)
        val textWatcher = createTextWatcher(editText, countTextView, maxLength)
        editText.addTextChangedListener(textWatcher)
    }

    private fun setupCompleteTextClickListener() {
        binding.tvChangeNicknameComplete.setOnClickListener {
            val userNickname = binding.etChangeNickname.text.toString()
            changeNicknameViewModel.patchUserNickName(userNickname)
        }
    }

    private fun observePatchSucess() {
        changeNicknameViewModel.patchSuccess.observe(this) { result ->
            when (result) {
                true -> {
                    showToast("닉네임을 저장했어요.", Toast.LENGTH_SHORT)
                    setResult(AppCompatActivity.RESULT_OK) // 결과 설정
                    finish()
                }

                false -> {
                    showToast("닉네임을 저장에 실패했어요.", Toast.LENGTH_SHORT)
                }
            }
        }
    }

    private fun showToast(message: String, duration: Int) {
        val toast = Toast.makeText(this, message, duration)
        val handler = Handler(Looper.getMainLooper())

        handler.postDelayed({
            toast.cancel()
        }, 1000)

        toast.show()
    }

    companion object {
        private const val MAX_LENGTH = 10

        fun createIntent(context: Context, userName: String): Intent {
            return Intent(context, ChangeNicknameActivity::class.java).apply {
                putExtra("userName", userName)
            }
        }
    }
}

