package com.khoavna.asteroidradar.data.network.service

interface ApiService<T> {
    suspend fun fetch() : T
    fun cancel()
}