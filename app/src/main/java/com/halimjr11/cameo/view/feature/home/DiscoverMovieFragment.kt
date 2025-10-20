package com.halimjr11.cameo.view.feature.home

import android.os.Bundle
import androidx.lifecycle.ViewModel
import coil.load
import com.halimjr11.cameo.common.Constants.IMAGE_URL
import com.halimjr11.cameo.components.BaseFragment
import com.halimjr11.cameo.databinding.FragmentDiscoverMovieBinding
import com.halimjr11.cameo.domain.model.MovieDomain
import com.halimjr11.cameo.resources.R

class DiscoverMovieFragment : BaseFragment<FragmentDiscoverMovieBinding, ViewModel>(
    FragmentDiscoverMovieBinding::inflate
) {

    override fun setupUI() = with(binding) {
        val title = arguments?.getString(DISCOVER_MOVIE_TITLE)
        val image = arguments?.getString(DISCOVER_MOVIE_IMAGE)
        val genre = arguments?.getString(DISCOVER_MOVIE_GENRE)
        tvDiscoverTitle.text = title
        tvDiscoverHighlight.text = genre
        imgDiscover.load(
            data = buildString {
                append(IMAGE_URL)
                append(image)
            }
        ) {
            crossfade(true)
            placeholder(R.drawable.ic_placeholder)
            error(R.drawable.ic_placeholder)
        }
        super.setupUI()
    }

    companion object {
        private const val DISCOVER_MOVIE_TITLE = "discover_movie_title"
        private const val DISCOVER_MOVIE_IMAGE = "discover_movie_image"
        private const val DISCOVER_MOVIE_GENRE = "discover_movie_genre"


        @JvmStatic
        fun newInstance(movieDomain: MovieDomain): DiscoverMovieFragment {
            val args = Bundle().apply {
                putString(DISCOVER_MOVIE_TITLE, movieDomain.title)
                putString(DISCOVER_MOVIE_IMAGE, movieDomain.backdropUrl)
                putString(DISCOVER_MOVIE_GENRE, buildString {
                    append(movieDomain.language)
                    append(" • ")
                    append(movieDomain.releaseDate)
                })
            }
            val fragment = DiscoverMovieFragment()
            fragment.arguments = args
            return fragment
        }
    }
}