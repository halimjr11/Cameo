package com.halimjr11.cameo.view.feature.main

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.halimjr11.cameo.common.orZero
import com.halimjr11.cameo.databinding.ActivityMainBinding
import com.halimjr11.cameo.view.feature.home.HomeFragmentDirections

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val navHostFragment =
            supportFragmentManager.findFragmentById(binding.mainNav.id) as NavHostFragment
        navController = navHostFragment.navController
        checkIntent()
    }

    private fun checkIntent() {
        val movieId = intent?.getIntExtra(MOVIE_ID, 0)
        if (movieId != 0) {
            val action =
                HomeFragmentDirections.actionHomeFragmentToMovieDetailFragment(
                    idMovie = movieId.orZero(),
                    isFromFavorite = true
                )
            navController.navigate(action)
        }
    }

    companion object {
        const val MOVIE_ID = "movie_id"
    }
}