package com.example.hungrybaby.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: dbFood)

    @Update
    suspend fun update(item: dbFood)

    @Delete
    suspend fun delete(item: dbFood)

    @Query("DELETE FROM foodintake WHERE volume = :volume AND dateAndTime = :dateAndTime")
    suspend fun deleteWithParams(
        volume: Int,
        dateAndTime: String,
    )

    @Query("DELETE FROM foodintake")
    suspend fun deleteAll()

    @Query("SELECT * FROM foodintake WHERE volume = :volume AND dateAndTime = :dateAndTime LIMIT 1")
    fun getFood(
        volume: Int,
        dateAndTime: String,
    ): Flow<dbFood>

    @Query("SELECT * from foodintake ORDER BY dateAndTime ASC")
    fun getAllItems(): Flow<List<dbFood>>
}
