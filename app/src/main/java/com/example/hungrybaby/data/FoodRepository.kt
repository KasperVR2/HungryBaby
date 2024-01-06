package com.example.hungrybaby.data

import com.example.hungrybaby.data.database.FoodDao
import com.example.hungrybaby.data.database.asDbFood
import com.example.hungrybaby.data.database.asDomainFood
import com.example.hungrybaby.model.Food
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface FoodRepository {
    fun getFoodList(): Flow<List<Food>>

    fun getFoodWithDate(date: String): Flow<List<Food>>

    suspend fun addFood(food: Food)

    suspend fun removeFood(food: Food)

    suspend fun removeFoodWithParams(
        volume: Int,
        dateAndTime: String,
    )

    suspend fun removeAllFood()

    suspend fun updateFood(food: Food)
}

class FoodRepositoryImpl(private val foodDao: FoodDao) : FoodRepository {
    override fun getFoodList(): Flow<List<Food>> {
        return foodDao.getAllItems().map {
                list ->
            list.map { it.asDomainFood() }
        }
    }

    override fun getFoodWithDate(date: String): Flow<List<Food>> {
        return foodDao.getFoodWithDate(date).map {
                list ->
            list.map { it.asDomainFood() }
        }
    }

    override suspend fun addFood(food: Food) {
        foodDao.insert(food.asDbFood())
    }

    override suspend fun removeFood(food: Food) {
        foodDao.delete(food.asDbFood())
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

    override suspend fun updateFood(food: Food) {
        foodDao.update(food.asDbFood())
    }
}
