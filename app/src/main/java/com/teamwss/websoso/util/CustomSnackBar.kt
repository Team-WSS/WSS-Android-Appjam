package com.teamwss.websoso.util

import android.graphics.drawable.Drawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import com.teamwss.websoso.R
import com.teamwss.websoso.databinding.CustomSnackBarBinding

class CustomSnackBar(view: View, private val message: String, private val drawable: Drawable) {

    companion object {
        fun make(view: View, message: String, drawable: Drawable) =
            CustomSnackBar(view, message, drawable)
    }

    private val context = view.context
    private val snackBar = Snackbar.make(view, "", 2000)
    private val snackBarLayout = snackBar.view as Snackbar.SnackbarLayout

    private val inflater = LayoutInflater.from(context)
    private val snackBarBinding: CustomSnackBarBinding =
        DataBindingUtil.inflate(inflater, R.layout.custom_snack_bar, null, false)

    init {
        initView()
        initData()
    }

    private fun initView() {
        with(snackBarLayout) {
            val layoutParams = layoutParams as FrameLayout.LayoutParams

            layoutParams.gravity = Gravity.BOTTOM
            removeAllViews()
            setPadding(0, 0, 0, 6) // 👈 padding 설정. 위에서 16만큼 떨어져있게.
            setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent))
            addView(snackBarBinding.root, 0)
        }
    }

    private fun initData() {
        snackBarBinding.tvCustomSnackBar.text = message
        snackBarBinding.ivCustomSnackBar.background = drawable
    }

    fun show() {
        snackBar.show()
    }
}