package com.teamwss.websoso.util.activity

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.teamwss.websoso.ui.common.view.LoadingProgressIndicator


fun AppCompatActivity.showLoading() {
    supportFragmentManager.commit(allowStateLoss = true) {
        add(LoadingProgressIndicator.newInstance(), LoadingProgressIndicator.TAG)
    }
}

fun AppCompatActivity.hideLoading() {
    supportFragmentManager.findFragmentByTag(LoadingProgressIndicator.TAG)?.let { fragment ->
        supportFragmentManager.commit(allowStateLoss = true) {
            remove(fragment)
        }
    }
}