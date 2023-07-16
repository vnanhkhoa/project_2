package com.khoavna.shoestore.data.repositores.shoe

import com.khoavna.shoestore.data.datasource.shoe.ShoeDataSource
import com.khoavna.asteroidradar.data.model.Shoe
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

class ShoeRepositoryIml(private val shoeDataSource: ShoeDataSource) : ShoeRepository {

    override fun getShoes(): StateFlow<List<Shoe>> = shoeDataSource.findAll()

    override fun getShoe(id: Int): Flow<Shoe?> = shoeDataSource.findOne(id = id)

    override fun delete(shoe: Shoe) {
        shoeDataSource.delete(shoe)
    }

    override fun insert(shoe: Shoe) {
        shoeDataSource.insert(shoe)
    }

    override fun update(shoe: Shoe) {
        shoeDataSource.update(shoe)
    }
}