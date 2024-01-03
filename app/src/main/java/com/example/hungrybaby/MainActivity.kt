package com.example.hungrybaby

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.hungrybaby.ui.HungryBabyApp
import com.example.hungrybaby.ui.shared.StartData
import com.example.hungrybaby.ui.theme.HungryBabyTheme
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Ophalen van de data van de baby uit de datastore
        val data = StartData(this)
        var babyData: String?
        runBlocking { babyData = data.getBabyData.firstOrNull() }

        setContent {
            HungryBabyTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    HungryBabyApp(babyData = babyData)
                }
            }
        }
    }
}
