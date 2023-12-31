package com.khoavna.asteroidradar

import android.app.Application
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.khoavna.asteroidradar.worker.AsteroidWorkManager
import java.util.concurrent.TimeUnit

class MyApplication : Application() {

    private val workManager by lazy {
        WorkManager.getInstance(applicationContext)
    }
    private val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.UNMETERED)
        .setRequiresBatteryNotLow(true)
        .setRequiresCharging(true)
        .build()


    override fun onCreate() {
        super.onCreate()
        createWorkManager()
    }

    private fun createWorkManager() {
        val work = PeriodicWorkRequestBuilder<AsteroidWorkManager>(1, TimeUnit.DAYS)
            .setConstraints(constraints)
        workManager.enqueueUniquePeriodicWork(
            "CALL_API",
            ExistingPeriodicWorkPolicy.UPDATE,
            work.build()
        )
    }
}
