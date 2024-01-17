package com.teamwss.websoso.ui.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.teamwss.websoso.databinding.ActivitySearchBinding
import com.teamwss.websoso.ui.postNovel.PostNovelActivity
import com.teamwss.websoso.ui.search.searchViewModel.SearchViewModel
import com.teamwss.websoso.ui.search.searchViewModel.SearchViewModel.Companion.EXTRA_PAGE_SIZE
import com.teamwss.websoso.ui.search.searchViewModel.SearchViewModel.Companion.LAST_NOVEL_ID
import com.teamwss.websoso.ui.search.searchViewModel.SearchViewModel.Companion.PAGE_SIZE
import com.teamwss.websoso.ui.search.searchViewModel.SearchViewModel.Companion.SEARCH_DELAY
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchActivity : AppCompatActivity() {
    private val viewModel by viewModels<SearchViewModel>()
    private lateinit var binding: ActivitySearchBinding
    private lateinit var searchAdapter: SearchAdapter

    private var searchJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivSearchBack.setOnClickListener {
            finish()
        }

        setTranslucentOnStatusBar()
        showKeyboardOnEditTextFocus()
        setupSearchEditText()
        setupRecyclerView()
        setResultNovelList()
        setupInfinityScroll()
        isResultEmpty()
    }

    private fun setTranslucentOnStatusBar() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
    }

    private fun showKeyboardOnEditTextFocus() {
        val searchKeyboard = binding.etSearch
        searchKeyboard.requestFocus()

        val inputMethodManager =
            this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(binding.etSearch, 0)
    }

    private fun setupSearchEditText() {
        setupTextWatcher()
        setupTextRemover()
    }

    private fun setupTextWatcher() {
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                text: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
                val autoSearchDelay: Long = SEARCH_DELAY
                searchJob?.cancel()
                searchJob = lifecycleScope.launch {
                    delay(autoSearchDelay)
                    handleSearchTextChange(text)
                }
            }

            override fun afterTextChanged(test: Editable?) {
                toggleCancelVisibility(test)
            }
        })
    }

    private fun handleSearchTextChange(text: CharSequence?) {
        if (text.isNullOrEmpty()) {
            viewModel.removeSearchResult()
            binding.clSearchResultNoExist.visibility = View.GONE
        }
        viewModel.searchNovels(LAST_NOVEL_ID, PAGE_SIZE, text.toString())
    }

    private fun setupTextRemover() {
        binding.ivSearchCancel.setOnClickListener {
            binding.etSearch.text.clear()
        }
    }

    private fun toggleCancelVisibility(text: Editable?) {
        if (text.isNullOrEmpty()) {
            binding.ivSearchCancel.visibility = View.GONE
        } else {
            binding.ivSearchCancel.visibility = View.VISIBLE
        }
    }

    private fun setupRecyclerView() {
        searchAdapter = SearchAdapter { novelId ->
            navigateToPostNovelActivity(novelId)
        }
        binding.rvSearchResult.adapter = searchAdapter
    }

    private fun navigateToPostNovelActivity(novelId: Long) {
        val intent = PostNovelActivity.newIntent(this, novelId)
        startActivity(intent)
        finish()
    }

    private fun setResultNovelList() {
        viewModel.searchResult.observe(this) {
            searchAdapter.setResultNovelList(it.novels)
        }
    }

    private fun setupInfinityScroll() {
        binding.rvSearchResult.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val lastVisibleItemPosition =
                    (recyclerView.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
                val itemTotalCount = recyclerView.adapter!!.itemCount - EXTRA_PAGE_SIZE

                if (lastVisibleItemPosition == itemTotalCount && viewModel.isLoading.value != true) {
                    viewModel.searchNovels(
                        LAST_NOVEL_ID, PAGE_SIZE, viewModel.searchWord.value.toString()
                    )
                }
            }
        })
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

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, SearchActivity::class.java).apply {
            }
        }
    }
}