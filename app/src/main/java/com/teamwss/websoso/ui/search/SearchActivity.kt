package com.teamwss.websoso.ui.search

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.teamwss.websoso.R
import com.teamwss.websoso.databinding.ActivitySearchBinding
import com.teamwss.websoso.ui.postNovel.PostNovelActivity
import com.teamwss.websoso.ui.search.searchViewModel.SearchViewModel
import com.teamwss.websoso.ui.search.searchViewModel.SearchViewModel.Companion.EXTRA_PAGE_SIZE
import com.teamwss.websoso.ui.search.searchViewModel.SearchViewModel.Companion.INPUT_DELAY
import com.teamwss.websoso.ui.search.searchViewModel.SearchViewModel.Companion.LAST_NOVEL_ID
import com.teamwss.websoso.ui.search.searchViewModel.SearchViewModel.Companion.PAGE_SIZE

class SearchActivity : AppCompatActivity() {
    private val viewModel by viewModels<SearchViewModel>()
    private lateinit var binding: ActivitySearchBinding
    private lateinit var searchAdapter: SearchAdapter

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
        handleSearchEditTextOnInputFinish()
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
        val handler = Handler(Looper.getMainLooper())
        val delaySearchRunnable = Runnable {
            viewModel.searchNovels(LAST_NOVEL_ID, PAGE_SIZE, binding.etSearch.text.toString())
        }
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
                handleTextChange(handler, delaySearchRunnable)
            }

            override fun afterTextChanged(text: Editable?) {
                toggleCancelVisibility(text)
            }
        })
    }

    private fun handleTextChange(
        handler: Handler,
        delaySearchRunnable: Runnable
    ) {
        if (binding.etSearch.text.isNotEmpty()) {
            handler.removeCallbacks(delaySearchRunnable)
            handler.postDelayed(delaySearchRunnable, INPUT_DELAY)
            binding.clSearchView.setBackgroundResource(R.drawable.bg_stroke_gray70_2dp_radius_12dp)
        }
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

                viewModel.searchNovels(LAST_NOVEL_ID, PAGE_SIZE, binding.etSearch.text.toString())
            } else {
                binding.clSearchView.setBackgroundResource(R.color.transparent)
            }
            isHandled
        }
    }

    private fun setupRecyclerView() {
        searchAdapter = SearchAdapter { novelId ->
            val intent = PostNovelActivity.createIntent(this, novelId).apply {
                putExtra("NOVEL_ID", novelId)
            }
            startActivity(intent)
        }
        binding.rvSearchResult.adapter = searchAdapter
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
}