package com.teamwss.websoso.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
import coil.transform.RoundedCornersTransformation
import coil.transform.Transformation
import com.teamwss.websoso.R
import jp.wasabeef.transformers.coil.BlurTransformation

@BindingAdapter("loadCoverImageRounded30")
fun loadCoverImageRounded30(view: ImageView, imageUrl: String?) {
    loadCustomImage(view, imageUrl, RoundedCornersTransformation(30F))
}

@BindingAdapter("loadCoverImageRounded6")
fun loadCoverImageRounded6(view: ImageView, imageUrl: String?) {
    loadCustomImage(view, imageUrl, RoundedCornersTransformation(30F))
}

@BindingAdapter("loadCoverImageBlurred5")
fun loadCoverImageBlurred5(view: ImageView, imageUrl: String?) {
    loadCustomImage(view, imageUrl, BlurTransformation(view.context, 5))
}

private fun loadCustomImage(view: ImageView, imageUrl: String?, transformation: Transformation) {
    imageUrl?.let {
        view.load(it) {
            listener(
                onSuccess = { _, _ -> applyImageTransformations(view, it, transformation) },
                onError = { _, _ -> loadDefaultImage(view, transformation) }
            )
        }
    } ?: run {
        loadDefaultImage(view, transformation)
    }
}

private fun applyImageTransformations(view: ImageView, imageUrl: String, transformation: Transformation) {
    view.load(imageUrl) {
        crossfade(true)
        transformations(transformation)
    }
}

private fun loadDefaultImage(view: ImageView, transformation: Transformation) {
    view.load(R.drawable.img_loading_thumbnail) {
        transformations(transformation)
    }
}