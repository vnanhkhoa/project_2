package com.khoavna.asteroidradar.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.khoavna.asteroidradar.data.database.entity.Asteroid
import kotlinx.coroutines.flow.StateFlow

@Dao
interface AsteroidDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(asteroid: Asteroid)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg asteroid: Asteroid)

    @Query("Select * from `asteroid`")
    suspend fun getAll(): StateFlow<List<Asteroid>>

    @Update
    suspend fun update(asteroid: Asteroid)

    @Delete
    suspend fun delete(asteroid: Asteroid)
}