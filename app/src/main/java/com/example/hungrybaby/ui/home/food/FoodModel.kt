package com.example.hungrybaby.ui.home.food

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.hungrybaby.HungryBabyApplication
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class FoodModel(private val foodRepo: FoodRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(FoodState())
    val uiState: StateFlow<FoodState> = _uiState.asStateFlow()
    lateinit var uiListState: StateFlow<FoodListState>
    var foodApiState: FoodApiState by mutableStateOf(FoodApiState.Loading)
        private set

    /*
    init {
        getRepoFood()
    }

    private fun getRepoFood() {
        try {
            uiListState =
                foodRepo.getFoodList().map { FoodListState(it) }
                    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), FoodListState())
            foodApiState = FoodApiState.Success
        } catch (e: Exception) {
            println("Error: $e")
        }
    }
     */

    companion object {
        val Factory: ViewModelProvider.Factory =
            viewModelFactory {
                initializer {
                    val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as HungryBabyApplication)
                    val foodRepository = application.container.foodRepository
                    FoodModel(foodRepo = foodRepository)
                }
            }
    }
}
