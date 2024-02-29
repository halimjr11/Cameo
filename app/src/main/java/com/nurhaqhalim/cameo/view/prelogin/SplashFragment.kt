package com.nurhaqhalim.cameo.view.prelogin

import android.animation.ValueAnimator
import android.os.Looper
import android.view.View
import androidx.navigation.fragment.findNavController
import coil.load
import com.nurhaqhalim.cameo.R
import com.nurhaqhalim.cameo.core.base.BaseFragment
import com.nurhaqhalim.cameo.core.domain.state.doOnBoarding
import com.nurhaqhalim.cameo.core.domain.state.doOnLogin
import com.nurhaqhalim.cameo.core.domain.state.doOnMain
import com.nurhaqhalim.cameo.core.domain.state.doOnProfile
import com.nurhaqhalim.cameo.core.utils.launchAndCollectIn
import com.nurhaqhalim.cameo.databinding.FragmentSplashBinding
import com.nurhaqhalim.cameo.utils.toDrawable
import com.nurhaqhalim.cameo.viewmodel.PreLoginViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashFragment :
    BaseFragment<FragmentSplashBinding, PreLoginViewModel>(FragmentSplashBinding::inflate) {
    override val viewModel: PreLoginViewModel by viewModel()

    override fun initView() = with(binding) {
        ivSplashLogo.load(resources.toDrawable(R.drawable.ic_logo))
        animateView(ivSplashLogo, 0.2f, 1f, 2000L)
        viewModel.getOnboardingState()
    }

    override fun observeData() {
        with(viewModel) {
            onboarding.launchAndCollectIn(viewLifecycleOwner) { state ->
                android.os.Handler(Looper.getMainLooper()).postDelayed({
                    state.doOnBoarding {
                        findNavController().navigate(R.id.action_splashFragment_to_onboardingFragment)
                    }.doOnLogin {
                        findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
                    }.doOnProfile {
                        findNavController().navigate(R.id.action_splashFragment_to_profileFragment)
                    }.doOnMain {
                        findNavController().navigate(R.id.action_splashFragment_to_mainFragment)
                    }
                }, 3000L)
            }
        }
    }

    override fun initListener() = with(binding) {}

    private fun animateView(
        view: View,
        startValue: Float,
        endValue: Float,
        durationAnimation: Long = 1500
    ) {
        val animator = ValueAnimator.ofFloat(startValue, endValue)
        animator.duration = durationAnimation
        animator.addUpdateListener { animation ->
            val animatedValue = animation.animatedValue as Float
            view.run {
                scaleX = animatedValue
                scaleY = animatedValue
            }
        }
        animator.start()
    }
}