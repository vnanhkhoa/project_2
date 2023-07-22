package com.khoavna.asteroidradar.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.khoavna.asteroidradar.data.database.AppDatabase
import com.khoavna.asteroidradar.data.database.entity.Asteroid
import com.khoavna.asteroidradar.data.datasource.asteroid.AsteroidDataSourceImpl
import com.khoavna.asteroidradar.data.datasource.feed.FeedDataSourceImpl
import com.khoavna.asteroidradar.data.network.APIConfig
import com.khoavna.asteroidradar.data.network.result.ResultAPI
import com.khoavna.asteroidradar.data.network.wapi.feed.ResponseFeed
import com.khoavna.asteroidradar.data.repositores.asteroid.AsteroidRepositoryImpl
import com.khoavna.asteroidradar.data.repositores.feed.FeedRepositoryImpl
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AsteroidWorkManager(
    context: Context,
    workerParameters: WorkerParameters
) : CoroutineWorker(context, workerParameters) {

    companion object {
        const val DATE_FORMAT = "yyyy-MM-dd"
        const val NUMBER_DATE = 7
    }

    private val dateFormat = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())

    private val feedRepository = FeedRepositoryImpl(
        feedDataSource = FeedDataSourceImpl(appAPI = APIConfig.getInstance().appService)
    )

    private val asteroidRepository = AsteroidRepositoryImpl(
        asteroidDataSource = AsteroidDataSourceImpl(
            AppDatabase.getInstance(context).getAsteroidDao()
        )
    )

    override suspend fun doWork(): Result {
        val date = getDate()
        val result = feedRepository.getResultFeed(
            startDate = date.first,
            endDate = date.second
        )
        return when (result) {
            is ResultAPI.SUCCESS<ResponseFeed> -> {
                result.data.nearEarthObjects.values.map {
                    it.map { nearEarthObject -> Asteroid.from(nearEarthObject = nearEarthObject) }
                }.flatten().also {
                    asteroidRepository.insertAll(*it.toTypedArray())
                }
                Result.success()
            }

            is ResultAPI.ERROR -> Result.retry()
        }

    }

    private fun getDate(): Pair<String, String> {
        val now = Calendar.getInstance()
        val startDate = dateFormat.format(now.time)

        now.add(Calendar.DAY_OF_YEAR, NUMBER_DATE)
        val endDate = dateFormat.format(now.time)
        return Pair(startDate, endDate)
    }
}