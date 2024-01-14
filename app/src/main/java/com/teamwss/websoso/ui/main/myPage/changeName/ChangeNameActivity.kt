package com.teamwss.websoso.ui.main.myPage.changeName

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.teamwss.websoso.R
import com.teamwss.websoso.databinding.ActivityChangeNameBinding


class ChangeNameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChangeNameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangeNameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setTranslucentOnStatusBar()
        setupUI()
    }

    private fun setTranslucentOnStatusBar() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
    }

    private fun setupUI() {
        launchBackToMyPageOnClick()
        setupChangeNameEditText()
        initChangeNameEditText()
        setDefaultNameAndTextWatcher(binding.etChangeName, binding.tvChangeNameCount, 10)
    }

    private fun launchBackToMyPageOnClick() {
        binding.ivChangeNameBack.setOnClickListener {
            try {
                finish()
            } catch (e: Exception) {
                Log.e("error", "이동실패", e)
            }
        }
    }

    private fun setupChangeNameEditText() {
        val etChangeName: EditText = binding.etChangeName

        etChangeName.addTextChangedListener(
            createTextWatcher(
                etChangeName,
                binding.tvChangeNameCount,
                10
            )
        )
        etChangeName.setOnFocusChangeListener { _, hasFocus ->
            updateEditTextFocus(etChangeName, hasFocus)
        }
    }

    private fun updateEditTextFocus(editText: EditText, hasFocus: Boolean) {
        val backgroundColor = if (hasFocus) R.color.primary_100_6341F0 else R.color.gray_200_AEADB3
        editText.background.setTint(ContextCompat.getColor(this, backgroundColor))
    }

    private fun initChangeNameEditText() {
        binding.etChangeName.addTextChangedListener(
            createTextWatcher(
                binding.etChangeName,
                binding.tvChangeNameCount,
                10
            )
        )
        binding.ivChangeNameCancel.setOnClickListener {
            clearChangeNameEditText()
        }
    }

    private fun clearChangeNameEditText() {
        binding.etChangeName.text.clear()
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
            binding.etChangeName.text?.delete(start + count - 1, start + count)
        }
    }

    private fun updateTextCount(charSequence: CharSequence?, countTextView: TextView) {
        val currentLength = charSequence?.length ?: 0
        countTextView.text = getString(R.string.change_name_text_count, currentLength)
    }

    private fun toggleCancelIconVisibility(text: Editable?) {
        binding.ivChangeNameCancel.visibility =
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
}

