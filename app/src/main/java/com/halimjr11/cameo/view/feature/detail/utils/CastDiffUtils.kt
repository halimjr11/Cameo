package com.halimjr11.cameo.view.feature.detail.utils

import androidx.recyclerview.widget.DiffUtil
import com.halimjr11.cameo.domain.model.MovieDetailDomain

class CastDiffUtils : DiffUtil.ItemCallback<MovieDetailDomain.CastDomain>() {
    override fun areItemsTheSame(
        oldItem: MovieDetailDomain.CastDomain,
        newItem: MovieDetailDomain.CastDomain
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: MovieDetailDomain.CastDomain,
        newItem: MovieDetailDomain.CastDomain
    ): Boolean {
        return oldItem == newItem
    }
}