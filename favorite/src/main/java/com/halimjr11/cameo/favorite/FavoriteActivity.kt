package com.halimjr11.cameo.favorite

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.halimjr11.cameo.common.UiState
import com.halimjr11.cameo.common.extension.launchAndCollect
import com.halimjr11.cameo.components.SpacingItemDecoration
import com.halimjr11.cameo.favorite.adapter.FavoriteAdapter
import com.halimjr11.cameo.favorite.databinding.ActivityFavoriteBinding
import com.halimjr11.cameo.favorite.di.loadFavoriteModule
import com.halimjr11.cameo.favorite.viewmodel.FavoriteViewModel
import com.halimjr11.cameo.navigation.CameoNavigation
import com.halimjr11.cameo.resources.R
import org.koin.android.ext.android.inject
import org.koin.android.scope.AndroidScopeComponent
import org.koin.androidx.scope.activityScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.scope.Scope

class FavoriteActivity : AppCompatActivity(), AndroidScopeComponent {
    private lateinit var binding: ActivityFavoriteBinding
    private val viewModel: FavoriteViewModel by viewModel()
    private val navigation: CameoNavigation by inject()
    private val favoriteAdapter: FavoriteAdapter by lazy {
        FavoriteAdapter()
    }
    override val scope: Scope by activityScope()

    init {
        loadFavoriteModule()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.mainFav) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupUI()
        setupListener()
        observeData()
    }

    private fun setupUI() = with(binding) {
        rvFavoriteRecommended.apply {
            adapter = favoriteAdapter
            layoutManager =
                LinearLayoutManager(this@FavoriteActivity, LinearLayoutManager.VERTICAL, false)
            addItemDecoration(
                SpacingItemDecoration(
                    spacing = resources.getDimensionPixelSize(R.dimen.spacing_small),
                    orientation = RecyclerView.VERTICAL,
                    includeEdge = true
                )
            )
            hasFixedSize()
        }
    }

    private fun setupListener() = with(binding) {
        favoriteAdapter.setOnClickCallback {
            navigation.goToDetailPage(this@FavoriteActivity, it.id)
        }
    }

    private fun observeData() = with(viewModel) {
        launchAndCollect(favoriteMovie) { state ->
            binding?.run {
                nestedScroll.isVisible = state is UiState.Success
                progressFavoriteLoading.isVisible = state is UiState.Loading
                errorFavoriteView.isVisible = state is UiState.Error
            }
            when (state) {
                is UiState.Success -> {
                    favoriteAdapter.submitList(state.data)
                }

                is UiState.Error -> binding.errorFavoriteView.setMessageAndCallback(state.message)
                else -> Unit
            }
        }
    }
}