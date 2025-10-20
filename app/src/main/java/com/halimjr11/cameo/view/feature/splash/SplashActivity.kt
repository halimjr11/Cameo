package com.halimjr11.cameo.view.feature.splash

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.view.animation.OvershootInterpolator
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.addListener
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.halimjr11.cameo.databinding.ActivitySplashBinding
import com.halimjr11.cameo.view.feature.main.MainActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        animateLogo()
    }

    private fun animateLogo() = with(binding) {
        splashLogo.alpha = 0f
        splashLogo.scaleX = 0.7f
        splashLogo.scaleY = 0.7f
        splashLogo.rotation = -10f

        val fadeIn = ObjectAnimator.ofFloat(splashLogo, "alpha", 0f, 1f)
        val rotate = ObjectAnimator.ofFloat(splashLogo, "rotation", -10f, 0f)
        val scaleXIn = ObjectAnimator.ofFloat(splashLogo, "scaleX", 0.7f, 1f)
        val scaleYIn = ObjectAnimator.ofFloat(splashLogo, "scaleY", 0.7f, 1f)

        val enterSet = AnimatorSet().apply {
            playTogether(fadeIn, rotate, scaleXIn, scaleYIn)
            duration = 1400
            interpolator = OvershootInterpolator(1.2f)
        }

        val glowUp = ObjectAnimator.ofFloat(splashLogo, "alpha", 1f, 0.85f, 1f)
        val scaleUpX = ObjectAnimator.ofFloat(splashLogo, "scaleX", 1f, 1.07f, 1f)
        val scaleUpY = ObjectAnimator.ofFloat(splashLogo, "scaleY", 1f, 1.07f, 1f)

        val pulseSet = AnimatorSet().apply {
            playTogether(glowUp, scaleUpX, scaleUpY)
            duration = 1000
            interpolator = AccelerateDecelerateInterpolator()
            startDelay = 300
        }

        val moveUp = ObjectAnimator.ofFloat(splashLogo, "translationY", 0f, -60f)
        val fadeOut = ObjectAnimator.ofFloat(splashLogo, "alpha", 1f, 0f)

        val exitSet = AnimatorSet().apply {
            playTogether(moveUp, fadeOut)
            duration = 900
            startDelay = 2000
            interpolator = DecelerateInterpolator()
        }

        AnimatorSet().apply {
            playSequentially(enterSet, pulseSet, exitSet)
            start()
            addListener(onEnd = {
                lifecycleScope.launch {
                    delay(300)
                    startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                    finish()
                }
            })
        }
    }

}