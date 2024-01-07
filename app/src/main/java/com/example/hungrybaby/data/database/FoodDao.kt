package com.example.hungrybaby.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: dbFood)

    @Query("DELETE FROM foodintake WHERE volume = :volume AND dateAndTime = :dateAndTime")
    suspend fun deleteWithParams(
        volume: Int,
        dateAndTime: String,
    )

    @Query("DELETE FROM foodintake")
    suspend fun deleteAll()

    // Most recent first
    @Query("SELECT * from foodintake ORDER BY dateAndTime DESC")
    fun getAllItems(): Flow<List<dbFood>>
}
