package com.khoavna.shoestore.data.repositores.shoe

import com.khoavna.asteroidradar.data.model.Shoe
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface ShoeRepository {
    fun getShoes(): StateFlow<List<Shoe>>
    fun getShoe(id: Int): Flow<Shoe?>
    fun delete(shoe: Shoe)
    fun insert(shoe: Shoe)
    fun update(shoe: Shoe)
}