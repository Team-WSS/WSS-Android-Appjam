package com.teamwss.websoso.util

import com.teamwss.websoso.R

fun Long.toLottieImage() = when (this) {
    1L -> R.raw.lottie_home_regressor
    else -> R.raw.lottie_home_regressor
}