package com.halimjr11.cameo.view.feature.detail

import android.graphics.PorterDuff
import androidx.activity.addCallback
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.google.android.material.color.MaterialColors
import com.halimjr11.cameo.common.Constants.IMAGE_URL
import com.halimjr11.cameo.common.UiState
import com.halimjr11.cameo.common.launchAndCollect
import com.halimjr11.cameo.components.BaseFragment
import com.halimjr11.cameo.databinding.FragmentMovieDetailBinding
import com.halimjr11.cameo.domain.model.MovieDetailDomain
import com.halimjr11.cameo.resources.R
import com.halimjr11.cameo.view.feature.detail.adapter.CastAdapter
import com.halimjr11.cameo.view.feature.detail.di.loadDetailModule
import com.halimjr11.cameo.view.feature.detail.viewmodel.DetailViewModel
import org.koin.android.scope.AndroidScopeComponent
import org.koin.androidx.scope.fragmentScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.scope.Scope
import com.google.android.material.R as MaterialRes

class MovieDetailFragment : BaseFragment<FragmentMovieDetailBinding, DetailViewModel>(
    FragmentMovieDetailBinding::inflate
), AndroidScopeComponent {
    override val viewModel: DetailViewModel by viewModel()
    override val scope: Scope by fragmentScope()
    private val castAdapter: CastAdapter by lazy {
        CastAdapter()
    }
    private val args: MovieDetailFragmentArgs by navArgs()

    init {
        loadDetailModule()
    }

    override fun setupUI() = with(binding) {
        rvCast.apply {
            adapter = castAdapter
            layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            hasFixedSize()
        }
        super.setupUI()
    }

    override fun setupListeners() = with(binding) {
        btnBack.setOnClickListener {
            if (args.isFromFavorite) activity?.finish() else findNavController().popBackStack()
        }
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner) {
            if (args.isFromFavorite) activity?.finish() else findNavController().popBackStack()
        }
        super.setupListeners()
    }

    override fun observeData() = with(viewModel) {
        getDetailMovie(args.idMovie)
        launchAndCollect(detailMovie) { state ->
            binding.run {
                nestedContent.isVisible = state is UiState.Success
                progressLoading.isVisible = state is UiState.Loading
                viewError.isVisible = state is UiState.Error
                when (state) {
                    is UiState.Success -> {
                        setupDetail(state.data)
                    }

                    is UiState.Error -> {
                        viewError.setMessageAndCallback(state.message) {
                            viewModel.getDetailMovie(args.idMovie)
                        }
                    }

                    else -> Unit
                }

            }
        }
        launchAndCollect(isFavorite) {
            val color =
                if (it) MaterialRes.attr.colorSecondary else MaterialRes.attr.colorOnSurface
            binding.btnFavorite.apply {
                setColorFilter(
                    MaterialColors.getColor(this@apply, color),
                    PorterDuff.Mode.SRC_IN
                )
            }
        }
        super.observeData()
    }

    private fun setupDetail(data: MovieDetailDomain) = with(binding) {
        imgBackdrop.load(
            data = buildString {
                append(IMAGE_URL)
                append(data.backdropUrl)
            }
        )
        tvTitle.text = data.title
        tvStory.text = data.overview
        tvDuration.text = buildString {
            append(resources.getString(R.string.detail_duration))
            append(data.runtime)
        }
        tvReleaseDate.text = buildString {
            append(resources.getString(R.string.detail_release))
            append(data.releaseDate)
        }
        tvRating.text = buildString {
            append("⭐ ")
            append(data.rating)
            append(" ")
            append(resources.getString(R.string.rating_tmdb))
        }
        tvLanguage.text = buildString {
            append(resources.getString(R.string.detail_language_available))
            append(data.spokenLanguages.joinToString(", "))
        }
        btnFavorite.setOnClickListener {
            viewModel.toggleFavorite(data)
        }
        castAdapter.submitList(data.cast)
    }

}