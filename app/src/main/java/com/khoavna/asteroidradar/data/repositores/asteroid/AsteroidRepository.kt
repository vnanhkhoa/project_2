package com.khoavna.asteroidradar.data.repositores.asteroid

import com.khoavna.asteroidradar.data.database.entity.Asteroid
import kotlinx.coroutines.flow.Flow

interface AsteroidRepository {
    suspend fun insert(asteroid: Asteroid)
    suspend fun insertAll(vararg asteroids: Asteroid)
    fun getAsteroids(): Flow<List<Asteroid>>
    suspend fun update(asteroid: Asteroid)
    suspend fun delete(asteroid: Asteroid)
}