package com.khoavna.asteroidradar.domain.home

import com.khoavna.asteroidradar.data.database.entity.Asteroid
import com.khoavna.asteroidradar.data.network.result.ResultAPI
import com.khoavna.asteroidradar.data.network.wapi.apod.Apod
import com.khoavna.asteroidradar.data.repositores.apod.ApodRepository
import com.khoavna.asteroidradar.data.repositores.asteroid.AsteroidRepository
import kotlinx.coroutines.flow.Flow

class GetHomeUseCaseImpl(
    private val asteroidRepository: AsteroidRepository,
    private val apodRepository: ApodRepository
) : GetHomeUseCase {
    override fun executeGetAsteroid(): Flow<List<Asteroid>> {
        return asteroidRepository.getAsteroids()
    }

    override suspend fun executeGetApod(): ResultAPI<Apod> {
        return apodRepository.getResultApod()
    }
}