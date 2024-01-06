package com.example.hungrybaby.ui.home.food

import com.example.hungrybaby.model.Food

data class FoodState(
    // val currentTaskList: List<Task>,
    val newFoodVolume: Int = 0,
    val newFoodDate: String = "",
    val newFoodNote: String = "",
)

data class FoodListState(val foodList: List<Food> = listOf())

sealed interface FoodApiState {
    object Success : FoodApiState

    object Error : FoodApiState

    object Loading : FoodApiState
}
