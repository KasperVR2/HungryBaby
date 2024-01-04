package com.example.hungrybaby.ui.home.food

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.hungrybaby.HungryBabyApplication
import com.example.hungrybaby.data.FoodRepository
import com.example.hungrybaby.model.Food
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FoodModel(private val foodRepo: FoodRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(FoodState())
    val uiState: StateFlow<FoodState> = _uiState.asStateFlow()
    lateinit var uiListState: StateFlow<FoodListState>
    var foodApiState: FoodApiState by mutableStateOf(FoodApiState.Loading)
        private set

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

    fun addFood() {
        setNewFoodVolume(50)
        viewModelScope.launch {
            foodRepo.addFood(Food(_uiState.value.newFoodVolume, _uiState.value.newFoodDate, _uiState.value.newFoodNote))
        }
    }

    fun setNewFoodVolume(volume: Int) {
        _uiState.update {
            it.copy(newFoodVolume = volume)
        }
    }

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
