package com.example.hungrybaby.ui.home.food

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch

@Composable
fun FoodOverview(
    modifier: Modifier = Modifier,
    foodModel: FoodModel = viewModel(factory = FoodModel.Factory),
) {
    val foodOverviewState by foodModel.uiState.collectAsState()
    val foodListState by foodModel.uiListState.collectAsState()
    val foodApiState = foodModel.foodApiState

    Box(modifier = modifier) {
        when (foodApiState) {
            is FoodApiState.Loading -> Text("Loading...")
            is FoodApiState.Error -> Text("Couldn't load...")
            is FoodApiState.Success ->
                Column {
                    FoodList(
                        foodState = foodOverviewState,
                        foodListState = foodListState,
                    )
                    Button(onClick = { foodModel.addFood() }) {
                        Text("Add food")
                    }
                }
        }
    }
}

@Composable
fun FoodList(
    foodState: FoodState,
    foodListState: FoodListState,
) {
    val lazyListState = rememberLazyListState()
    LazyColumn(state = lazyListState) {
        items(foodListState.foodList) {
            FoodItem(volume = it.volume, dateAndTime = it.dateAndTime, note = it.note)
        }
    }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(foodState.scrollActionIdx) {
        if (foodState.scrollActionIdx != 0) {
            coroutineScope.launch {
                lazyListState.animateScrollToItem(foodState.scrollToItemIndex)
            }
        }
    }
}
