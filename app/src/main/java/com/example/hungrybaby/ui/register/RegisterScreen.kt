package com.example.hungrybaby.ui.register

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.example.hungrybaby.R
import com.example.hungrybaby.ui.shared.StartData
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Register(
    goToHomeScreen: () -> Unit,
    modifier: Modifier = Modifier,
) {
    // Datastore voor het opslaan van de naam en geboortedatum van de baby
    val data = StartData(LocalContext.current)
    // Naam Baby
    var name by rememberSaveable { mutableStateOf("") }
    val changeName = { newName: String -> name = newName }
    // Geboortedatum Baby
    var date = rememberSaveable { mutableStateOf("Kies de geboortedatum") }

    val saveData = {
        // TODO nog checks schrijven voor de naam en geboortedatum
        runBlocking { data.saveBabyData(name) }
        goToHomeScreen()
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier =
            Modifier
                .padding(dimensionResource(R.dimen.mediumPadding)),
    ) {
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.mediumSpacer)))
        OutlinedTextField(
            value = name,
            onValueChange = changeName,
            label = { Text(stringResource(R.string.wat_is_naam_baby)) },
            singleLine = true,
        )

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.mediumSpacer)))

        MyDatePickerDialog(date)

        Text(name)
        Text(text = date.value)

        Row {
            Spacer(Modifier.weight(1F))
            TextButton(onClick = saveData) {
                Text(stringResource(R.string.bewaar))
            }
        }
    }
}

@Composable
fun MyDatePickerDialog(date: MutableState<String>) {
    // var date by rememberSaveable { mutableStateOf("Kies de geboortedatum") }
    var showDatePicker by rememberSaveable { mutableStateOf(false) }
    var datum by date

    Box(contentAlignment = Alignment.Center) {
        Button(onClick = { showDatePicker = true }) {
            Text(text = datum)
        }
    }

    if (showDatePicker) {
        MyDatePickerDialog(
            onDateSelected = { datum = it },
            onDismiss = { showDatePicker = false },
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyDatePickerDialog(
    onDateSelected: (String) -> Unit,
    onDismiss: () -> Unit,
) {
    val datePickerState = rememberDatePickerState()

    val selectedDate =
        datePickerState.selectedDateMillis?.let {
            convertMillisToDate(it)
        } ?: ""

    DatePickerDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {
            Button(
                onClick = {
                    onDateSelected(selectedDate)
                    onDismiss()
                },
            ) {
                Text(text = "OK")
            }
        },
        dismissButton = {
            Button(onClick = {
                onDismiss()
            }) {
                Text(text = "Cancel")
            }
        },
    ) {
        DatePicker(
            state = datePickerState,
        )
    }
}

private fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("dd/MM/yyyy")
    return formatter.format(Date(millis))
}
