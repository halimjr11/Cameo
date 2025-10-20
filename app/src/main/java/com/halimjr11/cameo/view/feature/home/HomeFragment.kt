package com.halimjr11.cameo.view.feature.home

import android.content.Intent
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.halimjr11.cameo.common.Constants.FAVORITE_URI_SCHEME
import com.halimjr11.cameo.common.UiState
import com.halimjr11.cameo.common.launchAndCollect
import com.halimjr11.cameo.components.BaseFragment
import com.halimjr11.cameo.components.SpacingItemDecoration
import com.halimjr11.cameo.databinding.FragmentHomeBinding
import com.halimjr11.cameo.domain.model.MovieDomain
import com.halimjr11.cameo.resources.R
import com.halimjr11.cameo.view.feature.home.adapter.DiscoverAdapter
import com.halimjr11.cameo.view.feature.home.adapter.RecommendedAdapter
import com.halimjr11.cameo.view.feature.home.di.loadHomeModule
import com.halimjr11.cameo.view.feature.home.viewmodel.HomeViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import org.koin.android.scope.AndroidScopeComponent
import org.koin.androidx.scope.fragmentScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.scope.Scope

class HomeFragment :
    BaseFragment<FragmentHomeBinding, HomeViewModel>(FragmentHomeBinding::inflate),
    AndroidScopeComponent {
    override val viewModel: HomeViewModel by viewModel()
    override val scope: Scope by fragmentScope()
    private val recommendedAdapter: RecommendedAdapter by lazy {
        RecommendedAdapter()
    }
    private var autoSlideJob: Job? = null

    init {
        loadHomeModule()
    }

    override fun setupUI() = with(binding) {
        rvRecommended.apply {
            adapter = recommendedAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            addItemDecoration(
                SpacingItemDecoration(
                    spacing = resources.getDimensionPixelSize(R.dimen.spacing_small),
                    orientation = RecyclerView.HORIZONTAL,
                    includeEdge = true
                )
            )
            hasFixedSize()
        }
        super.setupUI()
    }

    override fun setupListeners() = with(binding) {
        recommendedAdapter.setOnClickCallback {
            val action = HomeFragmentDirections.actionHomeFragmentToMovieDetailFragment(it.id)
            findNavController().navigate(action)
        }
        ibActionFav.setOnClickListener {
            val uri = FAVORITE_URI_SCHEME.toUri()
            startActivity(Intent(Intent.ACTION_VIEW, uri))
        }
        super.setupListeners()
    }


    override fun observeData() = with(viewModel) {
        launchAndCollect(homeMovie) { state ->
            binding.run {
                nestedScroll.isVisible = state is UiState.Success
                errorHomeView.isVisible = state is UiState.Error
                progressHomeLoading.isVisible = state is UiState.Loading
                when (state) {
                    is UiState.Success -> {
                        val (discover, recommended) = state.data
                        recommendedAdapter.submitList(recommended)
                        setupViewPager(discover)
                    }

                    is UiState.Error -> {
                        errorHomeView.setMessageAndCallback(state.message) {
                            viewModel.getHomeMovie()
                        }
                    }

                    else -> Unit
                }
            }

        }
        super.observeData()
    }

    private fun setupViewPager(data: List<MovieDomain>) = with(binding) {
        val discoverAdapter =
            DiscoverAdapter(childFragmentManager, viewLifecycleOwner.lifecycle, data)
        vpDiscoverMovie.apply {
            adapter = discoverAdapter
            offscreenPageLimit = 3
            startAutoSlide(this)
        }

    }

    override fun onDestroyView() {
        binding.vpDiscoverMovie.adapter = null
        binding.rvRecommended.adapter = null
        autoSlideJob?.cancel()
        super.onDestroyView()
    }

    private fun startAutoSlide(viewPager: ViewPager2) {
        autoSlideJob = viewLifecycleOwner.lifecycleScope.launch {
            while (isActive) {
                val itemCount = viewPager.adapter?.itemCount ?: 0
                if (itemCount > 0) {
                    delay(3000L)
                    val nextItem = (viewPager.currentItem + 1) % itemCount
                    viewPager.setCurrentItem(nextItem, true)
                }
            }
        }
    }
}

