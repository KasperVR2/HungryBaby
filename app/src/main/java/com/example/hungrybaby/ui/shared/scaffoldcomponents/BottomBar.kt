package com.example.hungrybaby.ui.shared.scaffoldcomponents

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun BottomB(
    goHome: () -> Unit,
    goToAbout: () -> Unit,
    goToASettings: () -> Unit,
) {
    BottomAppBar(
        modifier = Modifier.height(70.dp),
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.primary,
        actions = {
            Spacer(Modifier.weight(1F))
            IconButton(onClick = goHome) {
                Icon(
                    Icons.Filled.Home,
                    contentDescription = "navigate to home screen",
                )
            }
            IconButton(onClick = goToAbout) {
                Icon(
                    Icons.Filled.Info,
                    contentDescription = "navigate to about page",
                )
            }
            IconButton(onClick = goToASettings) {
                Icon(
                    Icons.Filled.Settings,
                    contentDescription = "navigate to settings page",
                )
            }
            Spacer(Modifier.weight(1F))
        },
    )
}
