package com.khoavna.asteroidradar.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.khoavna.asteroidradar.R
import com.khoavna.asteroidradar.data.network.APIConfig
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lifecycleScope.launch {
            try {
                val a = APIConfig.getInstance().appService.getAsteroid(
                    startDate = "2023-07-17",
                    endDate = "2023-07-24"
                )

                Log.e("KHOAVNA1", "onCreate: ${a.body()}")
            } catch (e : Exception) {
                Log.e("KHOAVNA1", "onCreate: ${e.message}")
            }
        }
    }
}