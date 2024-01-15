package com.teamwss.websoso.util

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
import coil.transform.RoundedCornersTransformation
import coil.transform.Transformation
import com.airbnb.lottie.LottieAnimationView
import com.teamwss.websoso.R
import jp.wasabeef.transformers.coil.BlurTransformation

@BindingAdapter("loadCoverImageRounded6")
fun loadCoverImageRounded6(view: ImageView, imageUrl: String?) {
    loadCustomImage(view, imageUrl, RoundedCornersTransformation(6F))
}

@BindingAdapter("loadCoverImageRounded10")
fun loadCoverImageRounded10(view: ImageView, imageUrl: String?) {
    loadCustomImage(view, imageUrl, RoundedCornersTransformation(10F))
}

@BindingAdapter("loadCoverImageRounded14")
fun loadCoverImageRounded14(view: ImageView, imageUrl: String?) {
    loadCustomImage(view, imageUrl, RoundedCornersTransformation(14F))
}

@BindingAdapter("loadCoverImageRounded30")
fun loadCoverImageRounded30(view: ImageView, imageUrl: String?) {
    loadCustomImage(view, imageUrl, RoundedCornersTransformation(30F))
}

@BindingAdapter("loadCoverImageBlurred5")
fun loadCoverImageBlurred5(view: ImageView, imageUrl: String?) {
    loadCustomImage(view, imageUrl, BlurTransformation(view.context, 5))
}

@BindingAdapter("loadLottieRawRes")
fun loadLottieAnimation(view: LottieAnimationView, avatarId: Int) {
    val resId = avatarId.toLottieImage()
    try {
        if (resId != 0) {
            view.setAnimation(resId)
            view.playAnimation()
        }
    } catch (e: IllegalArgumentException) {
        e.printStackTrace()
    }
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

@BindingAdapter("loadImageUrl")
fun loadImageUrl(view: ImageView, imageUrl: String?) {
    view.load(imageUrl)
}