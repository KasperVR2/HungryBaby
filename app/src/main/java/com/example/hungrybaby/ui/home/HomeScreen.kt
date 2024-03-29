package com.example.hungrybaby.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimeInput
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.hungrybaby.R
import com.example.hungrybaby.ui.home.food.FoodOverview
import com.example.hungrybaby.ui.shared.StartData
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(homeModel: HomeModel = viewModel(factory = HomeModel.Factory)) {
    // Variables for UI
    val data = StartData(LocalContext.current)
    val babyData: String? = runBlocking { data.getBabyData.firstOrNull() }
    val name = babyData?.split(";")?.get(0) ?: ""
    val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    val date =
        try {
            LocalDate.parse(babyData?.split(";")?.get(1), dateFormatter)
        } catch (e: Exception) {
            LocalDate.now()
        }
    val daysBetween = daysBetween(LocalDate.now(), date)
    val openFoodDialog = rememberSaveable { mutableStateOf(false) }
    val time = rememberSaveable { mutableStateOf("") }

    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
    )
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                "Baby $name: $daysBetween ${stringResource(id = R.string.days_old)}",
                style = TextStyle(fontWeight = androidx.compose.ui.text.font.FontWeight.Bold),
            )
            Spacer(modifier = Modifier.weight(1F))
            Button(onClick = { openFoodDialog.value = true }) {
                Text(text = "+")
                Image(painter = painterResource(id = R.drawable.bottle), contentDescription = "Bottle")
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        FoodOverview()
    }

    when {
        openFoodDialog.value -> {
            AddFoodDialog(
                onDismissRequest = { openFoodDialog.value = false },
                time = time,
                homeModel = homeModel,
                modal = openFoodDialog,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddFoodDialog(
    onDismissRequest: () -> Unit,
    time: MutableState<String>,
    homeModel: HomeModel,
    modal: MutableState<Boolean>,
) {
    var volume by remember { mutableStateOf("") }
    val timeState = rememberTimePickerState(is24Hour = true)
    val changeVolume = { newVolume: String -> volume = newVolume }
    var warning by remember { mutableStateOf(false) }

    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(500.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(20.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo",
                    contentScale = ContentScale.Fit,
                    modifier =
                        Modifier
                            .height(160.dp),
                )

                if (warning) {
                    Text(
                        text = stringResource(id = R.string.warning),
                        style = TextStyle(fontWeight = androidx.compose.ui.text.font.FontWeight.Bold),
                        modifier = Modifier.testTag("warning"),
                    )
                }

                TimeInput(
                    state = timeState,
                    modifier = Modifier.padding(16.dp),
                )

                OutlinedTextField(
                    value = volume,
                    onValueChange = { changeVolume(it) },
                    label = { Text(stringResource(R.string.how_much_drink)) },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                )

                Row(
                    modifier =
                        Modifier
                            .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    TextButton(
                        onClick = { onDismissRequest() },
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Text(stringResource(id = R.string.dismiss))
                    }
                    TextButton(
                        onClick = {
                            val hour = if (timeState.hour < 10) "0" + timeState.hour.toString() else timeState.hour.toString()
                            val minute = if (timeState.minute < 10) "0" + timeState.minute.toString() else timeState.minute.toString()
                            val flag = checkVolume(volume)
                            if (flag) {
                                time.value = DateTimeFormatter.ofPattern("yyyy/MM/dd").format(LocalDate.now()) + "-" + hour + ":" + minute
                                homeModel.setNewFoodTime(time.value)
                                homeModel.setNewFoodVolume(volume)
                                homeModel.addFood()
                                modal.value = false
                            } else {
                                warning = true
                                return@TextButton
                            }
                        },
                        modifier = Modifier.padding(8.dp).testTag("confirm"),
                    ) {
                        Text(stringResource(id = R.string.confirm))
                    }
                }
            }
        }
    }
}

fun checkVolume(volume: String): Boolean {
    return volume.toIntOrNull() != null && volume.toInt() > 0
}

fun daysBetween(
    dateOne: LocalDate,
    dateTwo: LocalDate,
): Int {
    val days = dateOne.toEpochDay() - dateTwo.toEpochDay()
    return days.toInt()
}
