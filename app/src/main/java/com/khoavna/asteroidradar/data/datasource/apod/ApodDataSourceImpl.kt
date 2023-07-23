package com.khoavna.asteroidradar.data.datasource.apod

import com.khoavna.asteroidradar.data.network.AppAPI
import com.khoavna.asteroidradar.data.network.service.ApiServiceImpl
import com.khoavna.asteroidradar.data.network.wapi.apod.Apod
import com.khoavna.asteroidradar.data.network.wapi.feed.ResponseFeed

class ApodDataSourceImpl(private val appAPI: AppAPI) : ApodDataSource {
    override suspend fun getApod(): Apod =
        ApiServiceImpl(call = appAPI.getApod()).fetch()
}