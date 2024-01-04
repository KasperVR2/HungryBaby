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

    @Query("SELECT * from foodintake ORDER BY dateAndTime ASC")
    fun getAllItems(): Flow<List<dbFood>>
}
