package com.teamwss.websoso.ui.search

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.teamwss.websoso.R
import com.teamwss.websoso.databinding.ActivitySearchBinding
import com.teamwss.websoso.ui.search.model.SearchResult

class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding
    private lateinit var searchAdapter: SearchAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showKeyboardOnEditTextFocus()
        deleteEditTextOnCancleBtn()
        handleSearchEditTextOnInputFinish()
        setupRecyclerView()
        setResultNovelList()
    }

    private fun showKeyboardOnEditTextFocus() {
        val searchKeyboard = binding.etSearch
        searchKeyboard.requestFocus()

        val inputMethodManager =
            this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(binding.etSearch, 0)
    }

    private fun deleteEditTextOnCancleBtn() {
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.clSearchView.setBackgroundResource(R.drawable.bg_transparent_stroke_gray70_2dp_radius_12dp)
            }

            override fun afterTextChanged(s: Editable?) {
                if (s.isNullOrEmpty()) {
                    binding.ivSearchCancel.visibility = View.GONE
                } else {
                    binding.ivSearchCancel.visibility = View.VISIBLE
                }
            }
        })
        binding.ivSearchCancel.setOnClickListener {
            binding.etSearch.text.clear()
        }
    }

    private fun handleSearchEditTextOnInputFinish() {
        binding.etSearch.setOnEditorActionListener { _, action, _ ->
            binding.clSearchView.setBackgroundResource(R.color.transparent)
            var handled = false

            if (action == EditorInfo.IME_ACTION_DONE) {
                val inputMethodManager =
                    this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(binding.etSearch.windowToken, 0)
                binding.clSearchView.setBackgroundResource(R.drawable.bg_gray50_radius_12dp)
                handled = true
            } else {
                binding.clSearchView.setBackgroundResource(R.color.transparent)
            }
            handled
        }
    }

    private fun setupRecyclerView() {
        searchAdapter = SearchAdapter()
        binding.rvSearchResult.adapter = searchAdapter
    }

    private fun setResultNovelList() {
        val resultList = mockResultNovelList
        searchAdapter.setResultNovelList(resultList)
    }

    companion object {
        private val mockResultNovelList = listOf<SearchResult>(
            SearchResult(
                resultNovelImage = R.drawable.img_cover_test,
                resultNovelTitle = "당신의 이해를 돕기 위해서",
                resultNovelAuthor = "이보라",
                resultNovelGenre = "로판",
            ),
            SearchResult(
                resultNovelImage = R.drawable.img_cover_test,
                resultNovelTitle = "당신의 이해를 돕기 위해서",
                resultNovelAuthor = "이보라",
                resultNovelGenre = "로판",
            ),
            SearchResult(
                resultNovelImage = R.drawable.img_cover_test,
                resultNovelTitle = "당신의 이해를 돕기 위해서",
                resultNovelAuthor = "이보라",
                resultNovelGenre = "로판",
            ),
            SearchResult(
                resultNovelImage = R.drawable.img_cover_test,
                resultNovelTitle = "당신의 이해를 돕기 위해서",
                resultNovelAuthor = "이보라",
                resultNovelGenre = "로판",
            ),
            SearchResult(
                resultNovelImage = R.drawable.img_cover_test,
                resultNovelTitle = "당신의 이해를 돕기 위해서",
                resultNovelAuthor = "이보라",
                resultNovelGenre = "로판",
            ),
            SearchResult(
                resultNovelImage = R.drawable.img_cover_test,
                resultNovelTitle = "당신의 이해를 돕기 위해서",
                resultNovelAuthor = "이보라",
                resultNovelGenre = "로판",
            ),
            SearchResult(
                resultNovelImage = R.drawable.img_cover_test,
                resultNovelTitle = "당신의 이해를 돕기 위해서",
                resultNovelAuthor = "이보라",
                resultNovelGenre = "로판",
            ),
            SearchResult(
                resultNovelImage = R.drawable.img_cover_test,
                resultNovelTitle = "당신의 이해를 돕기 위해서",
                resultNovelAuthor = "이보라",
                resultNovelGenre = "로판",
            ),
        )
    }
}