package com.halimjr11.cameo.view.feature.home.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.halimjr11.cameo.common.Constants.IMAGE_URL
import com.halimjr11.cameo.components.databinding.ItemListMovieBinding
import com.halimjr11.cameo.domain.model.MovieDomain
import com.halimjr11.cameo.view.feature.home.util.MovieDiffUtils

class RecommendedAdapter : ListAdapter<MovieDomain, RecommendedAdapter.RecommendedViewHolder>(
    MovieDiffUtils()
) {
    private var onCardClick: ((MovieDomain) -> Unit)? = null

    fun setOnClickCallback(action: ((MovieDomain) -> Unit)?) {
        onCardClick = action
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecommendedViewHolder = RecommendedViewHolder(
        ItemListMovieBinding.inflate(
            android.view.LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(
        holder: RecommendedViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position))
    }

    inner class RecommendedViewHolder(private val binding: ItemListMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: MovieDomain) = with(binding) {
            root.run {
                setMovieDetail(
                    title = movie.title,
                    rating = movie.rating.toString(),
                    imageUrl = buildString {
                        append(IMAGE_URL)
                        append(movie.posterUrl)
                    }
                )
                setOnClickListener {
                    onCardClick?.invoke(movie)
                }
            }
        }
    }
}