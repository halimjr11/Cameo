package com.nurhaqhalim.cameo.view.prelogin.onboarding.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.nurhaqhalim.cameo.core.domain.model.DataOnboarding
import com.nurhaqhalim.cameo.view.prelogin.onboarding.OnboardingItemFragment

class OnBoardingAdapter(fm: FragmentManager, lifecycle: Lifecycle, val list: List<DataOnboarding>) :
    FragmentStateAdapter(fm, lifecycle) {
    override fun getItemCount(): Int = list.size

    override fun createFragment(position: Int): Fragment {
        return OnboardingItemFragment.newInstance(list[position])
    }
}
