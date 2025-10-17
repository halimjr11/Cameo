package com.halimjr11.cameo.components

import android.view.View
import androidx.core.view.isVisible

fun View.visibleIf(state: Boolean) {
    this.isVisible = state
}


