package com.halimjr11.cameo.navigation

import android.content.Context

interface CameoNavigation {
    fun goToDetailPage(context: Context, movieId: Int)
}