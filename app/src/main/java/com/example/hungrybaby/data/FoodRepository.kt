package com.example.hungrybaby.data

import com.example.hungrybaby.data.database.FoodDao
import com.example.hungrybaby.data.database.asDbFood
import com.example.hungrybaby.data.database.asDomainFood
import com.example.hungrybaby.model.Food
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface FoodRepository {
    fun getFoodList(): Flow<List<Food>>

    suspend fun addFood(food: Food)

    // Since dateAndTime has unique constraint,
    // dateAndTime is enough to delete a food but volume is also included for safety
    suspend fun removeFoodWithParams(
        volume: Int,
        dateAndTime: String,
    )

    // For testing purposes, was kept in the final app in settings
    suspend fun removeAllFood()
}

// Implementation of FoodRepository
class FoodRepositoryImpl(private val foodDao: FoodDao) : FoodRepository {
    override fun getFoodList(): Flow<List<Food>> {
        return foodDao.getAllItems().map {
                list ->
            list.map { it.asDomainFood() }
        }
    }

    override suspend fun addFood(food: Food) {
        foodDao.insert(food.asDbFood())
    }

    override suspend fun removeFoodWithParams(
        volume: Int,
        dateAndTime: String,
    ) {
        foodDao.deleteWithParams(volume, dateAndTime)
    }

    override suspend fun removeAllFood() {
        foodDao.deleteAll()
    }
}
