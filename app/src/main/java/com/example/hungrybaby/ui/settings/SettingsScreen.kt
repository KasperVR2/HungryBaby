package com.example.hungrybaby.ui.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun Settings(resetNameAndBirthdate: () -> Unit) {
    Column {
        Text("Settings")
        TextButton(onClick = resetNameAndBirthdate) {
            Text("Reset name and birthdate")
        }
    }
}
