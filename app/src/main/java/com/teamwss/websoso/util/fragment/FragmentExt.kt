package com.teamwss.websoso.util.fragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.teamwss.websoso.ui.common.view.LoadingProgressIndicator

fun Fragment.showLoading() {
    childFragmentManager.commit(allowStateLoss = true) {
        add(LoadingProgressIndicator.newInstance(), LoadingProgressIndicator.TAG)
    }
}

fun Fragment.hideLoading() {
    childFragmentManager.findFragmentByTag(LoadingProgressIndicator.TAG)?.let { fragment ->
        childFragmentManager.commit(allowStateLoss = true) {
            remove(fragment)
        }
    }
}