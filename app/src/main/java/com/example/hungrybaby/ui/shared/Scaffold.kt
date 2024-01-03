package com.example.hungrybaby.ui.shared

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun Scaff(body: @Composable () -> Unit) {
    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
        },
        bottomBar = {
        },
        floatingActionButton = {
        },
    ) { innerPadding ->
        body()
    }
}
