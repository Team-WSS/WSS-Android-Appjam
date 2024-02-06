package com.teamwss.websoso.util

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import coil.load
import coil.transform.RoundedCornersTransformation
import coil.transform.Transformation
import com.airbnb.lottie.LottieAnimationView
import com.teamwss.websoso.R
import com.teamwss.websoso.data.remote.response.NovelPlatformInfoResponse
import com.teamwss.websoso.ui.common.model.ReadStatus
import jp.wasabeef.transformers.coil.BlurTransformation

@BindingAdapter("imageUrl", "cornerRadius")
fun loadRoundedCornerImage(view: ImageView, imageUrl: String?, cornerRadius: Float) {
    loadCustomImage(view, imageUrl, RoundedCornersTransformation(cornerRadius))
}

//TODO: SearchViewHolder 데이터 바인딩 적용시 삭제 예정
@BindingAdapter("loadCoverImageRounded14")
fun loadCoverImageRounded14(view: ImageView, imageUrl: String?) {
    loadCustomImage(view, imageUrl, RoundedCornersTransformation(14F))
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

@BindingAdapter("loadImageUrl")
fun loadImageUrl(view: ImageView, imageUrl: String?) {
    view.load(imageUrl)
}

@BindingAdapter("setNovelInfoReadStatusText")
fun setReadStatusText(textView: TextView, status: String) {
    textView.text = when (status) {
        ReadStatus.FINISH.name -> "읽음"
        ReadStatus.READING.name -> "읽는 중"
        ReadStatus.DROP.name -> "하차"
        ReadStatus.WISH.name -> "읽고 싶음"
        else -> "에러"
    }
}

@BindingAdapter("setNovelInfoReadStatusImage")
fun setReadStatusImage(imageView: ImageView, status: String) {
    val context = imageView.context
    val drawableId = when (status) {
        ReadStatus.FINISH.name -> R.drawable.ic_status_finish
        ReadStatus.READING.name -> R.drawable.ic_status_reading
        ReadStatus.DROP.name -> R.drawable.ic_status_drop
        ReadStatus.WISH.name -> R.drawable.ic_status_wish
        else -> R.drawable.ic_alert_warning
    }
    imageView.setImageDrawable(ContextCompat.getDrawable(context, drawableId))
}

@BindingAdapter("setUserNovelInfoReadDateText")
fun setReadDateText(textView: TextView, status: String) {
    when (status) {
        ReadStatus.FINISH.name -> {
            textView.text = "읽은 날짜"
            textView.visibility = View.VISIBLE
        }

        ReadStatus.READING.name -> {
            textView.text = "시작 날짜"
            textView.visibility = View.VISIBLE
        }

        ReadStatus.DROP.name -> {
            textView.text = "종료 날짜"
            textView.visibility = View.VISIBLE
        }

        ReadStatus.WISH.name -> {
            textView.visibility = View.GONE
        }
    }
}

@BindingAdapter("setNovelInfoReadDateBox")
fun setReadDateBox(layout: ConstraintLayout, status: String) {
    when (status) {
        ReadStatus.FINISH.name ->
            layout.visibility = View.VISIBLE

        ReadStatus.READING.name ->
            layout.visibility = View.VISIBLE

        ReadStatus.DROP.name ->
            layout.visibility = View.VISIBLE

        ReadStatus.WISH.name ->
            layout.visibility = View.GONE
    }
}

@BindingAdapter("setNovelInfoReadDateTilde")
fun setReadDateTilde(textView: TextView, status: String) {
    when (status) {
        ReadStatus.FINISH.name ->
            textView.visibility = View.VISIBLE

        ReadStatus.READING.name ->
            textView.visibility = View.GONE

        ReadStatus.DROP.name ->
            textView.visibility = View.GONE

        ReadStatus.WISH.name ->
            textView.visibility = View.GONE
    }
}

@BindingAdapter("setNaverSeriesPlatformUrl")
fun setNaverSeriesPlatformUrl(
    layout: ConstraintLayout,
    platformInfo: List<NovelPlatformInfoResponse>
) {
    val isNaverSeriesVisible = platformInfo.any { it.platformName == "네이버시리즈" }
    layout.visibility = if (isNaverSeriesVisible) View.VISIBLE else View.GONE
}

@BindingAdapter("setKakaoPagePlatformUrl")
fun setKakaoPagePlatformUrl(
    layout: ConstraintLayout,
    platformInfo: List<NovelPlatformInfoResponse>
) {
    val isKakaoPageViesible = platformInfo.any { it.platformName == "카카오페이지" }
    layout.visibility = if (isKakaoPageViesible) View.VISIBLE else View.GONE
}