package com.nurhaqhalim.cameo.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.nurhaqhalim.cameo.R
import com.nurhaqhalim.cameo.databinding.ActivityMainBinding
import com.nurhaqhalim.cameo.view.home.AccountFragment
import com.nurhaqhalim.cameo.view.home.FavoriteFragment
import com.nurhaqhalim.cameo.view.home.HomeFragment
import com.nurhaqhalim.cameo.view.home.SearchFragment

class MainActivity : AppCompatActivity() {
    private lateinit var mainBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        mainBinding.bottomNav.apply {
            if(selectedItemId == R.id.home){
                loadFragment(HomeFragment())
            }
            setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.home -> {
                        loadFragment(HomeFragment())
                    }
                    R.id.search -> {
                        loadFragment(SearchFragment())
                    }
                    R.id.favorite -> {
                        loadFragment(FavoriteFragment())
                    }
                    R.id.account -> {
                        loadFragment(AccountFragment())
                    }
                }
                true
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(mainBinding.content.id, fragment)
        transaction.commit()
    }
}