package com.halimjr11.cameo.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.DrawableRes
import coil.load
import com.google.android.material.card.MaterialCardView
import com.halimjr11.cameo.components.databinding.ItemMovieCardBinding
import com.halimjr11.cameo.resources.R

class MovieCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : MaterialCardView(context, attrs, defStyle) {

    private val binding: ItemMovieCardBinding =
        ItemMovieCardBinding.inflate(LayoutInflater.from(context), this, true)

    fun setMovieDetail(
        title: String,
        rating: String,
        imageUrl: String,
        @DrawableRes imageRes: Int = R.drawable.ic_placeholder
    ) = with(binding) {
        tvTitle.text = title
        tvRating.text = buildString {
            append("⭐ ")
            append(rating)
        }
        imgPoster.load(imageUrl) {
            crossfade(true)
            placeholder(imageRes)
            error(imageRes)
        }
    }
}
