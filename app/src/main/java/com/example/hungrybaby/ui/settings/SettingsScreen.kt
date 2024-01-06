package com.example.hungrybaby.ui.settings

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.core.content.ContextCompat.getString
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.hungrybaby.R
import com.example.hungrybaby.ui.home.HomeModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Settings(
    resetNameAndBirthdate: () -> Unit,
    homeModel: HomeModel = viewModel(factory = HomeModel.Factory),
) {
    val openAlertDialog = remember { mutableStateOf(false) }
    val context = LocalContext.current
    Column {
        TextButton(onClick = resetNameAndBirthdate) {
            Text("Reset name and birthdate")
        }
        TextButton(onClick = {
            openAlertDialog.value = true
        }) {
            Text("Delete all food")
        }

        when {
            openAlertDialog.value -> {
                AlertDialog(
                    onDismissRequest = { openAlertDialog.value = false },
                    onConfirmation = {
                        openAlertDialog.value = false
                        homeModel.deleteAllFood()
                        Toast.makeText(
                            context,
                            getString(context, R.string.all_foods_deleted),
                            Toast.LENGTH_SHORT,
                        ).show()
                    },
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlertDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
) {
    AlertDialog(
        title = {
            Text(text = stringResource(id = R.string.delete_all_food))
        },
        text = {
            Text(text = stringResource(id = R.string.delete_all_food_text))
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                },
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                },
            ) {
                Text("Dismiss")
            }
        },
    )
}
