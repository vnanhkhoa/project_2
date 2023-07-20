package com.khoavna.asteroidradar.data.repositores.feed

import com.khoavna.asteroidradar.data.network.result.Result

fun interface FeedRepository {
    suspend fun getResultFeed(startDate: String, endDate: String): Result
}