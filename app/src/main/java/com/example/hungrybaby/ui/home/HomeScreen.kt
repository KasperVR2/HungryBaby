package com.example.hungrybaby.ui.home

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.hungrybaby.ui.shared.StartData
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking

@Composable
fun Home() {
    val data = StartData(LocalContext.current)
    var babyData: String?
    runBlocking { babyData = data.getBabyData.firstOrNull() }
    Text("Home $babyData")
}
