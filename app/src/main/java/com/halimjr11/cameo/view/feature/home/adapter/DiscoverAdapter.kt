package com.halimjr11.cameo.view.feature.home.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.halimjr11.cameo.domain.model.MovieDomain
import com.halimjr11.cameo.view.feature.home.DiscoverMovieFragment

class DiscoverAdapter(
    private val fragmentManager: FragmentManager,
    private val lifecycle: Lifecycle,
    private val movies: List<MovieDomain>
) : FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun createFragment(position: Int): Fragment {
        return DiscoverMovieFragment.newInstance(movies[position])
    }

    override fun getItemCount(): Int = movies.size
}