package com.khoavna.asteroidradar.data.datasource.apod

import com.khoavna.asteroidradar.data.network.wapi.apod.Apod

fun interface ApodDataSource {
    suspend fun getApod(): Apod
}