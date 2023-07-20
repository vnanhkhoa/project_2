package com.khoavna.asteroidradar.data.network.wapi.feed

import com.google.gson.annotations.SerializedName
import com.khoavna.asteroidradar.data.network.wapi.feed.link.Links
import com.khoavna.asteroidradar.data.network.wapi.feed.nearearthobject.NearEarthObject

data class ResponseFeed(
    @SerializedName(value = "element_count")
    val elementCount: Int,
    val links: Links,
    @SerializedName(value = "near_earth_objects")
    val nearEarthObjects : Map<String, List<NearEarthObject>>
)