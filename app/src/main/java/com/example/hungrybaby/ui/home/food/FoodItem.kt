package com.example.hungrybaby.ui.home.food

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun FoodItem(
    volume: Int,
    dateAndTime: String,
    note: String = "",
) {
    Text(text = "Volume: $volume en $dateAndTime")
}
