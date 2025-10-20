package com.halimjr11.cameo.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.DrawableRes
import androidx.viewbinding.ViewBinding
import coil.load
import com.google.android.material.card.MaterialCardView
import com.halimjr11.cameo.components.databinding.ItemMovieCardBinding
import com.halimjr11.cameo.components.databinding.ItemMovieVerticalBinding
import com.halimjr11.cameo.resources.R

class MovieCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : MaterialCardView(context, attrs, defStyle) {

    private val binding: ViewBinding

    init {
        val inflater = LayoutInflater.from(context)
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.MovieCardView)
        val orientation = typedArray.getInt(R.styleable.MovieCardView_cardOrientation, 0)
        typedArray.recycle()

        // Inflate layout based on orientation
        binding = if (orientation == 1) {
            ItemMovieVerticalBinding.inflate(inflater, this, true)
        } else {
            ItemMovieCardBinding.inflate(inflater, this, true)
        }
    }

    fun setMovieDetail(
        title: String,
        rating: String,
        imageUrl: String,
        overview: String? = null,
        releaseDate: String? = null,
        @DrawableRes imageRes: Int = R.drawable.ic_placeholder
    ) {
        when (binding) {
            is ItemMovieCardBinding -> with(binding) {
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

            is ItemMovieVerticalBinding -> with(binding) {
                tvTitle.text = title
                tvRating.text = buildString {
                    append("⭐ ")
                    append(rating)
                }
                tvRelease.text = releaseDate
                tvOverview.text = overview
                imgPoster.load(imageUrl) {
                    crossfade(true)
                    placeholder(imageRes)
                    error(imageRes)
                }
            }
        }
    }
}

