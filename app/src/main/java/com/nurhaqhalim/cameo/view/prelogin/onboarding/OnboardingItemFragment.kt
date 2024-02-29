package com.nurhaqhalim.cameo.view.prelogin.onboarding

import android.os.Bundle
import coil.load
import com.nurhaqhalim.cameo.core.base.BaseFragment
import com.nurhaqhalim.cameo.core.domain.model.DataOnboarding
import com.nurhaqhalim.cameo.databinding.FragmentOnboardingItemBinding
import com.nurhaqhalim.cameo.utils.toDrawable
import com.nurhaqhalim.cameo.viewmodel.PreLoginViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class OnboardingItemFragment : BaseFragment<FragmentOnboardingItemBinding, PreLoginViewModel>(
    FragmentOnboardingItemBinding::inflate
) {
    override val viewModel: PreLoginViewModel by viewModel()

    override fun initView() {
        with(binding) {
            arguments?.let {
                val data = it.getParcelable(dataOnboarding) ?: DataOnboarding()
                ivOnboardingItem.load(resources.toDrawable(data.image))
                tvOnboardingItem.text = data.text
            }
        }
    }

    override fun observeData() {}

    override fun initListener() = with(binding) {}

    companion object {
        const val dataOnboarding = "onboarding_data"
        fun newInstance(data: DataOnboarding): OnboardingItemFragment {
            val args = Bundle().apply {
                putParcelable(dataOnboarding, data)
            }
            val fragment = OnboardingItemFragment()
            fragment.arguments = args
            return fragment
        }
    }

}