package com.khoavna.asteroidradar.data.repositores.feed

import com.khoavna.asteroidradar.data.network.result.ResultAPI
import com.khoavna.asteroidradar.data.network.wapi.feed.ResponseFeed

fun interface FeedRepository {
    suspend fun getResultFeed(startDate: String, endDate: String): ResultAPI<ResponseFeed>
}