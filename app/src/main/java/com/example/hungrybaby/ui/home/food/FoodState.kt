package com.example.hungrybaby.ui.home.food

import com.example.hungrybaby.model.Food
import java.util.Date

data class FoodState(
    // val currentTaskList: List<Task>,
    val newFoodVolume: Int = 0,
    val newFoodDate: String = Date().toString(),
    val newFoodNote: String = "",
    val scrollActionIdx: Int = 0,
    val scrollToItemIndex: Int = 0,
)

data class FoodListState(val foodList: List<Food> = listOf())

sealed interface FoodApiState {
    object Success : FoodApiState

    object Error : FoodApiState

    object Loading : FoodApiState
}
