package com.khoavna.asteroidradar.data.network

import com.khoavna.asteroidradar.data.network.wapi.feed.ResponseFeed
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

fun interface AppAPI {

    @GET("/neo/rest/v1/feed")
    fun getAsteroid(
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String
    ): Call<ResponseFeed>
}