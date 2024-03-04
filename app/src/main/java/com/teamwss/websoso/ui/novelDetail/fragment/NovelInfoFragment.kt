package com.teamwss.websoso.ui.novelDetail.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.teamwss.websoso.R
import com.teamwss.websoso.databinding.FragmentNovelInfoBinding
import com.teamwss.websoso.ui.common.model.ReadStatus
import com.teamwss.websoso.ui.novelDetail.NovelDetailViewModel

class NovelInfoFragment : Fragment() {
    private var _binding: FragmentNovelInfoBinding? = null
    private val binding: FragmentNovelInfoBinding
        get() = requireNotNull(_binding)
    private val novelDetailViewModel: NovelDetailViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNovelInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupLifecycleOwner()
        setupDataBinding()
        observeReadStatus()
        observeNovelData()
    }

    private fun setupLifecycleOwner() {
        binding.lifecycleOwner = viewLifecycleOwner
    }

    private fun setupDataBinding() {
        binding.novelDetailViewModel = novelDetailViewModel
    }

    private fun observeReadStatus() {
        novelDetailViewModel.readStatus.observe(viewLifecycleOwner) { readStatus ->
            val drawableRes = when (readStatus) {
                ReadStatus.FINISH -> R.drawable.ic_status_finish
                ReadStatus.READING -> R.drawable.ic_status_reading
                ReadStatus.DROP -> R.drawable.ic_status_drop
                ReadStatus.WISH -> R.drawable.ic_status_wish
                else -> null
            }
            drawableRes?.let { binding.ivNovelInfoReadStatus.setImageResource(it) }
        }
    }

    private fun observeNovelData() {
        novelDetailViewModel.userNovelMemoInfoResponse.observe(viewLifecycleOwner) {
            clickListener()
        }
    }

    private fun clickListener() {
        val platformUlrs: MutableMap<String, String> = mutableMapOf()
        novelDetailViewModel.platforms.value!!.forEach {
            when (it.platformName) {
                "네이버시리즈" -> platformUlrs[it.platformName] = it.platformUrl
                "카카오페이지" -> platformUlrs[it.platformName] = it.platformUrl
            }
        }
        binding.clNovelInfoNaverSeries.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(platformUlrs["네이버시리즈"]))
            startActivity(intent)
        }
        binding.clNovelInfoKakaoPage.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(platformUlrs["카카오페이지"]))
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()

        view?.requestLayout()
    }
}