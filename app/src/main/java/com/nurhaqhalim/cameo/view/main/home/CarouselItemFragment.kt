package com.nurhaqhalim.cameo.view.main.home

import android.os.Bundle
import com.nurhaqhalim.cameo.core.base.BaseFragment
import com.nurhaqhalim.cameo.databinding.FragmentCarouselItemBinding
import com.nurhaqhalim.cameo.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CarouselItemFragment :
    BaseFragment<FragmentCarouselItemBinding, MainViewModel>(FragmentCarouselItemBinding::inflate) {
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

    companion object {
        fun newInstance(): CarouselItemFragment {
            val args = Bundle()

            val fragment = CarouselItemFragment()
            fragment.arguments = args
            return fragment
        }
    }

}