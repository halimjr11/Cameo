package com.nurhaqhalim.cameo.view.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import com.nurhaqhalim.cameo.databinding.ActivitySplashBinding
import com.nurhaqhalim.cameo.view.MainActivity

class SplashActivity : AppCompatActivity() {
    private lateinit var splashBinding: ActivitySplashBinding
    private val countDown = 4000L
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        splashBinding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(splashBinding.root)

        val animationFadeIn = AnimationUtils.loadAnimation(this, resources.getIdentifier("fade_in", "anim", packageName))
        splashBinding.appLogo.apply {
            startAnimation(animationFadeIn)
        }
        Handler(mainLooper).postDelayed({
           val intent = Intent(this@SplashActivity, MainActivity::class.java)
           startActivity(intent)
        }, countDown)
    }
}