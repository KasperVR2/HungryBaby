package com.example.hungrybaby.data

import android.content.Context
import com.example.hungrybaby.data.database.FoodDb

interface AppContainer {
    val foodRepository: FoodRepository
}

class DefaultAppContainer(private val context: Context) : AppContainer {
    override val foodRepository: FoodRepository by lazy {
        FoodRepositoryImpl(FoodDb.getDatabase(context = context).foodDao())
    }
}
