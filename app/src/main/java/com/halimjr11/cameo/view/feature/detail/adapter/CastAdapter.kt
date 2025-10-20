package com.halimjr11.cameo.view.feature.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.halimjr11.cameo.common.Constants.IMAGE_URL
import com.halimjr11.cameo.databinding.ItemCastMovieBinding
import com.halimjr11.cameo.domain.model.MovieDetailDomain
import com.halimjr11.cameo.view.feature.detail.utils.CastDiffUtils

class CastAdapter : ListAdapter<MovieDetailDomain.CastDomain, CastAdapter.CastViewHolder>(
    CastDiffUtils()
) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CastViewHolder = CastViewHolder(
        ItemCastMovieBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(
        holder: CastViewHolder,
        position: Int
    ) {
        holder.bindView(getItem(position))
    }

    inner class CastViewHolder(
        private val binding: ItemCastMovieBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindView(cast: MovieDetailDomain.CastDomain) = with(binding) {
            imgActor.load(data = buildString {
                append(IMAGE_URL)
                append(cast.profileUrl)
            })
        }
    }
}