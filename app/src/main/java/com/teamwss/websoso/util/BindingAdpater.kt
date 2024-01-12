package com.teamwss.websoso.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
import coil.transform.RoundedCornersTransformation
import com.teamwss.websoso.R
import jp.wasabeef.transformers.coil.BlurTransformation

@BindingAdapter("loadCoverImage")
fun loadCoverImage(view: ImageView, imageUrl: String?) {
    imageUrl?.let {
        view.load(it) {
            crossfade(true)
            placeholder(R.drawable.img_loading_thumbnail)
            error(R.drawable.img_loading_thumbnail)
            transformations(RoundedCornersTransformation(30F))
        }
    }
}

@BindingAdapter("loadBlurredCoverImage")
fun loadBlurredCoverImage(view: ImageView, imageUrl: String?) {
    imageUrl?.let {
        view.load(it) {
            crossfade(true)
            placeholder(R.drawable.img_loading_thumbnail)
            error(R.drawable.img_loading_thumbnail)
            transformations(BlurTransformation(view.context, 25))
        }
    }
}