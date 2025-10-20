package com.halimjr11.cameo.navigation

import android.content.Context
import android.content.Intent
import com.halimjr11.cameo.view.feature.main.MainActivity

class CameoAppNavigation : CameoNavigation {
    override fun goToDetailPage(context: Context, movieId: Int) {
        val intent = Intent(context, MainActivity::class.java).apply {
            putExtra(MainActivity.MOVIE_ID, movieId)
        }
        context.startActivity(intent)
    }
}