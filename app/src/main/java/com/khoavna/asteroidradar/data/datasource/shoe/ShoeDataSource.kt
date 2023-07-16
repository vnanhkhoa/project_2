package com.khoavna.shoestore.data.datasource.shoe

import com.khoavna.asteroidradar.data.model.Shoe
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface ShoeDataSource {
    fun findAll(): StateFlow<List<Shoe>>
    fun findOne(id: Int): Flow<Shoe?>
    fun delete(shoe: Shoe)
    fun insert(shoe: Shoe)
    fun update(shoe: Shoe)
}