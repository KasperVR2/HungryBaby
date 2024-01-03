package com.example.hungrybaby.ui.shared

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import com.example.hungrybaby.R
import com.example.hungrybaby.ui.shared.scaffoldcomponents.BottomB
import com.example.hungrybaby.ui.shared.scaffoldcomponents.TopB

@Composable
fun Scaff(
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    currentScreenTitle: Int,
    goHome: () -> Unit,
    goToAbout: () -> Unit,
    goToSettings: () -> Unit,
    body: @Composable () -> Unit,
) {
    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            TopB(
                canNavigateBack = canNavigateBack,
                navigateUp = navigateUp,
                currentScreenTitle = currentScreenTitle,
            )
        },
        bottomBar = {
            BottomB(goHome, goToAbout, goToSettings)
        },
        floatingActionButton = {
        },
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            Box(modifier = Modifier.padding(dimensionResource(id = R.dimen.mediumPadding))) {
                body()
            }
        }
    }
}
