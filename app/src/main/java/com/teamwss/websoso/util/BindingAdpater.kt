package com.teamwss.websoso.util

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
import coil.transform.RoundedCornersTransformation
import coil.transform.Transformation
import com.airbnb.lottie.LottieAnimationView
import com.teamwss.websoso.R
import com.teamwss.websoso.data.remote.response.NovelPlatformInfoResponse
import jp.wasabeef.transformers.coil.BlurTransformation

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, imageUrl: String?) {
    view.load(imageUrl)
}

@BindingAdapter("imageUrl", "cornerRadius")
fun loadRoundedCornerImage(view: ImageView, imageUrl: String?, cornerRadius: Float) {
    loadCustomImage(view, imageUrl, RoundedCornersTransformation(cornerRadius))
}

//TODO: SearchViewHolder 데이터 바인딩 적용시 삭제 예정
@BindingAdapter("loadCoverImageRounded14")
fun loadCoverImageRounded14(view: ImageView, imageUrl: String?) {
    loadCustomImage(view, imageUrl, RoundedCornersTransformation(14F))
}

@BindingAdapter("imageUrl", "blurRadius")
fun loadBlurredImage(view: ImageView, imageUrl: String?, blurRadius: Int) {
    loadCustomImage(view, imageUrl, BlurTransformation(view.context, blurRadius))
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

private fun loadDefaultImage(view: ImageView, transformation: Transformation) {
    view.load(R.drawable.img_loading_thumbnail) {
        transformations(transformation)
    }
}

private fun applyImageTransformations(
    view: ImageView,
    imageUrl: String,
    transformation: Transformation
) {
    view.load(imageUrl) {
        crossfade(true)
        transformations(transformation)
    }
}

@BindingAdapter("loadLottieRawRes")
fun loadLottieAnimation(view: LottieAnimationView, avatarId: Long) {
    val currentTime = System.currentTimeMillis()
    val resId =
        if (currentTime % 2 == 0L) avatarId.toLottieImage() else (avatarId + 100L).toLottieImage()
    try {
        if (resId != 0) {
            view.setAnimation(resId)
            view.playAnimation()
        }
    } catch (e: IllegalArgumentException) {
        e.printStackTrace()
    }
}

@BindingAdapter("visibleGone")
fun setVisibleGone(view: View, visible: Boolean) {
    view.visibility = if (visible) View.VISIBLE else View.GONE
}

@BindingAdapter("visibleIf", "andCondition")
fun setVisibility(view: View, condition1: Boolean, condition2: Boolean) {
    view.visibility = if (condition1 && condition2) View.VISIBLE else View.GONE
}

@BindingAdapter("platformInfo", "platformName")
fun setPlatformVisibility(
    view: View,
    platformInfo: List<NovelPlatformInfoResponse>,
    platformName: String,
) {
    val platformData = platformInfo.find { it.platformName == platformName }
    view.visibility = if (platformData != null) View.VISIBLE else View.GONE
}