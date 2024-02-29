package com.nurhaqhalim.cameo.view.main.navigation

import com.nurhaqhalim.cameo.core.base.BaseFragment
import com.nurhaqhalim.cameo.databinding.FragmentTransactionBinding
import com.nurhaqhalim.cameo.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class TransactionFragment :
    BaseFragment<FragmentTransactionBinding, MainViewModel>(FragmentTransactionBinding::inflate) {
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