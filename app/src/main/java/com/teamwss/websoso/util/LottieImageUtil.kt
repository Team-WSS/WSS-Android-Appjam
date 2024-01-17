package com.teamwss.websoso.util

import com.teamwss.websoso.R

fun Long.toLottieImage() = when (this) {
    1L -> R.raw.lottie_sosocat_1
    2L -> R.raw.lottie_villainess_1
    3L -> R.raw.lottie_regressor_1
    else -> R.raw.lottie_sosocat_2
}