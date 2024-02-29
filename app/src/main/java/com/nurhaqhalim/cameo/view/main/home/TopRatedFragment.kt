package com.nurhaqhalim.cameo.view.main.home

import com.nurhaqhalim.cameo.core.base.BaseFragment
import com.nurhaqhalim.cameo.databinding.FragmentTopRatedBinding
import com.nurhaqhalim.cameo.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class TopRatedFragment :
    BaseFragment<FragmentTopRatedBinding, MainViewModel>(FragmentTopRatedBinding::inflate) {
    override val viewModel: MainViewModel by viewModel()

    override fun initView() {
        with(binding) {

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