package com.khoavna.asteroidradar.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Shoe(
    val id: Int = 0,
    val name: String,
    val price: Int,
    val description: String,
) : Parcelable
