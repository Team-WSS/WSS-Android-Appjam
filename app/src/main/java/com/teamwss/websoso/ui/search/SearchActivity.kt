package com.teamwss.websoso.ui.search

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.teamwss.websoso.R
import com.teamwss.websoso.databinding.ActivitySearchBinding
import com.teamwss.websoso.ui.search.model.SearchResult
import com.teamwss.websoso.ui.search.searchViewModel.SearchViewModel

class SearchActivity : AppCompatActivity() {
    private val viewModel by viewModels<SearchViewModel>()
    private lateinit var binding: ActivitySearchBinding
    private lateinit var searchAdapter: SearchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showKeyboardOnEditTextFocus()
        setupSearchEditText()
        handleSearchEditTextOnInputFinish()
        setupRecyclerView()
        setResultNovelList()

        isResultEmpty()
    }

    private fun showKeyboardOnEditTextFocus() {
        val searchKeyboard = binding.etSearch
        searchKeyboard.requestFocus()

        val inputMethodManager =
            this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(binding.etSearch, 0)
    }

    private fun setupSearchEditText() {
        binding.etSearch.addTextChangedListener(getTextWatcher())
        binding.ivSearchCancel.setOnClickListener {
            clearSearchEditText()
        }
    }

    private fun getTextWatcher(): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
                updateSearchViewBackground()
            }

            override fun afterTextChanged(text: Editable?) {
                toggleCancelVisibility(text)
            }
        }
    }

    private fun updateSearchViewBackground() {
        binding.clSearchView.setBackgroundResource(R.drawable.bg_stroke_gray70_2dp_radius_12dp)
    }

    private fun toggleCancelVisibility(text: Editable?) {
        if (text.isNullOrEmpty()) {
            binding.ivSearchCancel.visibility = View.GONE
        } else {
            binding.ivSearchCancel.visibility = View.VISIBLE
        }
    }

    private fun clearSearchEditText() {
        binding.etSearch.text.clear()
    }

    private fun handleSearchEditTextOnInputFinish() {
        binding.etSearch.setOnEditorActionListener { _, action, _ ->
            binding.clSearchView.setBackgroundResource(R.color.transparent)
            var isHandled = false

            if (action == EditorInfo.IME_ACTION_DONE) {
                val inputMethodManager =
                    this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(binding.etSearch.windowToken, 0)
                binding.clSearchView.setBackgroundResource(R.drawable.bg_gray50_radius_12dp)
                isHandled = true

                viewModel.searchNovels(999999, 40, binding.etSearch.text.toString())
            } else {
                binding.clSearchView.setBackgroundResource(R.color.transparent)
            }
            isHandled
        }
    }

    private fun setupRecyclerView() {
        searchAdapter = SearchAdapter()
        binding.rvSearchResult.adapter = searchAdapter
    }

    private fun setResultNovelList() {
        viewModel.searchResult.observe(this) {
            searchAdapter.setResultNovelList(it.novels)
        }
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
                resultNovelTitle = "당신의 이해를 돕기 위해서라는 웹소설이있어요 이거는 겁나 긴 제목을 위한 것입니다 과연 이게 어떻게 나올까요 나도 궁금하네요",
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

    private fun isResultEmpty() {
        viewModel.searchResult.observe(this) {
            if (it.novels.isEmpty()) {
                binding.clSearchResultNoExist.visibility = View.VISIBLE
            } else {
                binding.clSearchResultNoExist.visibility = View.GONE
            }
        }
    }
}