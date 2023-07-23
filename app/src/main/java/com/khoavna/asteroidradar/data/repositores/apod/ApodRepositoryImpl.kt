package com.khoavna.asteroidradar.data.repositores.apod

import com.khoavna.asteroidradar.data.datasource.apod.ApodDataSource
import com.khoavna.asteroidradar.data.network.result.ResultAPI
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ApodRepositoryImpl(
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val apodDataSource: ApodDataSource
) : ApodRepository {
    override suspend fun getResultApod() =
        withContext(dispatcher) {
            try {
                apodDataSource.getApod().let {
                    ResultAPI.SUCCESS(it)
                }
            } catch (e: Exception) {
                ResultAPI.ERROR(e.message ?: "Call API Error")
            }
        }
}