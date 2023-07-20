package com.khoavna.asteroidradar.data.datasource.asteroid

import com.khoavna.asteroidradar.data.database.entity.Asteroid
import kotlinx.coroutines.flow.StateFlow

interface AsteroidDataSource {
    suspend fun insert(asteroid: Asteroid)
    suspend fun insertAll(vararg asteroids: Asteroid)
    suspend fun getAsteroids(): StateFlow<List<Asteroid>>
    suspend fun update(asteroid: Asteroid)
    suspend fun delete(asteroid: Asteroid)
}