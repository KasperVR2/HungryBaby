package com.example.hungrybaby.ui.home

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
import com.example.hungrybaby.ui.home.food.FoodApiState
import com.example.hungrybaby.ui.home.food.FoodListState
import com.example.hungrybaby.ui.home.food.FoodState
import com.example.hungrybaby.ui.news.news.NewsListState
import com.example.hungrybaby.ui.news.news.NewsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeModel(private val foodRepo: FoodRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(FoodState())
    val uiState: StateFlow<FoodState> = _uiState.asStateFlow()
    lateinit var uiListState: StateFlow<FoodListState>

    private val _newsState = MutableStateFlow(NewsState())
    val newsState: StateFlow<NewsState> = _newsState.asStateFlow()
    lateinit var newsListState: StateFlow<NewsListState>

    var foodApiState: FoodApiState by mutableStateOf(FoodApiState.Loading)
        private set

    init {
        getRepoFood()

        /* VERDER AFWERKEN
        viewModelScope.launch {
            newsListState = NewsService().map { NewsListState(it) }
                    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), NewsListState())
        }
         */
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

    fun checkFoodTime(time: String): Boolean {
        var food: StateFlow<FoodListState> = MutableStateFlow(FoodListState())
        try {
            viewModelScope.launch {
                food =
                    foodRepo.getFoodWithDate(time).map { FoodListState(it) }
                        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), FoodListState())
            }
        } catch (e: Exception) {
            println("Error: $e")
        }
        return food.value.foodList.isEmpty()
    }

    fun addFood() {
        viewModelScope.launch {
            foodRepo.addFood(Food(_uiState.value.newFoodVolume, _uiState.value.newFoodDate, _uiState.value.newFoodNote))
        }
        _uiState.update {
            it.copy(newFoodVolume = 0, newFoodDate = "", newFoodNote = "")
        }
    }

    fun deleteAllFood() {
        viewModelScope.launch {
            foodRepo.removeAllFood()
        }
    }

    fun deleteFood(
        volume: Int,
        dateAndTime: String,
    ) {
        viewModelScope.launch {
            foodRepo.removeFoodWithParams(volume, dateAndTime)
        }
    }

    fun setNewFoodVolume(volumeString: String) {
        val volume =
            try {
                volumeString.toInt()
            } catch (e: Exception) {
                0
            }
        _uiState.update {
            it.copy(newFoodVolume = volume)
        }
    }

    fun getFoodVolume(): Int {
        return _uiState.value.newFoodVolume
    }

    fun setNewFoodTime(time: String) {
        _uiState.update {
            it.copy(newFoodDate = time)
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory =
            viewModelFactory {
                initializer {
                    val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as HungryBabyApplication)
                    val foodRepository = application.container.foodRepository
                    HomeModel(foodRepo = foodRepository)
                }
            }
    }
}
