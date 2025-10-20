package com.halimjr11.cameo.favorite.utils

import androidx.recyclerview.widget.DiffUtil
import com.halimjr11.cameo.domain.model.MovieDomain

class MovieDiffUtils : DiffUtil.ItemCallback<MovieDomain>() {
    override fun areItemsTheSame(oldItem: MovieDomain, newItem: MovieDomain): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MovieDomain, newItem: MovieDomain): Boolean {
        return oldItem == newItem
    }
}