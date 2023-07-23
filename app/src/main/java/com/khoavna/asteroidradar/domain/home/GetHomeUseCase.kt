package com.khoavna.asteroidradar.domain.home

import com.khoavna.asteroidradar.data.database.entity.Asteroid
import com.khoavna.asteroidradar.data.network.result.ResultAPI
import com.khoavna.asteroidradar.data.network.wapi.apod.Apod
import kotlinx.coroutines.flow.Flow

interface GetHomeUseCase {
    fun executeGetAsteroid(): Flow<List<Asteroid>>
    suspend fun executeGetApod(): ResultAPI<Apod>
}