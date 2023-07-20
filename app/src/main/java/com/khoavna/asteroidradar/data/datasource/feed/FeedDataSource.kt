package com.khoavna.asteroidradar.data.datasource.feed

import com.khoavna.asteroidradar.data.network.wapi.feed.ResponseFeed

fun interface FeedDataSource {
    suspend fun getResponseFeed(startDate: String, endDate: String): ResponseFeed
}