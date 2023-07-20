package com.khoavna.asteroidradar.data.network.result

sealed class Result {
    data class SUCCESS<T>(val data: T) : Result()
    data class ERROR(val message: String) : Result()
}
