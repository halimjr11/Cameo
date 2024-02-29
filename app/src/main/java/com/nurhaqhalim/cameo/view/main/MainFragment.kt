package com.nurhaqhalim.cameo.view.main

import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.nurhaqhalim.cameo.R
import com.nurhaqhalim.cameo.core.base.BaseFragment
import com.nurhaqhalim.cameo.databinding.FragmentMainBinding
import com.nurhaqhalim.cameo.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment :
    BaseFragment<FragmentMainBinding, MainViewModel>(FragmentMainBinding::inflate) {
    private lateinit var navController: NavController
    override val viewModel: MainViewModel by viewModel()

    override fun initView() {
        with(binding) {
            mtMain.apply {
                title = resources.getString(R.string.app_name)
                subtitle = context.getString(R.string.greetings_text)
                    .replace("%name%", viewModel.getDisplayName())
            }
            val navHostFragment =
                childFragmentManager.findFragmentById(mainHomeFragment.id) as NavHostFragment
            navController = navHostFragment.navController
            bottomNavMainView.setupWithNavController(navController)
        }
    }

    override fun observeData() {
        with(viewModel) {

        }
    }

    override fun initListener() {
        with(binding) {

        }
    }

}