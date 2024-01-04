package com.example.hungrybaby.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.hungrybaby.ui.home.food.FoodOverview
import com.example.hungrybaby.ui.shared.StartData
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking

@Composable
fun Home() {
    val data = StartData(LocalContext.current)
    val babyData: String? = runBlocking { data.getBabyData.firstOrNull() }
    Column {
        Text("Home $babyData")
        FoodOverview()
    }
}
