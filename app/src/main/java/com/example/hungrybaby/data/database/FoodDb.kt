package com.example.hungrybaby.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [dbFood::class], version = 1)
abstract class FoodDb : RoomDatabase() {
    abstract fun foodDao(): FoodDao

    companion object {
        @Volatile
        private var Instance: FoodDb? = null

        fun getDatabase(context: Context): FoodDb {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, FoodDb::class.java, "food_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}
