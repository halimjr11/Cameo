package com.nurhaqhalim.cameo.view.prelogin.onboarding

import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.nurhaqhalim.cameo.R
import com.nurhaqhalim.cameo.core.base.BaseFragment
import com.nurhaqhalim.cameo.core.domain.model.DataOnboarding
import com.nurhaqhalim.cameo.databinding.FragmentOnboardingBinding
import com.nurhaqhalim.cameo.utils.gone
import com.nurhaqhalim.cameo.utils.visible
import com.nurhaqhalim.cameo.view.prelogin.onboarding.adapter.OnBoardingAdapter
import com.nurhaqhalim.cameo.viewmodel.PreLoginViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class OnboardingFragment :
    BaseFragment<FragmentOnboardingBinding, PreLoginViewModel>(FragmentOnboardingBinding::inflate) {
    override val viewModel: PreLoginViewModel by viewModel()

    override fun initView() = with(binding) {
        val list = listOf(
            DataOnboarding(
                getString(R.string.intimacy_text), R.drawable.ic_intimacy
            ), DataOnboarding(
                getString(R.string.convenience_text), R.drawable.ic_enjoy

            ), DataOnboarding(
                getString(R.string.enjoy_text), R.drawable.ic_convenience
            )
        )
        onBoardingViewPager.adapter = OnBoardingAdapter(parentFragmentManager, lifecycle, list)
        TabLayoutMediator(onBoardingDotIndicator, onBoardingViewPager) { _, _ -> }.attach()
        onBoardingViewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                if (position == list.size - 1) {
                    onBoardingNextText.gone()
                } else {
                    onBoardingNextText.visible()
                }
            }
        })
        viewModel.setOnBoarding(true)
    }

    override fun observeData() {
        with(viewModel) {

        }
    }

    override fun initListener() = with(binding) {
        btnOnBoardingRegister.setOnClickListener {
            findNavController().navigate(R.id.action_onboardingFragment_to_registerFragment)
        }
        onBoardingNextText.setOnClickListener {
            val position = onBoardingDotIndicator.selectedTabPosition
            onBoardingDotIndicator.selectTab(onBoardingDotIndicator.getTabAt(position + 1))
        }
        onBoardingSkipText.setOnClickListener {
            findNavController().navigate(R.id.action_onboardingFragment_to_loginFragment)
        }
    }

}