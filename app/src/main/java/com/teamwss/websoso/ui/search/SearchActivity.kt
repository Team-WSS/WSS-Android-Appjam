package com.teamwss.websoso.ui.search

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.teamwss.websoso.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showKeyboardOnEditTextFocus()
        deleteEditTextOnCancleBtn()
    }

    private fun showKeyboardOnEditTextFocus() {
        val searchKeyboard = binding.etSearch
        searchKeyboard.requestFocus()

        val inputMethodManager =
            this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(binding.etSearch, 0)
    }

    private fun deleteEditTextOnCancleBtn() {
        val searchBtn = binding.ivSearchCancel
        val etSearch = binding.etSearch

        etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if (s.isNullOrEmpty()) {
                    searchBtn.visibility = View.GONE
                } else {
                    searchBtn.visibility = View.VISIBLE
                }
            }
        })

        searchBtn.setOnClickListener {
            etSearch.text.clear()
        }

    }

}