package com.khoavna.asteroidradar.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.khoavna.asteroidradar.R
import com.khoavna.asteroidradar.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private lateinit var navController: NavController
    private val appBarConfig = AppBarConfiguration(
        setOf(R.id.homeFragment)
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        navController = supportFragmentManager.findFragmentById(R.id.nav_host_fragment).let {
            (it as NavHostFragment).findNavController()
        }

        setupActionBarWithNavController(navController, appBarConfig)
    }
}