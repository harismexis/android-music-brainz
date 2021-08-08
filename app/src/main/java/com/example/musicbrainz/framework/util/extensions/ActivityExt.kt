package com.example.musicbrainz.framework.util.extensions

import android.app.Activity
import android.view.WindowManager
import androidx.annotation.ColorRes
import androidx.core.view.WindowInsetsControllerCompat
import com.example.musicbrainz.R

fun Activity.makeStatusBarDark() {
    makeStatusBarDark(R.color.default_bg_color)
}

fun Activity.makeStatusBarDark(@ColorRes color: Int) {
    renderStatusBar(false, color)
}

fun Activity.renderStatusBar(isLight: Boolean, @ColorRes color: Int) {
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    window.statusBarColor = getColorCompat(color)
    val wic = WindowInsetsControllerCompat(window, window.decorView)
    wic.isAppearanceLightStatusBars = isLight
}