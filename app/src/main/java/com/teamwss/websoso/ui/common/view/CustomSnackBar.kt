package com.teamwss.websoso.ui.common.view

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.teamwss.websoso.databinding.CustomSnackBarBinding

class CustomSnackBar(private val view: View) {
    private val snackBar: Snackbar = Snackbar.make(view, "", Snackbar.LENGTH_SHORT)
    private val binding: CustomSnackBarBinding

    init {
        val inflater = LayoutInflater.from(view.context)
        binding = CustomSnackBarBinding.inflate(inflater)

        val snackBarLayout = snackBar.view as Snackbar.SnackbarLayout
        snackBarLayout.setBackgroundColor(
            ContextCompat.getColor(
                view.context,
                android.R.color.transparent
            )
        )
        snackBarLayout.addView(binding.root, 0)
    }

    fun setText(text: String): CustomSnackBar {
        binding.tvCustomSnackBar.text = text
        return this
    }

    fun setIcon(drawable: Drawable): CustomSnackBar {
        binding.ivCustomSnackBar.background = drawable
        return this
    }

    fun show() {
        snackBar.show()
    }

    companion object {
        fun make(view: View): CustomSnackBar {
            return CustomSnackBar(view)
        }
    }
}