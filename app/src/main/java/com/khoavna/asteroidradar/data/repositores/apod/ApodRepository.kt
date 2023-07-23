package com.khoavna.asteroidradar.data.repositores.apod

import com.khoavna.asteroidradar.data.network.result.ResultAPI
import com.khoavna.asteroidradar.data.network.wapi.apod.Apod

fun interface ApodRepository {
    suspend fun getResultApod(): ResultAPI<Apod>
}