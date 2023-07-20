package com.khoavna.asteroidradar.data.datasource.feed

import com.khoavna.asteroidradar.data.network.AppAPI
import com.khoavna.asteroidradar.data.network.service.ApiServiceImpl
import com.khoavna.asteroidradar.data.network.wapi.feed.ResponseFeed

class FeedDataSourceImpl(private val appAPI: AppAPI) : FeedDataSource {
    override suspend fun getResponseFeed(startDate: String, endDate: String): ResponseFeed =
        ApiServiceImpl(call = appAPI.getAsteroid(startDate, endDate)).fetch()
}