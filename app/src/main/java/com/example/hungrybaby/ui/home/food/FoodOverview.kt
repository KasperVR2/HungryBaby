package com.example.hungrybaby.ui.home.food

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.hungrybaby.ui.home.HomeModel

@Composable
fun FoodOverview(
    modifier: Modifier = Modifier,
    homeModel: HomeModel = viewModel(factory = HomeModel.Factory),
) {
    val foodListState by homeModel.uiListState.collectAsState()
    val foodApiState = homeModel.foodApiState

    Box(modifier = modifier) {
        when (foodApiState) {
            is FoodApiState.Loading -> Text("Loading...")
            is FoodApiState.Error -> Text("Couldn't load...")
            is FoodApiState.Success ->
                FoodList(
                    foodListState = foodListState,
                )
        }
    }
}

@Composable
fun FoodList(foodListState: FoodListState) {
    val lazyListState = rememberLazyListState()
    LazyColumn(state = lazyListState, verticalArrangement = Arrangement.spacedBy(4.dp)) {
        items(foodListState.foodList) {
            FoodItem(volume = it.volume, dateAndTime = it.dateAndTime)
        }
    }
}
