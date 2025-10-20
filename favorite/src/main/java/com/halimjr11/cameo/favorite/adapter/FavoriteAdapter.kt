package com.halimjr11.cameo.favorite.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.halimjr11.cameo.common.Constants.IMAGE_URL
import com.halimjr11.cameo.domain.model.MovieDomain
import com.halimjr11.cameo.favorite.databinding.ItemListFavoriteBinding
import com.halimjr11.cameo.favorite.utils.MovieDiffUtils

class FavoriteAdapter : ListAdapter<MovieDomain, FavoriteAdapter.FavoriteViewHolder>(
    MovieDiffUtils()
) {
    private var onCardClick: ((MovieDomain) -> Unit)? = null

    fun setOnClickCallback(action: ((MovieDomain) -> Unit)?) {
        onCardClick = action
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoriteViewHolder = FavoriteViewHolder(
        ItemListFavoriteBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(
        holder: FavoriteViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position))
    }

    inner class FavoriteViewHolder(private val binding: ItemListFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: MovieDomain) = with(binding) {
            root.run {
                setMovieDetail(
                    title = movie.title,
                    rating = movie.rating.toString(),
                    imageUrl = buildString {
                        append(IMAGE_URL)
                        append(movie.posterUrl)
                    },
                    releaseDate = movie.releaseDate,
                    overview = movie.overview
                )
                setOnClickListener {
                    onCardClick?.invoke(movie)
                }
            }
        }
    }
}