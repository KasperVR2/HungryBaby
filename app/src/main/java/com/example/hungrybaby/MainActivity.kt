package com.example.hungrybaby

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.example.hungrybaby.ui.opening.StartData
import com.example.hungrybaby.ui.theme.HungryBabyTheme
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val data = StartData(this)
        var naamBaby: String?
        runBlocking { naamBaby = data.getBabyData.firstOrNull() }
        setContent {
            HungryBabyTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    if (naamBaby.isNullOrEmpty()) {
                        Inschrijving()
                    } else {
                        Opening(naamBaby!!)
                    }
                }
            }
        }
    }
}

@Composable
fun StartScherm(modifier: Modifier = Modifier) {
    val data = StartData(LocalContext.current)
    var naamBaby: String?

    runBlocking {
        // data.saveBabyData("Léonore")
        naamBaby = data.getBabyData.firstOrNull()
    }

    if (naamBaby.isNullOrEmpty()) {
        Inschrijving()
    } else {
        Opening(naamBaby!!)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Inschrijving(modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier =
            Modifier
                .padding(dimensionResource(R.dimen.mediumPadding)),
    ) {
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.mediumSpacer)))
        OutlinedTextField(
            value = "",
            onValueChange = {},
            label = { Text(stringResource(R.string.wat_is_naam_baby)) },
        )
        Row {
            Spacer(Modifier.weight(1F))
            TextButton(onClick = {}) {
                Text(stringResource(R.string.bewaar))
            }
        }
    }
}

@Composable
fun Opening(
    name: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = "Hello $name!",
        modifier = modifier,
    )
}
