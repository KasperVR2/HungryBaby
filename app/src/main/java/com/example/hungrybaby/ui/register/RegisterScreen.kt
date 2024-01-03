package com.example.hungrybaby.ui.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import com.example.hungrybaby.R
import com.example.hungrybaby.ui.shared.StartData
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Register(
    goToHomeScreen: () -> Unit,
    firstTime: Boolean = true,
) {
    // Datastore voor het opslaan van de naam en geboortedatum van de baby
    val data = StartData(LocalContext.current)
    // Naam Baby
    var name by rememberSaveable { mutableStateOf("") }
    val changeName = { newName: String -> name = newName }
    // Geboortedatum Baby
    val chooseDate = stringResource(R.string.choose_date_birth)
    var date = rememberSaveable { mutableStateOf(chooseDate) }

    var showErrorMessage by rememberSaveable { mutableStateOf(false) }

    val saveData = saveData@{
        // Check if everything is filled in
        if (name.isEmpty() || date.value == chooseDate) {
            showErrorMessage = true
            return@saveData
        }
        runBlocking { data.saveBabyData(name) }
        goToHomeScreen()
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier =
            Modifier
                .padding(dimensionResource(R.dimen.mediumPadding)),
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = stringResource(id = R.string.logo_description),
            contentScale = ContentScale.Fit,
            modifier = Modifier.size(dimensionResource(id = R.dimen.logoSize)),
        )

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.mediumSpacer)))

        OutlinedTextField(
            value = name,
            onValueChange = changeName,
            label = { Text(stringResource(R.string.wat_is_naam_baby)) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.mediumSpacer)))

        MyDatePickerDialog(date)

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.mediumSpacer)))
        if (showErrorMessage) {
            Text(
                text = stringResource(R.string.error_message_regitration),
                style = TextStyle(color = Color.Red),
            )
        }
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.mediumSpacer)))
        Row {
            if (!firstTime) {
                TextButton(onClick = goToHomeScreen) {
                    Text(text = stringResource(R.string.cancel))
                }
            }
            TextButton(onClick = saveData) {
                Text(text = stringResource(R.string.bewaar))
            }
        }
    }
}

@Composable
fun MyDatePickerDialog(date: MutableState<String>) {
    var showDatePicker by rememberSaveable { mutableStateOf(false) }
    var datum by date

    Box(contentAlignment = Alignment.Center) {
        Button(
            onClick = { showDatePicker = true },
            modifier =
                Modifier
                    .height(dimensionResource(id = R.dimen.buttonHeight))
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colorScheme.primary),
        ) {
            Text(
                text = datum,
                style = TextStyle(fontSize = 14.sp),
            )
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
                Text(text = stringResource(id = R.string.ok))
            }
        },
        dismissButton = {
            Button(onClick = {
                onDismiss()
            }) {
                Text(text = stringResource(id = R.string.cancel))
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
