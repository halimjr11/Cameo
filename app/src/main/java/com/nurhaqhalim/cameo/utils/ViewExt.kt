package com.nurhaqhalim.cameo.utils

import android.content.res.Resources
import android.view.View
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible

fun Resources.toDrawable(@DrawableRes drawableId: Int) =
    ResourcesCompat.getDrawable(this, drawableId, null)

fun View.gone() {
    this.isVisible = false
}

fun View.visible() {
    this.isVisible = true
}

fun AppCompatImageView.toCenterCrop() {
    this.apply {
        scaleType = ImageView.ScaleType.CENTER_CROP
    }
}
